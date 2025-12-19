package com.qbk.mapper;

import com.qbk.entity.UserNotificationPreference;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户通知偏好设置Mapper
 */
@Mapper
public interface UserNotificationPreferenceMapper {
    /**
     * 插入用户通知偏好
     * @param preference 用户通知偏好对象
     * @return 受影响的行数
     */
    int insert(UserNotificationPreference preference);

    /**
     * 更新用户通知偏好
     * @param preference 用户通知偏好对象
     * @return 受影响的行数
     */
    int update(UserNotificationPreference preference);

    /**
     * 根据用户ID获取通知偏好列表
     * @param userId 用户ID
     * @return 通知偏好列表
     */
    List<UserNotificationPreference> getByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户ID、订单状态和渠道获取通知偏好
     * @param userId 用户ID
     * @param orderStatus 订单状态
     * @param channel 通知渠道
     * @return 用户通知偏好对象
     */
    UserNotificationPreference getByUserIdOrderStatusAndChannel(
            @Param("userId") Integer userId,
            @Param("orderStatus") Integer orderStatus,
            @Param("channel") Integer channel);
}
