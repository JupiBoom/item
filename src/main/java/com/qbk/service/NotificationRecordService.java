package com.qbk.service;

import com.qbk.entity.NotificationRecord;
import com.qbk.entity.NotificationChannel;

import java.util.List;

/**
 * 通知记录服务接口
 */
public interface NotificationRecordService {
    /**
     * 记录通知
     * @param userId 用户ID
     * @param orderId 订单ID
     * @param orderNo 订单号
     * @param channel 通知渠道
     * @param success 发送是否成功
     * @return 通知记录
     */
    NotificationRecord recordNotification(Long userId, Long orderId, String orderNo, NotificationChannel channel, boolean success);
    
    /**
     * 获取所有通知记录
     * @return 通知记录列表
     */
    List<NotificationRecord> getAllRecords();
    
    /**
     * 获取用户的通知记录
     * @param userId 用户ID
     * @return 通知记录列表
     */
    List<NotificationRecord> getRecordsByUserId(Long userId);
    
    /**
     * 获取待重试的通知记录
     * @return 待重试的通知记录列表
     */
    List<NotificationRecord> getPendingRetryRecords();
    
    /**
     * 重试发送通知
     * @param recordId 记录ID
     * @return 重试是否成功
     */
    boolean retryNotification(Long recordId);
    
    /**
     * 更新通知记录状态
     * @param record 通知记录
     */
    void updateRecordStatus(NotificationRecord record);
}
