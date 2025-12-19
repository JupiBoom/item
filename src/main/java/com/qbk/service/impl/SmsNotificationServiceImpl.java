package com.qbk.service.impl;

import com.qbk.service.SmsNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 短信通知服务实现类
 */
@Service
@Slf4j
public class SmsNotificationServiceImpl implements SmsNotificationService {
    
    @Override
    public boolean sendSms(String phoneNumber, String content) {
        try {
            // 这里实现真实的短信发送逻辑
            // 例如调用第三方短信服务API
            log.info("Sending SMS to: {}, content: {}", phoneNumber, content);
            
            // 模拟发送成功
            return true;
        } catch (Exception e) {
            log.error("Failed to send SMS to: {}", phoneNumber, e);
            return false;
        }
    }
}
