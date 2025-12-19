package com.qbk.service.notification;

import com.qbk.entity.NotificationRecord;
import com.qbk.entity.dto.OrderInfoDTO;
import com.qbk.mapper.NotificationRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知失败重试服务
 */
@Slf4j
@Service
public class NotificationRetryService {
    
    @Autowired
    private List<NotificationChannel> notificationChannels;
    
    @Autowired
    private NotificationRecordMapper notificationRecordMapper;
    
    /**
     * 定时重试失败的通知
     * 每5分钟执行一次
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void retryFailedNotifications() {
        log.info("开始重试失败的通知");
        
        try {
            // 获取需要重试的通知记录
            List<NotificationRecord> failedRecords = getFailedNotifications();
            
            for (NotificationRecord record : failedRecords) {
                // 检查是否达到最大重试次数
                if (record.getRetryCount() >= 3) {
                    log.warn("通知重试次数已达上限，不再重试，记录ID: {}", record.getId());
                    continue;
                }
                
                // 检查是否到了下次重试时间
                if (record.getNextRetryTime() != null && 
                    record.getNextRetryTime().isAfter(LocalDateTime.now())) {
                    continue;
                }
                
                // 重试发送
                boolean success = retryNotification(record);
                
                // 更新记录
                updateNotificationRecord(record, success);
            }
            
        } catch (Exception e) {
            log.error("重试失败通知时发生错误", e);
        }
    }
    
    /**
     * 获取需要重试的失败通知
     * @return 失败通知记录列表
     */
    private List<NotificationRecord> getFailedNotifications() {
        log.info("获取需要重试的失败通知");
        return notificationRecordMapper.getRetryRecords(LocalDateTime.now());
    }
    
    /**
     * 重试发送通知
     * @param record 通知记录
     * @return 发送是否成功
     */
    private boolean retryNotification(NotificationRecord record) {
        // 构建订单信息（实际应从数据库获取）
        OrderInfoDTO orderInfo = OrderInfoDTO.builder()
            .orderId(record.getOrderId())
            .userId(record.getUserId())
            .orderStatus(record.getOrderStatus())
            .build();
        
        // 查找对应的通知渠道
        for (NotificationChannel channel : notificationChannels) {
            if (channel.getChannelType().equals(record.getChannel())) {
                return channel.send(orderInfo, record.getContent());
            }
        }
        
        return false;
    }
    
    /**
     * 更新通知记录
     * @param record 通知记录
     * @param success 发送是否成功
     */
    private void updateNotificationRecord(NotificationRecord record, boolean success) {
        record.setStatus(success);
        record.setRetryCount(record.getRetryCount() + 1);
        record.setUpdateDate(LocalDateTime.now());
        
        if (!success) {
            // 设置下次重试时间（指数退避）
            record.setNextRetryTime(LocalDateTime.now().plusMinutes((long) Math.pow(2, record.getRetryCount())));
        }
        
        log.info("更新通知记录: {}", record);
        notificationRecordMapper.update(record);
    }
}