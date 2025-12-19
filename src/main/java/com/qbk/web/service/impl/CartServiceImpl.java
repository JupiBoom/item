package com.qbk.web.service.impl;

import com.qbk.entity.Cart;
import com.qbk.entity.CartItem;
import com.qbk.entity.Product;
import com.qbk.result.BaseResult;
import com.qbk.result.BaseResultGenerator;
import com.qbk.result.ResultStatus;
import com.qbk.web.mapper.CartMapper;
import com.qbk.web.mapper.ProductMapper;
import com.qbk.web.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 购物车Service实现类
 */
@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;
    
    @Resource
    private ProductMapper productMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult addToCart(Long userId, String tempCartKey, Long productId, Integer quantity) {
        // 校验库存
        if (!checkStock(productId, quantity)) {
            return BaseResultGenerator.fail(ResultStatus.STOCK_INSUFFICIENT);
        }

        // 处理登录用户和未登录用户的购物车
        if (userId != null) {
            return addToUserCart(userId, productId, quantity);
        } else if (tempCartKey != null) {
            return addToTempCart(tempCartKey, productId, quantity);
        } else {
            return BaseResultGenerator.fail(ResultStatus.PARAM_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult updateCartItemQuantity(Long userId, String tempCartKey, Long productId, Integer quantity) {
        // 校验库存
        if (!checkStock(productId, quantity)) {
            return BaseResultGenerator.fail(ResultStatus.STOCK_INSUFFICIENT);
        }

        if (userId != null) {
            return updateUserCartItemQuantity(userId, productId, quantity);
        } else if (tempCartKey != null) {
            return updateTempCartItemQuantity(tempCartKey, productId, quantity);
        } else {
            return BaseResultGenerator.fail(ResultStatus.PARAM_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult deleteCartItem(Long userId, String tempCartKey, Long productId) {
        if (userId != null) {
            return deleteUserCartItem(userId, productId);
        } else if (tempCartKey != null) {
            return deleteTempCartItem(tempCartKey, productId);
        } else {
            return BaseResultGenerator.fail(ResultStatus.PARAM_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult clearCart(Long userId, String tempCartKey) {
        if (userId != null) {
            return clearUserCart(userId);
        } else if (tempCartKey != null) {
            return clearTempCart(tempCartKey);
        } else {
            return BaseResultGenerator.fail(ResultStatus.PARAM_ERROR);
        }
    }

    @Override
    public BaseResult<List<CartItem>> getCartItems(Long userId, String tempCartKey) {
        if (userId != null) {
            List<CartItem> cartItems = cartMapper.selectCartItemsByUserId(userId);
            return BaseResultGenerator.success(cartItems);
        } else if (tempCartKey != null) {
            Cart cart = cartMapper.selectCartByTempKey(tempCartKey);
            if (cart != null) {
                List<CartItem> cartItems = cartMapper.selectCartItemsByCartId(cart.getId());
                return BaseResultGenerator.success(cartItems);
            }
            return BaseResultGenerator.success(null);
        } else {
            return BaseResultGenerator.fail(ResultStatus.PARAM_ERROR);
        }
    }

    @Override
    public BaseResult<Double> getCartTotalAmount(Long userId, String tempCartKey) {
        List<CartItem> cartItems;
        if (userId != null) {
            cartItems = cartMapper.selectCartItemsByUserId(userId);
        } else if (tempCartKey != null) {
            Cart cart = cartMapper.selectCartByTempKey(tempCartKey);
            if (cart == null) {
                return BaseResultGenerator.success(0.0);
            }
            cartItems = cartMapper.selectCartItemsByCartId(cart.getId());
        } else {
            return BaseResultGenerator.fail(ResultStatus.PARAM_ERROR);
        }

        double totalAmount = 0.0;
        for (CartItem item : cartItems) {
            totalAmount += item.getPrice().doubleValue() * item.getQuantity();
        }
        return BaseResultGenerator.success(totalAmount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult mergeCart(Long userId, String tempCartKey) {
        if (userId == null || tempCartKey == null) {
            return BaseResultGenerator.fail(ResultStatus.PARAM_ERROR);
        }

        // 获取临时购物车商品
        Cart tempCart = cartMapper.selectCartByTempKey(tempCartKey);
        if (tempCart == null) {
            return BaseResultGenerator.success();
        }

        List<CartItem> tempCartItems = cartMapper.selectCartItemsByCartId(tempCart.getId());
        if (tempCartItems == null || tempCartItems.isEmpty()) {
            return BaseResultGenerator.success();
        }

        // 合并到用户购物车
        for (CartItem tempItem : tempCartItems) {
            CartItem existingItem = cartMapper.selectCartItemByUserIdAndProductId(userId, tempItem.getProductId());
            if (existingItem != null) {
                // 商品已存在，合并数量
                existingItem.setQuantity(existingItem.getQuantity() + tempItem.getQuantity());
                cartMapper.updateCartItem(existingItem);
            } else {
                // 商品不存在，添加新商品
                tempItem.setUserId(userId);
                tempItem.setCartId(null);
                cartMapper.insertCartItem(tempItem);
            }
        }

        // 清空临时购物车
        clearTempCart(tempCartKey);

        return BaseResultGenerator.success();
    }

    @Override
    public boolean checkStock(Long productId, Integer quantity) {
        Product product = productMapper.selectProductById(productId);
        if (product == null || product.getStock() < quantity) {
            return false;
        }
        return true;
    }

    /**
     * 向登录用户购物车添加商品
     */
    private BaseResult addToUserCart(Long userId, Long productId, Integer quantity) {
        Cart cart = cartMapper.selectCartByUserId(userId);
        if (cart == null) {
            // 创建新购物车
            cart = new Cart();
            cart.setUserId(userId);
            cart.setCreateTime(new Date());
            cart.setUpdateTime(new Date());
            cartMapper.insertCart(cart);
        }

        // 检查商品是否已在购物车中
        CartItem existingItem = cartMapper.selectCartItemByUserIdAndProductId(userId, productId);
        if (existingItem != null) {
            // 商品已存在，更新数量
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setUpdateTime(new Date());
            cartMapper.updateCartItem(existingItem);
        } else {
            // 添加新商品
            Product product = productMapper.selectProductById(productId);
            if (product == null) {
                return BaseResultGenerator.fail(ResultStatus.PARAM_ERROR);
            }
            
            CartItem newItem = new CartItem();
            newItem.setCartId(cart.getId());
            newItem.setUserId(userId);
            newItem.setProductId(productId);
            newItem.setProductName(product.getName());
            newItem.setPrice(product.getPrice());
            newItem.setQuantity(quantity);
            newItem.setStock(product.getStock());
            newItem.setImage(product.getImage());
            newItem.setCreateTime(new Date());
            newItem.setUpdateTime(new Date());
            cartMapper.insertCartItem(newItem);
        }

        return BaseResultGenerator.success();
    }

    /**
     * 向临时购物车添加商品
     */
    private BaseResult addToTempCart(String tempCartKey, Long productId, Integer quantity) {
        Cart cart = cartMapper.selectCartByTempKey(tempCartKey);
        if (cart == null) {
            // 创建新临时购物车
            cart = new Cart();
            cart.setTempCartKey(tempCartKey);
            cart.setCreateTime(new Date());
            cart.setUpdateTime(new Date());
            cartMapper.insertCart(cart);
        }

        // 检查商品是否已在临时购物车中
        // 注意：临时购物车没有userId，所以需要通过cartId和productId查询
        List<CartItem> cartItems = cartMapper.selectCartItemsByCartId(cart.getId());
        CartItem existingItem = null;
        for (CartItem item : cartItems) {
            if (item.getProductId().equals(productId)) {
                existingItem = item;
                break;
            }
        }

        if (existingItem != null) {
            // 商品已存在，更新数量
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setUpdateTime(new Date());
            cartMapper.updateCartItem(existingItem);
        } else {
            // 添加新商品
            Product product = productMapper.selectProductById(productId);
            if (product == null) {
                return BaseResultGenerator.fail(ResultStatus.PARAM_ERROR);
            }

            CartItem newItem = new CartItem();
            newItem.setCartId(cart.getId());
            newItem.setProductId(productId);
            newItem.setProductName(product.getName());
            newItem.setPrice(product.getPrice());
            newItem.setQuantity(quantity);
            newItem.setStock(product.getStock());
            newItem.setImage(product.getImage());
            newItem.setCreateTime(new Date());
            newItem.setUpdateTime(new Date());
            cartMapper.insertCartItem(newItem);
        }

        return BaseResultGenerator.success();
    }

    /**
     * 更新登录用户购物车商品数量
     */
    private BaseResult updateUserCartItemQuantity(Long userId, Long productId, Integer quantity) {
        CartItem cartItem = cartMapper.selectCartItemByUserIdAndProductId(userId, productId);
        if (cartItem == null) {
            return BaseResultGenerator.fail(ResultStatus.CART_ITEM_NOT_FOUND);
        }

        cartItem.setQuantity(quantity);
        cartItem.setUpdateTime(new Date());
        cartMapper.updateCartItem(cartItem);

        return BaseResultGenerator.success();
    }

    /**
     * 更新临时购物车商品数量
     */
    private BaseResult updateTempCartItemQuantity(String tempCartKey, Long productId, Integer quantity) {
        Cart cart = cartMapper.selectCartByTempKey(tempCartKey);
        if (cart == null) {
            return BaseResultGenerator.fail(ResultStatus.CART_ITEM_NOT_FOUND);
        }

        List<CartItem> cartItems = cartMapper.selectCartItemsByCartId(cart.getId());
        CartItem cartItem = null;
        for (CartItem item : cartItems) {
            if (item.getProductId().equals(productId)) {
                cartItem = item;
                break;
            }
        }

        if (cartItem == null) {
            return BaseResultGenerator.fail(ResultStatus.CART_ITEM_NOT_FOUND);
        }

        cartItem.setQuantity(quantity);
        cartItem.setUpdateTime(new Date());
        cartMapper.updateCartItem(cartItem);

        return BaseResultGenerator.success();
    }

    /**
     * 删除登录用户购物车商品
     */
    private BaseResult deleteUserCartItem(Long userId, Long productId) {
        CartItem cartItem = cartMapper.selectCartItemByUserIdAndProductId(userId, productId);
        if (cartItem == null) {
            return BaseResultGenerator.fail(ResultStatus.CART_ITEM_NOT_FOUND);
        }

        cartMapper.deleteCartItem(cartItem.getId());
        return BaseResultGenerator.success();
    }

    /**
     * 删除临时购物车商品
     */
    private BaseResult deleteTempCartItem(String tempCartKey, Long productId) {
        Cart cart = cartMapper.selectCartByTempKey(tempCartKey);
        if (cart == null) {
            return BaseResultGenerator.fail(ResultStatus.CART_ITEM_NOT_FOUND);
        }

        List<CartItem> cartItems = cartMapper.selectCartItemsByCartId(cart.getId());
        CartItem cartItem = null;
        for (CartItem item : cartItems) {
            if (item.getProductId().equals(productId)) {
                cartItem = item;
                break;
            }
        }

        if (cartItem == null) {
            return BaseResultGenerator.fail(ResultStatus.CART_ITEM_NOT_FOUND);
        }

        cartMapper.deleteCartItem(cartItem.getId());
        return BaseResultGenerator.success();
    }

    /**
     * 清空登录用户购物车
     */
    private BaseResult clearUserCart(Long userId) {
        Cart cart = cartMapper.selectCartByUserId(userId);
        if (cart == null) {
            return BaseResultGenerator.success();
        }

        cartMapper.clearCartItems(cart.getId());
        return BaseResultGenerator.success();
    }

    /**
     * 清空临时购物车
     */
    private BaseResult clearTempCart(String tempCartKey) {
        Cart cart = cartMapper.selectCartByTempKey(tempCartKey);
        if (cart == null) {
            return BaseResultGenerator.success();
        }

        cartMapper.clearCartItems(cart.getId());
        return BaseResultGenerator.success();
    }
}
