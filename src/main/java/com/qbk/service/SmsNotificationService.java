package com.qbk.service;

/**
 * 短信通知服务接口
 */
public interface SmsNotificationService {
    /**
     * 发送短信
     * @param phoneNumber 手机号
     * @param content 短信内容
     * @return 发送是否成功
     */
    boolean sendSms(String phoneNumber, String content);
}
