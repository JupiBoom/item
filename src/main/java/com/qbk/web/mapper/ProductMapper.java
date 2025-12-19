package com.qbk.web.mapper;

import com.qbk.entity.Product;
import org.apache.ibatis.annotations.Param;

/**
 * 商品Mapper接口
 */
public interface ProductMapper {
    /**
     * 根据商品ID查询商品信息
     *
     * @param id 商品ID
     * @return 商品信息
     */
    Product selectProductById(@Param("id") Long id);

    /**
     * 更新商品库存
     *
     * @param id 商品ID
     * @param stock 新库存数量
     * @return 影响行数
     */
    int updateProductStock(@Param("id") Long id, @Param("stock") Integer stock);
}
