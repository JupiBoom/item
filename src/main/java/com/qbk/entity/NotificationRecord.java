package com.qbk.entity;

import lombok.Data;

/**
 * 通知记录实体类
 */
@Data
public class NotificationRecord {
    /**
     * 记录ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 订单ID
     */
    private Long orderId;
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 通知渠道
     */
    private NotificationChannel channel;
    
    /**
     * 通知状态
     */
    private NotificationStatus status;
    
    /**
     * 通知内容
     */
    private String content;
    
    /**
     * 失败原因
     */
    private String failureReason;
    
    /**
     * 重试次数
     */
    private Integer retryCount;
    
    /**
     * 最大重试次数
     */
    private Integer maxRetryCount;
    
    /**
     * 下次重试时间
     */
    private java.time.LocalDateTime nextRetryTime;
    
    /**
     * 创建时间
     */
    private java.time.LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private java.time.LocalDateTime updateTime;
    
    /**
     * 通知状态枚举
     */
    public enum NotificationStatus {
        PENDING(1, "待发送"),
        SUCCESS(2, "发送成功"),
        FAILED(3, "发送失败"),
        RETRYING(4, "重试中");
        
        private final int code;
        private final String description;
        
        NotificationStatus(int code, String description) {
            this.code = code;
            this.description = description;
        }
        
        public int getCode() {
            return code;
        }
        
        public String getDescription() {
            return description;
        }
    }
}