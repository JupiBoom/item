package com.qbk.entity;

import com.qbk.entity.enums.NotificationChannelEnum;
import com.qbk.entity.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户通知偏好设置实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserNotificationPreference implements Serializable {

    private static final long serialVersionUID = 1234567890123456789L;

    /**
     * ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 订单状态
     * @see OrderStatusEnum
     */
    private Integer orderStatus;

    /**
     * 通知渠道
     * @see NotificationChannelEnum
     */
    private Integer channel;

    /**
     * 是否启用
     */
    private Boolean isEnabled;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 更新时间
     */
    private LocalDateTime updateDate;
}