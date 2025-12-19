package com.qbk.service.impl;

import com.qbk.entity.OrderStatus;
import com.qbk.entity.NotificationChannel;
import com.qbk.entity.NotificationTemplate;
import com.qbk.service.NotificationTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知模板服务实现类
 */
@Service
@Slf4j
public class NotificationTemplateServiceImpl implements NotificationTemplateService {
    
    // 模拟模板存储
    private final Map<String, NotificationTemplate> templates = new HashMap<>();
    
    public NotificationTemplateServiceImpl() {
        // 初始化默认模板
        initDefaultTemplates();
    }
    
    private void initDefaultTemplates() {
        // 支付成功模板 - 短信
        NotificationTemplate paidSmsTemplate = new NotificationTemplate();
        paidSmsTemplate.setOrderStatus(OrderStatus.PAID);
        paidSmsTemplate.setChannel(NotificationChannel.SMS);
        paidSmsTemplate.setTitle("支付成功通知");
        paidSmsTemplate.setContent("您的订单{orderNo}已支付成功，商品：{productName}，感谢您的购买！");
        saveTemplate(paidSmsTemplate);
        
        // 发货模板 - 微信
        NotificationTemplate shippedWechatTemplate = new NotificationTemplate();
        shippedWechatTemplate.setOrderStatus(OrderStatus.SHIPPED);
        shippedWechatTemplate.setChannel(NotificationChannel.WECHAT);
        shippedWechatTemplate.setTitle("订单已发货");
        shippedWechatTemplate.setContent("尊敬的用户，您的订单{orderNo}已发货，商品：{productName}，请注意查收！");
        saveTemplate(shippedWechatTemplate);
        
        // 签收模板 - 邮件
        NotificationTemplate deliveredEmailTemplate = new NotificationTemplate();
        deliveredEmailTemplate.setOrderStatus(OrderStatus.DELIVERED);
        deliveredEmailTemplate.setChannel(NotificationChannel.EMAIL);
        deliveredEmailTemplate.setTitle("订单已签收");
        deliveredEmailTemplate.setContent("亲爱的用户，您的订单{orderNo}已签收，商品：{productName}。如有问题请及时联系客服！");
        saveTemplate(deliveredEmailTemplate);
    }
    
    @Override
    public NotificationTemplate getTemplate(OrderStatus orderStatus, NotificationChannel channel) {
        String key = generateKey(orderStatus, channel);
        return templates.get(key);
    }
    
    @Override
    public String renderTemplate(OrderStatus orderStatus, NotificationChannel channel, Object variables) {
        NotificationTemplate template = getTemplate(orderStatus, channel);
        if (template == null) {
            log.error("No template found for order status: {} and channel: {}", orderStatus, channel);
            return null;
        }
        
        String content = template.getContent();
        
        // 使用反射获取变量值进行替换
        try {
            java.lang.reflect.Field[] fields = variables.getClass().getDeclaredFields();
            for (java.lang.reflect.Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(variables);
                if (value != null) {
                    content = content.replace("{" + fieldName + "}", value.toString());
                }
            }
        } catch (Exception e) {
            log.error("Failed to render template", e);
        }
        
        return content;
    }
    
    @Override
    public NotificationTemplate saveTemplate(NotificationTemplate template) {
        String key = generateKey(template.getOrderStatus(), template.getChannel());
        templates.put(key, template);
        return template;
    }
    
    @Override
    public void deleteTemplate(Long id) {
        // 简化实现，实际项目中需要根据ID删除
    }
    
    @Override
    public List<NotificationTemplate> getAllTemplates() {
        return templates.values().stream().toList();
    }
    
    private String generateKey(OrderStatus orderStatus, NotificationChannel channel) {
        return orderStatus.name() + "_" + channel.name();
    }
}
