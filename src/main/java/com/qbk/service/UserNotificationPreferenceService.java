package com.qbk.service;

import com.qbk.entity.UserNotificationPreference;
import com.qbk.entity.NotificationChannel;

import java.util.List;

/**
 * 用户通知偏好设置服务接口
 */
public interface UserNotificationPreferenceService {
    /**
     * 获取用户的所有通知偏好
     * @param userId 用户ID
     * @return 通知偏好列表
     */
    List<UserNotificationPreference> getUserPreferences(Long userId);
    
    /**
     * 获取用户指定渠道的通知偏好
     * @param userId 用户ID
     * @param channel 通知渠道
     * @return 通知偏好
     */
    UserNotificationPreference getUserPreference(Long userId, NotificationChannel channel);
    
    /**
     * 保存用户通知偏好
     * @param preference 通知偏好
     * @return 保存后的通知偏好
     */
    UserNotificationPreference saveUserPreference(UserNotificationPreference preference);
    
    /**
     * 删除用户通知偏好
     * @param id 偏好ID
     */
    void deleteUserPreference(Long id);
}
