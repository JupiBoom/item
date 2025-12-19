package com.qbk.web.mapper;

import com.qbk.entity.CartItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 购物车商品项 Mapper 接口
 */
public interface CartItemMapper {
    /**
     * 根据购物车ID查询购物车商品项
     * @param cartId 购物车ID
     * @return 购物车商品项列表
     */
    List<CartItem> findByCartId(@Param("cartId") Long cartId);

    /**
     * 根据购物车ID和商品ID查询购物车商品项
     * @param cartId 购物车ID
     * @param productId 商品ID
     * @return 购物车商品项
     */
    CartItem findByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);

    /**
     * 插入购物车商品项
     * @param cartItem 购物车商品项
     * @return 影响行数
     */
    int insert(CartItem cartItem);

    /**
     * 更新购物车商品项
     * @param cartItem 购物车商品项
     * @return 影响行数
     */
    int update(CartItem cartItem);

    /**
     * 删除购物车商品项
     * @param id 购物车商品项ID
     * @return 影响行数
     */
    int delete(@Param("id") Long id);

    /**
     * 根据购物车ID删除所有商品项
     * @param cartId 购物车ID
     * @return 影响行数
     */
    int deleteByCartId(@Param("cartId") Long cartId);
}
