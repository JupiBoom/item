package com.qbk.service;

/**
 * 邮件通知服务接口
 */
public interface EmailNotificationService {
    /**
     * 发送邮件
     * @param email 邮箱地址
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return 发送是否成功
     */
    boolean sendEmail(String email, String subject, String content);
}
