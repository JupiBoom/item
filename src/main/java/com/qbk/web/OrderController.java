package com.qbk.web;

import com.qbk.entity.Order;
import com.qbk.entity.OrderStatus;
import com.qbk.service.OrderService;
import com.qbk.service.UserNotificationPreferenceService;
import com.qbk.entity.UserNotificationPreference;
import com.qbk.entity.NotificationChannel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单控制器
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final OrderService orderService;
    private final UserNotificationPreferenceService userNotificationPreferenceService;
    
    public OrderController(OrderService orderService,
                          UserNotificationPreferenceService userNotificationPreferenceService) {
        this.orderService = orderService;
        this.userNotificationPreferenceService = userNotificationPreferenceService;
    }
    
    /**
     * 创建订单
     */
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }
    
    /**
     * 更新订单状态
     */
    @PutMapping("/{id}/status")
    public Order updateOrderStatus(@PathVariable Long id, @RequestParam("status") int statusCode) {
        OrderStatus status = OrderStatus.fromCode(statusCode);
        return orderService.updateOrderStatus(id, status);
    }
    
    /**
     * 获取订单信息
     */
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
    
    /**
     * 设置用户通知偏好
     */
    @PostMapping("/notification-preference")
    public UserNotificationPreference setNotificationPreference(@RequestBody UserNotificationPreference preference) {
        return userNotificationPreferenceService.saveUserPreference(preference);
    }
    
    /**
     * 获取用户通知偏好
     */
    @GetMapping("/notification-preference/{userId}")
    public List<UserNotificationPreference> getUserNotificationPreferences(@PathVariable Long userId) {
        return userNotificationPreferenceService.getUserPreferences(userId);
    }
}
