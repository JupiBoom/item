package com.qbk.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 购物车实体类
 */
@Data
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 购物车ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 临时购物车标识（未登录用户）
     */
    private String tempCartKey;

    /**
     * 购物车商品项列表
     */
    private List<CartItem> cartItems;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
