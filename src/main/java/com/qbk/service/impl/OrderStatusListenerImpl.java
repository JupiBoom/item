package com.qbk.service.impl;

import com.qbk.entity.Order;
import com.qbk.entity.OrderStatus;
import com.qbk.entity.NotificationChannel;
import com.qbk.entity.UserNotificationPreference;
import com.qbk.service.OrderStatusListener;
import com.qbk.service.NotificationService;
import com.qbk.service.UserNotificationPreferenceService;
import com.qbk.service.NotificationRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 订单状态监听器实现类
 */
@Component
@Slf4j
public class OrderStatusListenerImpl implements OrderStatusListener {
    
    private final NotificationService notificationService;
    private final UserNotificationPreferenceService userNotificationPreferenceService;
    private final NotificationRecordService notificationRecordService;
    
    public OrderStatusListenerImpl(NotificationService notificationService,
                                   UserNotificationPreferenceService userNotificationPreferenceService,
                                   NotificationRecordService notificationRecordService) {
        this.notificationService = notificationService;
        this.userNotificationPreferenceService = userNotificationPreferenceService;
        this.notificationRecordService = notificationRecordService;
    }
    
    @Override
    public void onOrderStatusChanged(Order order, OrderStatus oldStatus, OrderStatus newStatus) {
        log.info("Order status changed: OrderNo={}, OldStatus={}, NewStatus={}", 
                order.getOrderNo(), oldStatus, newStatus);
        
        // 获取用户通知偏好设置
        List<UserNotificationPreference> preferences = 
                userNotificationPreferenceService.getUserPreferences(order.getUserId());
        
        // 为每个启用的通知渠道发送通知
        for (UserNotificationPreference preference : preferences) {
            if (preference.getEnabled()) {
                NotificationChannel channel = preference.getChannel();
                boolean success = notificationService.sendOrderNotification(order, channel);
                
                // 记录通知
                notificationRecordService.recordNotification(
                    order.getUserId(),
                    order.getId(),
                    order.getOrderNo(),
                    channel,
                    success
                );
            }
        }
    }
}
