package com.qbk.entity;

import lombok.Data;

/**
 * 用户通知偏好设置
 */
@Data
public class UserNotificationPreference {
    /**
     * ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 通知渠道
     */
    private NotificationChannel channel;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    /**
     * 接收地址（手机号、邮箱等）
     */
    private String recipient;
    
    /**
     * 创建时间
     */
    private java.time.LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private java.time.LocalDateTime updateTime;
}