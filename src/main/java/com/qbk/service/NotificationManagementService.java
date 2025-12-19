package com.qbk.service;

import com.qbk.entity.NotificationRecord;
import com.qbk.entity.UserNotificationPreference;
import com.qbk.entity.enums.NotificationChannelEnum;
import com.qbk.entity.enums.OrderStatusEnum;

import java.util.List;

/**
 * 通知管理服务接口
 */
public interface NotificationManagementService {
    
    /**
     * 设置用户通知偏好
     * @param userId 用户ID
     * @param orderStatus 订单状态
     * @param channel 通知渠道
     * @param enabled 是否启用
     */
    void setUserNotificationPreference(Integer userId, Integer orderStatus, 
                                      Integer channel, Boolean enabled);
    
    /**
     * 获取用户通知偏好
     * @param userId 用户ID
     * @return 用户通知偏好列表
     */
    List<UserNotificationPreference> getUserNotificationPreferences(Integer userId);
    
    /**
     * 获取用户通知记录
     * @param userId 用户ID
     * @return 用户通知记录列表
     */
    List<NotificationRecord> getUserNotificationRecords(Integer userId);
    
    /**
     * 获取所有通知记录
     * @param page 页码
     * @param size 每页大小
     * @return 通知记录列表
     */
    List<NotificationRecord> getAllNotificationRecords(Integer page, Integer size);
    
    /**
     * 手动重试通知
     * @param recordId 通知记录ID
     * @return 重试是否成功
     */
    boolean retryNotification(Integer recordId);
}