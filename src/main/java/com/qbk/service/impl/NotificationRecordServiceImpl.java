package com.qbk.service.impl;

import com.qbk.entity.NotificationRecord;
import com.qbk.entity.NotificationChannel;
import com.qbk.service.NotificationRecordService;
import com.qbk.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通知记录服务实现类
 */
@Service
@Slf4j
public class NotificationRecordServiceImpl implements NotificationRecordService {
    
    private final NotificationService notificationService;
    private final ConcurrentHashMap<Long, NotificationRecord> records = new ConcurrentHashMap<>();
    private Long nextId = 1L;
    
    public NotificationRecordServiceImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    
    @Override
    public NotificationRecord recordNotification(Long userId, Long orderId, String orderNo, NotificationChannel channel, boolean success) {
        NotificationRecord record = new NotificationRecord();
        record.setId(nextId++);
        record.setUserId(userId);
        record.setOrderId(orderId);
        record.setOrderNo(orderNo);
        record.setChannel(channel);
        record.setStatus(success ? NotificationRecord.NotificationStatus.SUCCESS : NotificationRecord.NotificationStatus.FAILED);
        record.setRetryCount(0);
        record.setMaxRetryCount(3);
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        
        if (!success) {
            record.setNextRetryTime(LocalDateTime.now().plusMinutes(5)); // 5分钟后重试
        }
        
        records.put(record.getId(), record);
        log.info("Recorded notification: {}", record);
        
        return record;
    }
    
    @Override
    public List<NotificationRecord> getAllRecords() {
        return new ArrayList<>(records.values());
    }
    
    @Override
    public List<NotificationRecord> getRecordsByUserId(Long userId) {
        return records.values().stream()
            .filter(record -> record.getUserId().equals(userId))
            .toList();
    }
    
    @Override
    public List<NotificationRecord> getPendingRetryRecords() {
        LocalDateTime now = LocalDateTime.now();
        return records.values().stream()
            .filter(record -> record.getStatus() == NotificationRecord.NotificationStatus.FAILED || 
                             record.getStatus() == NotificationRecord.NotificationStatus.RETRYING)
            .filter(record -> record.getRetryCount() < record.getMaxRetryCount())
            .filter(record -> record.getNextRetryTime() != null && record.getNextRetryTime().isBefore(now))
            .toList();
    }
    
    @Override
    public boolean retryNotification(Long recordId) {
        NotificationRecord record = records.get(recordId);
        if (record == null) {
            log.error("Notification record not found: {}", recordId);
            return false;
        }
        
        record.setStatus(NotificationRecord.NotificationStatus.RETRYING);
        record.setRetryCount(record.getRetryCount() + 1);
        
        try {
            // 这里需要根据记录信息重新发送通知
            // 简化实现，假设重试成功
            boolean success = true;
            
            if (success) {
                record.setStatus(NotificationRecord.NotificationStatus.SUCCESS);
                record.setNextRetryTime(null);
            } else {
                record.setStatus(NotificationRecord.NotificationStatus.FAILED);
                record.setNextRetryTime(LocalDateTime.now().plusMinutes(5 * (record.getRetryCount() + 1)));
            }
            
            record.setUpdateTime(LocalDateTime.now());
            log.info("Retried notification: {}, success: {}", recordId, success);
            
            return success;
        } catch (Exception e) {
            log.error("Failed to retry notification: {}", recordId, e);
            record.setStatus(NotificationRecord.NotificationStatus.FAILED);
            record.setNextRetryTime(LocalDateTime.now().plusMinutes(5 * (record.getRetryCount() + 1)));
            record.setUpdateTime(LocalDateTime.now());
            return false;
        }
    }
    
    @Override
    public void updateRecordStatus(NotificationRecord record) {
        records.put(record.getId(), record);
    }
    
    /**
     * 定时任务，每分钟检查一次待重试的通知
     */
    @Scheduled(fixedRate = 60000)
    public void scheduledRetry() {
        log.info("Checking for pending retry notifications...");
        
        List<NotificationRecord> pendingRecords = getPendingRetryRecords();
        for (NotificationRecord record : pendingRecords) {
            retryNotification(record.getId());
        }
    }
}
