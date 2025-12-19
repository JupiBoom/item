package com.qbk.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品实体类
 */
@Data
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 商品图片
     */
    private String image;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
