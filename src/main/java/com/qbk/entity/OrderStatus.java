package com.qbk.entity;

/**
 * 订单状态枚举
 */
public enum OrderStatus {
    /**
     * 待支付
     */
    PENDING_PAYMENT(1, "待支付"),
    
    /**
     * 支付成功
     */
    PAID(2, "支付成功"),
    
    /**
     * 已发货
     */
    SHIPPED(3, "已发货"),
    
    /**
     * 已签收
     */
    DELIVERED(4, "已签收"),
    
    /**
     * 已完成
     */
    COMPLETED(5, "已完成"),
    
    /**
     * 已取消
     */
    CANCELLED(6, "已取消");
    
    private final int code;
    private final String description;
    
    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static OrderStatus fromCode(int code) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid OrderStatus code: " + code);
    }
}