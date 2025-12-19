package com.qbk.service;

import com.qbk.entity.Order;
import com.qbk.entity.OrderStatus;

/**
 * 订单服务接口
 */
public interface OrderService {
    /**
     * 创建订单
     * @param order 订单信息
     * @return 创建后的订单
     */
    Order createOrder(Order order);
    
    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param newStatus 新状态
     * @return 更新后的订单
     */
    Order updateOrderStatus(Long orderId, OrderStatus newStatus);
    
    /**
     * 根据ID获取订单
     * @param orderId 订单ID
     * @return 订单信息
     */
    Order getOrderById(Long orderId);
}
