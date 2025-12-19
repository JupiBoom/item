package com.qbk.entity;

/**
 * 通知渠道枚举
 */
public enum NotificationChannel {
    /**
     * 短信通知
     */
    SMS("sms", "短信"),
    
    /**
     * 微信通知
     */
    WECHAT("wechat", "微信"),
    
    /**
     * 邮件通知
     */
    EMAIL("email", "邮件");
    
    private final String code;
    private final String description;
    
    NotificationChannel(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static NotificationChannel fromCode(String code) {
        for (NotificationChannel channel : NotificationChannel.values()) {
            if (channel.code.equals(code)) {
                return channel;
            }
        }
        throw new IllegalArgumentException("Invalid NotificationChannel code: " + code);
    }
}