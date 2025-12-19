package com.qbk.service;

import com.qbk.entity.Order;
import com.qbk.entity.OrderStatus;

/**
 * 订单状态监听器接口
 */
public interface OrderStatusListener {
    /**
     * 订单状态变更时触发
     * @param order 订单信息
     * @param oldStatus 旧状态
     * @param newStatus 新状态
     */
    void onOrderStatusChanged(Order order, OrderStatus oldStatus, OrderStatus newStatus);
}
