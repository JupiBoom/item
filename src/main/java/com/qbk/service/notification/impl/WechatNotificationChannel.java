package com.qbk.service.notification.impl;

import com.qbk.entity.dto.OrderInfoDTO;
import com.qbk.entity.enums.NotificationChannelEnum;
import com.qbk.service.notification.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 微信通知渠道实现
 */
@Slf4j
@Component
public class WechatNotificationChannel implements NotificationChannel {
    
    @Override
    public boolean send(OrderInfoDTO orderInfo, String content) {
        try {
            // TODO: 实现真实的微信通知发送逻辑
            log.info("发送微信通知给用户 {}，订单 {}，内容：{}", 
                     orderInfo.getUserId(), orderInfo.getOrderId(), content);
            return true;
        } catch (Exception e) {
            log.error("微信通知发送失败，用户 {}，订单 {}", 
                      orderInfo.getUserId(), orderInfo.getOrderId(), e);
            return false;
        }
    }
    
    @Override
    public Integer getChannelType() {
        return NotificationChannelEnum.WECHAT.getCode();
    }
}