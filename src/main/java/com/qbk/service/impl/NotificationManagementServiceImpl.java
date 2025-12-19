package com.qbk.service.impl;

import com.qbk.entity.NotificationRecord;
import com.qbk.entity.UserNotificationPreference;
import com.qbk.mapper.NotificationRecordMapper;
import com.qbk.mapper.UserNotificationPreferenceMapper;
import com.qbk.service.NotificationManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知管理服务实现类
 */
@Slf4j
@Service
public class NotificationManagementServiceImpl implements NotificationManagementService {
    
    @Resource
    private UserNotificationPreferenceMapper userNotificationPreferenceMapper;
    
    @Resource
    private NotificationRecordMapper notificationRecordMapper;
    
    @Override
    public void setUserNotificationPreference(Integer userId, Integer orderStatus, 
                                             Integer channel, Boolean enabled) {
        log.info("设置用户通知偏好: 用户ID={}, 订单状态={}, 渠道={}, 启用={}", 
                 userId, orderStatus, channel, enabled);
        
        UserNotificationPreference existing = userNotificationPreferenceMapper
            .getByUserIdOrderStatusAndChannel(userId, orderStatus, channel);
        
        if (existing != null) {
            existing.setIsEnabled(enabled);
            existing.setUpdateDate(LocalDateTime.now());
            userNotificationPreferenceMapper.update(existing);
        } else {
            UserNotificationPreference newPreference = UserNotificationPreference.builder()
                .userId(userId)
                .orderStatus(orderStatus)
                .channel(channel)
                .isEnabled(enabled)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
            userNotificationPreferenceMapper.insert(newPreference);
        }
    }
    
    @Override
    public List<UserNotificationPreference> getUserNotificationPreferences(Integer userId) {
        log.info("获取用户通知偏好: 用户ID={}", userId);
        return userNotificationPreferenceMapper.getByUserId(userId);
    }
    
    @Override
    public List<NotificationRecord> getUserNotificationRecords(Integer userId) {
        log.info("获取用户通知记录: 用户ID={}", userId);
        return notificationRecordMapper.getByUserId(userId);
    }
    
    @Override
    public List<NotificationRecord> getAllNotificationRecords(Integer page, Integer size) {
        log.info("获取所有通知记录: 页码={}, 每页大小={}", page, size);
        return notificationRecordMapper.getAll(page, size);
    }
    
    @Override
    public boolean retryNotification(Integer recordId) {
        log.info("手动重试通知: 记录ID={}", recordId);
        NotificationRecord record = notificationRecordMapper.getById(recordId);
        
        if (record == null) {
            log.error("通知记录不存在: ID={}", recordId);
            return false;
        }
        
        // 将记录标记为待重试
        record.setNextRetryTime(LocalDateTime.now());
        notificationRecordMapper.update(record);
        
        return true;
    }
}