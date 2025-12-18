package com.qbk.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 商品评价实体类
 */
@Data
public class Comment {
    /**
     * 评价ID
     */
    private Long id;
    
    /**
     * 商品ID
     */
    private Long itemId;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 订单ID
     */
    private Long orderId;
    
    /**
     * 评价内容
     */
    private String content;
    
    /**
     * 评分（1-5星）
     */
    private Integer score;
    
    /**
     * 评价状态（0：正常，1：删除）
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}