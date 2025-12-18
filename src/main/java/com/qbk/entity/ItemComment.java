package com.qbk.entity;

import lombok.Data;
import java.util.Date;

/**
 * 商品评价实体类
 */
@Data
public class ItemComment {
    /**
     * 评价ID
     */
    private Integer id;
    /**
     * 商品ID
     */
    private Integer itemId;
    /**
     * 订单ID
     */
    private Integer orderId;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 评分（1-5星）
     */
    private Integer score;
    /**
     * 评价内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除
     */
    private Integer isDel;
}