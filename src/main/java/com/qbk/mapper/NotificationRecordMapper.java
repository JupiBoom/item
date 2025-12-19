package com.qbk.mapper;

import com.qbk.entity.NotificationRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知记录Mapper
 */
@Mapper
public interface NotificationRecordMapper {
    /**
     * 插入通知记录
     * @param record 通知记录对象
     * @return 受影响的行数
     */
    int insert(NotificationRecord record);

    /**
     * 更新通知记录
     * @param record 通知记录对象
     * @return 受影响的行数
     */
    int update(NotificationRecord record);

    /**
     * 根据记录ID获取通知记录
     * @param id 记录ID
     * @return 通知记录对象
     */
    NotificationRecord getById(@Param("id") Integer id);

    /**
     * 根据用户ID获取通知记录列表
     * @param userId 用户ID
     * @return 通知记录列表
     */
    List<NotificationRecord> getByUserId(@Param("userId") Integer userId);

    /**
     * 获取所有通知记录（分页）
     * @param page 页码
     * @param size 每页大小
     * @return 通知记录列表
     */
    List<NotificationRecord> getAll(@Param("page") Integer page, @Param("size") Integer size);

    /**
     * 获取需要重试的通知记录
     * @param currentTime 当前时间
     * @return 通知记录列表
     */
    List<NotificationRecord> getRetryRecords(@Param("currentTime") LocalDateTime currentTime);
}
