package com.qbk.web.mapper;

import com.qbk.entity.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 购物车 Mapper 接口
 */
public interface CartMapper {
    /**
     * 根据用户ID查询购物车
     * @param userId 用户ID
     * @return 购物车对象
     */
    Cart findByUserId(@Param("userId") Long userId);

    /**
     * 根据临时购物车标识查询购物车
     * @param tempCartKey 临时购物车标识
     * @return 购物车对象
     */
    Cart findByTempCartKey(@Param("tempCartKey") String tempCartKey);

    /**
     * 插入购物车
     * @param cart 购物车对象
     * @return 影响行数
     */
    int insert(Cart cart);

    /**
     * 更新购物车
     * @param cart 购物车对象
     * @return 影响行数
     */
    int update(Cart cart);

    /**
     * 删除购物车
     * @param id 购物车ID
     * @return 影响行数
     */
    int delete(@Param("id") Long id);
}
