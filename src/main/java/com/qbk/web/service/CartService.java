package com.qbk.web.service;

import com.qbk.entity.Cart;
import com.qbk.entity.CartItem;
import com.qbk.result.BaseResult;

import java.util.List;

/**
 * 购物车Service接口
 */
public interface CartService {
    /**
     * 添加商品到购物车
     *
     * @param userId 用户ID（登录用户）
     * @param tempCartKey 临时购物车标识（未登录用户）
     * @param productId 商品ID
     * @param quantity 商品数量
     * @return 操作结果
     */
    BaseResult addToCart(Long userId, String tempCartKey, Long productId, Integer quantity);

    /**
     * 修改购物车商品数量
     *
     * @param userId 用户ID（登录用户）
     * @param tempCartKey 临时购物车标识（未登录用户）
     * @param productId 商品ID
     * @param quantity 新的商品数量
     * @return 操作结果
     */
    BaseResult updateCartItemQuantity(Long userId, String tempCartKey, Long productId, Integer quantity);

    /**
     * 删除购物车商品
     *
     * @param userId 用户ID（登录用户）
     * @param tempCartKey 临时购物车标识（未登录用户）
     * @param productId 商品ID
     * @return 操作结果
     */
    BaseResult deleteCartItem(Long userId, String tempCartKey, Long productId);

    /**
     * 清空购物车
     *
     * @param userId 用户ID（登录用户）
     * @param tempCartKey 临时购物车标识（未登录用户）
     * @return 操作结果
     */
    BaseResult clearCart(Long userId, String tempCartKey);

    /**
     * 查询购物车商品列表
     *
     * @param userId 用户ID（登录用户）
     * @param tempCartKey 临时购物车标识（未登录用户）
     * @return 购物车商品列表
     */
    BaseResult<List<CartItem>> getCartItems(Long userId, String tempCartKey);

    /**
     * 计算购物车总金额
     *
     * @param userId 用户ID（登录用户）
     * @param tempCartKey 临时购物车标识（未登录用户）
     * @return 购物车总金额
     */
    BaseResult<Double> getCartTotalAmount(Long userId, String tempCartKey);

    /**
     * 合并登录前后的购物车
     *
     * @param userId 用户ID
     * @param tempCartKey 临时购物车标识
     * @return 操作结果
     */
    BaseResult mergeCart(Long userId, String tempCartKey);

    /**
     * 校验商品库存
     *
     * @param productId 商品ID
     * @param quantity 商品数量
     * @return 库存是否充足
     */
    boolean checkStock(Long productId, Integer quantity);
}
