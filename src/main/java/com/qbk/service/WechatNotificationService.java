package com.qbk.service;

/**
 * 微信通知服务接口
 */
public interface WechatNotificationService {
    /**
     * 发送微信消息
     * @param openId 用户微信OpenID
     * @param content 消息内容
     * @return 发送是否成功
     */
    boolean sendWechatMessage(String openId, String content);
}
