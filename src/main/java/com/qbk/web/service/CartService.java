package com.qbk.web.service;

import com.qbk.entity.param.CartParam;
import com.qbk.entity.vo.CartVo;

/**
 * 购物车服务接口
 */
public interface CartService {
    /**
     * 添加商品到购物车
     * @param cartParam 购物车参数
     * @param userId 用户ID（登录用户为null，未登录用户为null）
     * @return 购物车视图对象
     */
    CartVo addToCart(CartParam cartParam, Long userId);

    /**
     * 修改购物车商品数量
     * @param cartItemId 购物车商品项ID
     * @param quantity 新的数量
     * @param userId 用户ID
     * @param tempCartKey 临时购物车标识
     * @return 购物车视图对象
     */
    CartVo updateCartItemQuantity(Long cartItemId, Integer quantity, Long userId, String tempCartKey);

    /**
     * 删除购物车商品
     * @param cartItemId 购物车商品项ID
     * @param userId 用户ID
     * @param tempCartKey 临时购物车标识
     * @return 购物车视图对象
     */
    CartVo deleteCartItem(Long cartItemId, Long userId, String tempCartKey);

    /**
     * 清空购物车
     * @param userId 用户ID
     * @param tempCartKey 临时购物车标识
     * @return 购物车视图对象
     */
    CartVo clearCart(Long userId, String tempCartKey);

    /**
     * 查询购物车商品列表
     * @param userId 用户ID
     * @param tempCartKey 临时购物车标识
     * @return 购物车视图对象
     */
    CartVo getCartList(Long userId, String tempCartKey);

    /**
     * 合并登录前后的购物车
     * @param tempCartKey 临时购物车标识
     * @param userId 用户ID
     * @return 合并后的购物车视图对象
     */
    CartVo mergeCart(String tempCartKey, Long userId);

    /**
     * 校验商品库存
     * @param productId 商品ID
     * @param quantity 商品数量
     * @return 是否库存充足
     */
    boolean checkStock(Long productId, Integer quantity);
}