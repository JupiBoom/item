package com.qbk.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物车商品项实体类
 */
@Data
public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 购物车商品项ID
     */
    private Long id;

    /**
     * 购物车ID
     */
    private Long cartId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 商品图片
     */
    private String image;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
