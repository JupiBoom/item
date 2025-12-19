package com.qbk.service.impl;

import com.qbk.entity.UserNotificationPreference;
import com.qbk.entity.NotificationChannel;
import com.qbk.service.UserNotificationPreferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户通知偏好设置服务实现类
 */
@Service
@Slf4j
public class UserNotificationPreferenceServiceImpl implements UserNotificationPreferenceService {
    
    // 模拟存储
    private final Map<Long, List<UserNotificationPreference>> userPreferences = new HashMap<>();
    
    @Override
    public List<UserNotificationPreference> getUserPreferences(Long userId) {
        return userPreferences.getOrDefault(userId, new ArrayList<>());
    }
    
    @Override
    public UserNotificationPreference getUserPreference(Long userId, NotificationChannel channel) {
        List<UserNotificationPreference> preferences = getUserPreferences(userId);
        for (UserNotificationPreference preference : preferences) {
            if (preference.getChannel() == channel) {
                return preference;
            }
        }
        return null;
    }
    
    @Override
    public UserNotificationPreference saveUserPreference(UserNotificationPreference preference) {
        List<UserNotificationPreference> preferences = userPreferences.computeIfAbsent(
            preference.getUserId(), k -> new ArrayList<>()
        );
        
        // 移除旧的相同渠道的偏好
        preferences.removeIf(p -> p.getChannel() == preference.getChannel());
        preferences.add(preference);
        
        log.info("Saved user notification preference for user: {}, channel: {}", 
            preference.getUserId(), preference.getChannel());
        
        return preference;
    }
    
    @Override
    public void deleteUserPreference(Long id) {
        // 简化实现，实际项目中需要根据ID删除
    }
}
