package com.qbk.web.service.impl;

import com.qbk.entity.Cart;
import com.qbk.entity.CartItem;
import com.qbk.entity.param.CartParam;
import com.qbk.entity.vo.CartItemVo;
import com.qbk.entity.vo.CartVo;
import com.qbk.web.mapper.CartMapper;
import com.qbk.web.mapper.CartItemMapper;
import com.qbk.web.service.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车服务实现类
 */
@Service
public class CartServiceImpl implements CartService {
    @Resource
    private CartMapper cartMapper;

    @Resource
    private CartItemMapper cartItemMapper;

    @Override
    @Transactional
    public CartVo addToCart(CartParam cartParam, Long userId) {
        // 1. 获取或创建购物车
        Cart cart = getOrCreateCart(userId, cartParam.getTempCartKey());

        // 2. 校验库存
        if (!checkStock(cartParam.getProductId(), cartParam.getQuantity())) {
            throw new RuntimeException("商品库存不足");
        }

        // 3. 检查购物车中是否已有该商品
        CartItem existingCartItem = cartItemMapper.findByCartIdAndProductId(cart.getId(), cartParam.getProductId());

        if (existingCartItem != null) {
            // 4. 如果已有商品，更新数量
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartParam.getQuantity());
            cartItemMapper.update(existingCartItem);
        } else {
            // 5. 如果没有商品，添加新商品
            CartItem newCartItem = new CartItem();
            newCartItem.setCartId(cart.getId());
            newCartItem.setProductId(cartParam.getProductId());
            // 这里需要从商品服务获取商品信息，暂时模拟
            newCartItem.setProductName("商品名称");
            newCartItem.setPrice(100.0);
            newCartItem.setQuantity(cartParam.getQuantity());
            newCartItem.setStock(100);
            newCartItem.setImage("/image/product.jpg");
            cartItemMapper.insert(newCartItem);
        }

        // 6. 返回购物车信息
        return getCartList(userId, cartParam.getTempCartKey());
    }

    @Override
    @Transactional
    public CartVo updateCartItemQuantity(Long cartItemId, Integer quantity, Long userId, String tempCartKey) {
        // 1. 校验库存
        CartItem cartItem = cartItemMapper.findByCartIdAndProductId(null, null);
        // 这里需要根据 cartItemId 查询商品信息
        // 暂时省略，假设已经获取到商品ID
        Long productId = 1L;
        if (!checkStock(productId, quantity)) {
            throw new RuntimeException("商品库存不足");
        }

        // 2. 更新购物车商品数量
        CartItem updateItem = new CartItem();
        updateItem.setId(cartItemId);
        updateItem.setQuantity(quantity);
        cartItemMapper.update(updateItem);

        // 3. 返回购物车信息
        return getCartList(userId, tempCartKey);
    }

    @Override
    @Transactional
    public CartVo deleteCartItem(Long cartItemId, Long userId, String tempCartKey) {
        // 1. 删除购物车商品项
        cartItemMapper.delete(cartItemId);

        // 2. 返回购物车信息
        return getCartList(userId, tempCartKey);
    }

    @Override
    @Transactional
    public CartVo clearCart(Long userId, String tempCartKey) {
        // 1. 获取购物车
        Cart cart = getCart(userId, tempCartKey);
        if (cart != null) {
            // 2. 删除购物车所有商品项
            cartItemMapper.deleteByCartId(cart.getId());
        }

        // 3. 返回购物车信息
        return getCartList(userId, tempCartKey);
    }

    @Override
    public CartVo getCartList(Long userId, String tempCartKey) {
        // 1. 获取购物车
        Cart cart = getCart(userId, tempCartKey);
        CartVo cartVo = new CartVo();

        if (cart != null) {
            // 2. 获取购物车商品项
            List<CartItem> cartItems = cartItemMapper.findByCartId(cart.getId());

            // 3. 转换为 VO
            List<CartItemVo> cartItemVos = new ArrayList<>();
            Double totalAmount = 0.0;
            Integer totalQuantity = 0;

            for (CartItem item : cartItems) {
                CartItemVo itemVo = new CartItemVo();
                BeanUtils.copyProperties(item, itemVo);
                itemVo.setSubtotal(item.getPrice() * item.getQuantity());
                cartItemVos.add(itemVo);

                totalAmount += itemVo.getSubtotal();
                totalQuantity += item.getQuantity();
            }

            cartVo.setCartItems(cartItemVos);
            cartVo.setTotalAmount(totalAmount);
            cartVo.setTotalQuantity(totalQuantity);
        }

        return cartVo;
    }

    @Override
    @Transactional
    public CartVo mergeCart(String tempCartKey, Long userId) {
        // 1. 获取临时购物车
        Cart tempCart = cartMapper.findByTempCartKey(tempCartKey);
        if (tempCart == null) {
            // 临时购物车为空，直接返回登录用户购物车
            return getCartList(userId, null);
        }

        // 2. 获取登录用户购物车
        Cart userCart = getOrCreateCart(userId, null);

        // 3. 获取临时购物车商品项
        List<CartItem> tempCartItems = cartItemMapper.findByCartId(tempCart.getId());

        // 4. 合并商品项
        for (CartItem tempItem : tempCartItems) {
            CartItem existingItem = cartItemMapper.findByCartIdAndProductId(userCart.getId(), tempItem.getProductId());

            if (existingItem != null) {
                // 如果已有商品，合并数量
                existingItem.setQuantity(existingItem.getQuantity() + tempItem.getQuantity());
                cartItemMapper.update(existingItem);
            } else {
                // 如果没有商品，添加新商品
                tempItem.setCartId(userCart.getId());
                cartItemMapper.insert(tempItem);
            }
        }

        // 5. 删除临时购物车
        cartItemMapper.deleteByCartId(tempCart.getId());
        cartMapper.delete(tempCart.getId());

        // 6. 返回合并后的购物车
        return getCartList(userId, null);
    }

    @Override
    public boolean checkStock(Long productId, Integer quantity) {
        // 这里需要从商品服务获取商品库存信息
        // 暂时模拟库存充足
        Integer stock = 100;
        return stock >= quantity;
    }

    /**
     * 获取购物车
     * @param userId 用户ID
     * @param tempCartKey 临时购物车标识
     * @return 购物车对象
     */
    private Cart getCart(Long userId, String tempCartKey) {
        if (userId != null) {
            return cartMapper.findByUserId(userId);
        } else if (tempCartKey != null) {
            return cartMapper.findByTempCartKey(tempCartKey);
        }
        return null;
    }

    /**
     * 获取或创建购物车
     * @param userId 用户ID
     * @param tempCartKey 临时购物车标识
     * @return 购物车对象
     */
    private Cart getOrCreateCart(Long userId, String tempCartKey) {
        Cart cart = getCart(userId, tempCartKey);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setTempCartKey(tempCartKey);
            cartMapper.insert(cart);
        }
        return cart;
    }
}