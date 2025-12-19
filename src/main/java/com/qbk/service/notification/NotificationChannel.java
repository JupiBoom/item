package com.qbk.service.notification;

import com.qbk.entity.dto.OrderInfoDTO;

/**
 * 通知渠道接口
 */
public interface NotificationChannel {
    
    /**
     * 发送通知
     * @param orderInfo 订单信息
     * @param content 通知内容
     * @return 发送是否成功
     */
    boolean send(OrderInfoDTO orderInfo, String content);
    
    /**
     * 获取渠道类型
     * @return 渠道类型代码
     */
    Integer getChannelType();
}