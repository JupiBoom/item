package com.qbk.service;

import com.qbk.entity.Order;
import com.qbk.entity.NotificationChannel;

/**
 * 通知服务接口
 */
public interface NotificationService {
    /**
     * 发送订单状态通知
     * @param order 订单信息
     * @param channel 通知渠道
     * @return 发送是否成功
     */
    boolean sendOrderNotification(Order order, NotificationChannel channel);
    
    /**
     * 发送模板化通知
     * @param templateId 模板ID
     * @param variables 模板变量
     * @param recipient 接收者
     * @param channel 通知渠道
     * @return 发送是否成功
     */
    boolean sendTemplateNotification(Long templateId, Object variables, String recipient, NotificationChannel channel);
}
