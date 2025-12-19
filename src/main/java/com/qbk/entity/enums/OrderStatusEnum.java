package com.qbk.entity.enums;

import lombok.Getter;

/**
 * 订单状态枚举
 */
@Getter
public enum OrderStatusEnum {
    
    PENDING_PAYMENT(1, "待支付"),
    PAID(2, "已支付"),
    SHIPPED(3, "已发货"),
    DELIVERED(4, "已签收"),
    CANCELLED(5, "已取消");
    
    private final Integer code;
    private final String desc;
    
    OrderStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static OrderStatusEnum getByCode(Integer code) {
        for (OrderStatusEnum status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }
}