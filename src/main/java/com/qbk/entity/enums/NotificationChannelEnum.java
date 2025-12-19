package com.qbk.entity.enums;

import lombok.Getter;

/**
 * 通知渠道枚举
 */
@Getter
public enum NotificationChannelEnum {
    
    SMS(1, "短信"),
    WECHAT(2, "微信"),
    EMAIL(3, "邮件");
    
    private final Integer code;
    private final String desc;
    
    NotificationChannelEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static NotificationChannelEnum getByCode(Integer code) {
        for (NotificationChannelEnum channel : values()) {
            if (channel.code.equals(code)) {
                return channel;
            }
        }
        return null;
    }
}