package com.qbk.service.impl;

import com.qbk.entity.Order;
import com.qbk.entity.OrderStatus;
import com.qbk.service.OrderService;
import com.qbk.service.OrderStatusListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 订单服务实现类
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    
    private final ConcurrentHashMap<Long, Order> orders = new ConcurrentHashMap<>();
    private final OrderStatusListener orderStatusListener;
    private Long nextOrderId = 1L;
    
    public OrderServiceImpl(OrderStatusListener orderStatusListener) {
        this.orderStatusListener = orderStatusListener;
    }
    
    @Override
    public Order createOrder(Order order) {
        order.setId(nextOrderId++);
        order.setOrderNo(generateOrderNo());
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        
        orders.put(order.getId(), order);
        log.info("Created new order: {}", order);
        
        return order;
    }
    
    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        
        OrderStatus oldStatus = order.getStatus();
        if (oldStatus == newStatus) {
            log.info("Order status is already: {}, no change needed", newStatus);
            return order;
        }
        
        // 更新订单状态
        order.setStatus(newStatus);
        order.setUpdateTime(LocalDateTime.now());
        
        // 设置相应的时间
        if (newStatus == OrderStatus.PAID) {
            order.setPayTime(LocalDateTime.now());
        } else if (newStatus == OrderStatus.SHIPPED) {
            order.setShipTime(LocalDateTime.now());
        } else if (newStatus == OrderStatus.DELIVERED) {
            order.setSignTime(LocalDateTime.now());
        }
        
        log.info("Updated order status: OrderId={}, OldStatus={}, NewStatus={}", 
            orderId, oldStatus, newStatus);
        
        // 触发订单状态变更监听
        orderStatusListener.onOrderStatusChanged(order, oldStatus, newStatus);
        
        return order;
    }
    
    @Override
    public Order getOrderById(Long orderId) {
        return orders.get(orderId);
    }
    
    private String generateOrderNo() {
        return "ORD" + System.currentTimeMillis() + String.format("%04d", (int)(Math.random() * 10000));
    }
}
