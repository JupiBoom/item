package com.qbk.listener;

import com.qbk.entity.NotificationRecord;
import com.qbk.entity.NotificationTemplate;
import com.qbk.entity.UserNotificationPreference;
import com.qbk.entity.dto.OrderInfoDTO;
import com.qbk.event.OrderStatusChangeEvent;
import com.qbk.mapper.NotificationRecordMapper;
import com.qbk.mapper.NotificationTemplateMapper;
import com.qbk.mapper.UserNotificationPreferenceMapper;
import com.qbk.service.notification.NotificationChannel;
import com.qbk.service.notification.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订单状态变更监听器
 */
@Slf4j
@Component
public class OrderStatusChangeListener {
    
    @Autowired
    private List<NotificationChannel> notificationChannels;
    
    @Autowired
    private TemplateService templateService;
    
    @Autowired
    private UserNotificationPreferenceMapper userNotificationPreferenceMapper;
    
    @Autowired
    private NotificationTemplateMapper notificationTemplateMapper;
    
    @Autowired
    private NotificationRecordMapper notificationRecordMapper;
    
    /**
     * 处理订单状态变更事件
     * @param event 订单状态变更事件
     */
    @EventListener
    public void handleOrderStatusChange(OrderStatusChangeEvent event) {
        OrderInfoDTO orderInfo = event.getOrderInfo();
        log.info("收到订单状态变更事件，订单ID: {}, 状态: {}", 
                 orderInfo.getOrderId(), orderInfo.getOrderStatus());
        
        try {
            // 获取用户通知偏好
            List<UserNotificationPreference> preferences = getNotificationPreferences(orderInfo.getUserId(), orderInfo.getOrderStatus());
            
            // 获取通知模板
            Map<Integer, NotificationTemplate> templates = getNotificationTemplates(orderInfo.getOrderStatus());
            
            // 发送通知
            sendNotifications(orderInfo, preferences, templates);
            
        } catch (Exception e) {
            log.error("处理订单状态变更通知失败，订单ID: {}", orderInfo.getOrderId(), e);
        }
    }
    
    /**
     * 获取用户通知偏好
     * @param userId 用户ID
     * @param orderStatus 订单状态
     * @return 用户通知偏好列表
     */
    private List<UserNotificationPreference> getNotificationPreferences(Integer userId, Integer orderStatus) {
        log.info("获取用户通知偏好: 用户ID={}, 订单状态={}", userId, orderStatus);
        return userNotificationPreferenceMapper.getByUserId(userId);
    }
    
    /**
     * 获取通知模板
     * @param orderStatus 订单状态
     * @return 通知模板映射
     */
    private Map<Integer, NotificationTemplate> getNotificationTemplates(Integer orderStatus) {
        log.info("获取通知模板: 订单状态={}", orderStatus);
        List<NotificationTemplate> templates = notificationTemplateMapper.getByOrderStatus(orderStatus);
        return templates.stream()
            .collect(Collectors.toMap(NotificationTemplate::getChannel, template -> template));
    }
    
    /**
     * 发送通知
     * @param orderInfo 订单信息
     * @param preferences 用户通知偏好
     * @param templates 通知模板
     */
    private void sendNotifications(OrderInfoDTO orderInfo, 
                                  List<UserNotificationPreference> preferences, 
                                  Map<Integer, NotificationTemplate> templates) {
        // 按渠道类型分组通知渠道
        Map<Integer, NotificationChannel> channelMap = notificationChannels.stream()
            .collect(Collectors.toMap(NotificationChannel::getChannelType, channel -> channel));
        
        for (UserNotificationPreference preference : preferences) {
            if (preference.getIsEnabled()) {
                Integer channelType = preference.getChannel();
                NotificationChannel channel = channelMap.get(channelType);
                NotificationTemplate template = templates.get(channelType);
                
                if (channel != null && template != null && template.getIsEnabled()) {
                    String content = templateService.replaceVariables(template.getContent(), orderInfo);
                    boolean success = channel.send(orderInfo, content);
                    
                    // 保存通知记录
                    saveNotificationRecord(orderInfo, channelType, content, success, 
                                           success ? null : "发送失败");
                }
            }
        }
    }
    
    /**
     * 保存通知记录
     * @param orderInfo 订单信息
     * @param channel 通知渠道
     * @param content 通知内容
     * @param status 发送状态
     * @param failReason 失败原因
     */
    private void saveNotificationRecord(OrderInfoDTO orderInfo, Integer channel, 
                                       String content, boolean status, String failReason) {
        NotificationRecord record = NotificationRecord.builder()
            .userId(orderInfo.getUserId())
            .orderId(orderInfo.getOrderId())
            .orderStatus(orderInfo.getOrderStatus())
            .channel(channel)
            .content(content)
            .status(status)
            .failReason(failReason)
            .retryCount(0)
            .createDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build();
            
        log.info("保存通知记录: {}", record);
        notificationRecordMapper.insert(record);
    }
}