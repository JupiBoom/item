package com.qbk.entity;

import lombok.Data;

/**
 * 通知模板实体类
 */
@Data
public class NotificationTemplate {
    /**
     * 模板ID
     */
    private Long id;
    
    /**
     * 订单状态
     */
    private OrderStatus orderStatus;
    
    /**
     * 通知渠道
     */
    private NotificationChannel channel;
    
    /**
     * 模板内容
     */
    private String content;
    
    /**
     * 模板标题
     */
    private String title;
    
    /**
     * 创建时间
     */
    private java.time.LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private java.time.LocalDateTime updateTime;
}