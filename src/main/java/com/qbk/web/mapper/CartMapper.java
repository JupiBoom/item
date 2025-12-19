package com.qbk.web.mapper;

import com.qbk.entity.Cart;
import com.qbk.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 购物车Mapper接口
 */
@Mapper
public interface CartMapper {
    /**
     * 根据用户ID查询购物车
     *
     * @param userId 用户ID
     * @return 购物车信息
     */
    Cart selectCartByUserId(@Param("userId") Long userId);

    /**
     * 根据临时购物车标识查询购物车
     *
     * @param tempCartKey 临时购物车标识
     * @return 购物车信息
     */
    Cart selectCartByTempKey(@Param("tempCartKey") String tempCartKey);

    /**
     * 插入购物车
     *
     * @param cart 购物车信息
     * @return 影响行数
     */
    int insertCart(Cart cart);

    /**
     * 更新购物车
     *
     * @param cart 购物车信息
     * @return 影响行数
     */
    int updateCart(Cart cart);

    /**
     * 删除购物车
     *
     * @param id 购物车ID
     * @return 影响行数
     */
    int deleteCart(@Param("id") Long id);

    /**
     * 根据购物车ID查询购物车商品项
     *
     * @param cartId 购物车ID
     * @return 购物车商品项列表
     */
    List<CartItem> selectCartItemsByCartId(@Param("cartId") Long cartId);

    /**
     * 根据用户ID查询购物车商品项
     *
     * @param userId 用户ID
     * @return 购物车商品项列表
     */
    List<CartItem> selectCartItemsByUserId(@Param("userId") Long userId);

    /**
     * 插入购物车商品项
     *
     * @param cartItem 购物车商品项信息
     * @return 影响行数
     */
    int insertCartItem(CartItem cartItem);

    /**
     * 更新购物车商品项
     *
     * @param cartItem 购物车商品项信息
     * @return 影响行数
     */
    int updateCartItem(CartItem cartItem);

    /**
     * 删除购物车商品项
     *
     * @param id 购物车商品项ID
     * @return 影响行数
     */
    int deleteCartItem(@Param("id") Long id);

    /**
     * 根据购物车ID清空购物车商品项
     *
     * @param cartId 购物车ID
     * @return 影响行数
     */
    int clearCartItems(@Param("cartId") Long cartId);

    /**
     * 根据用户ID和商品ID查询购物车商品项
     *
     * @param userId 用户ID
     * @param productId 商品ID
     * @return 购物车商品项信息
     */
    CartItem selectCartItemByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
}
