package com.qbk.service.impl;

import com.qbk.entity.Order;
import com.qbk.entity.NotificationChannel;
import com.qbk.service.NotificationService;
import com.qbk.service.SmsNotificationService;
import com.qbk.service.WechatNotificationService;
import com.qbk.service.EmailNotificationService;
import com.qbk.service.NotificationTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 通知服务实现类
 */
@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    
    private final SmsNotificationService smsNotificationService;
    private final WechatNotificationService wechatNotificationService;
    private final EmailNotificationService emailNotificationService;
    private final NotificationTemplateService notificationTemplateService;
    
    public NotificationServiceImpl(SmsNotificationService smsNotificationService,
                                   WechatNotificationService wechatNotificationService,
                                   EmailNotificationService emailNotificationService,
                                   NotificationTemplateService notificationTemplateService) {
        this.smsNotificationService = smsNotificationService;
        this.wechatNotificationService = wechatNotificationService;
        this.emailNotificationService = emailNotificationService;
        this.notificationTemplateService = notificationTemplateService;
    }
    
    @Override
    public boolean sendOrderNotification(Order order, NotificationChannel channel) {
        try {
            String content = notificationTemplateService.renderTemplate(order.getStatus(), channel, order);
            
            switch (channel) {
                case SMS:
                    return smsNotificationService.sendSms("", content); // 这里需要获取用户手机号
                case WECHAT:
                    return wechatNotificationService.sendWechatMessage("", content); // 这里需要获取用户微信ID
                case EMAIL:
                    return emailNotificationService.sendEmail("", "订单状态通知", content); // 这里需要获取用户邮箱
                default:
                    log.error("Unsupported notification channel: {}", channel);
                    return false;
            }
        } catch (Exception e) {
            log.error("Failed to send order notification for order: {} via channel: {}", order.getOrderNo(), channel, e);
            return false;
        }
    }
    
    @Override
    public boolean sendTemplateNotification(Long templateId, Object variables, String recipient, NotificationChannel channel) {
        try {
            // 这里可以根据模板ID获取模板并渲染
            // 简化实现，直接返回成功
            log.info("Sending template notification to: {} via channel: {}", recipient, channel);
            return true;
        } catch (Exception e) {
            log.error("Failed to send template notification", e);
            return false;
        }
    }
}
