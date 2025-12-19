package com.qbk.service;

import com.qbk.entity.OrderStatus;
import com.qbk.entity.NotificationChannel;
import com.qbk.entity.NotificationTemplate;

import java.util.List;
import java.util.Map;

/**
 * 通知模板服务接口
 */
public interface NotificationTemplateService {
    /**
     * 根据订单状态和通知渠道获取模板
     * @param orderStatus 订单状态
     * @param channel 通知渠道
     * @return 通知模板
     */
    NotificationTemplate getTemplate(OrderStatus orderStatus, NotificationChannel channel);
    
    /**
     * 渲染模板
     * @param orderStatus 订单状态
     * @param channel 通知渠道
     * @param variables 模板变量
     * @return 渲染后的内容
     */
    String renderTemplate(OrderStatus orderStatus, NotificationChannel channel, Object variables);
    
    /**
     * 保存或更新模板
     * @param template 通知模板
     * @return 保存后的模板
     */
    NotificationTemplate saveTemplate(NotificationTemplate template);
    
    /**
     * 删除模板
     * @param id 模板ID
     */
    void deleteTemplate(Long id);
    
    /**
     * 获取所有模板
     * @return 模板列表
     */
    List<NotificationTemplate> getAllTemplates();
}
