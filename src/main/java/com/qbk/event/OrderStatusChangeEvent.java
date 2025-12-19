package com.qbk.event;

import com.qbk.entity.dto.OrderInfoDTO;
import org.springframework.context.ApplicationEvent;

/**
 * 订单状态变更事件
 */
public class OrderStatusChangeEvent extends ApplicationEvent {
    
    private final OrderInfoDTO orderInfo;
    
    public OrderStatusChangeEvent(Object source, OrderInfoDTO orderInfo) {
        super(source);
        this.orderInfo = orderInfo;
    }
    
    public OrderInfoDTO getOrderInfo() {
        return orderInfo;
    }
}