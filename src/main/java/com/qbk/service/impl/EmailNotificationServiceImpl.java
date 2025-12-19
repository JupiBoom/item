package com.qbk.service.impl;

import com.qbk.service.EmailNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 邮件通知服务实现类
 */
@Service
@Slf4j
public class EmailNotificationServiceImpl implements EmailNotificationService {
    
    @Override
    public boolean sendEmail(String email, String subject, String content) {
        try {
            // 这里实现真实的邮件发送逻辑
            // 例如使用JavaMailSender发送邮件
            log.info("Sending email to: {}, subject: {}, content: {}", email, subject, content);
            
            // 模拟发送成功
            return true;
        } catch (Exception e) {
            log.error("Failed to send email to: {}", email, e);
            return false;
        }
    }
}
