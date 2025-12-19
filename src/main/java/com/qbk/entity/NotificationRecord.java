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
 * 通知记录实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRecord implements Serializable {

    private static final long serialVersionUID = 3456789012345678901L;

    /**
     * 记录ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 订单ID
     */
    private String orderId;

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
     * 通知内容
     */
    private String content;

    /**
     * 通知状态：0-失败，1-成功
     */
    private Boolean status;

    /**
     * 失败原因
     */
    private String failReason;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 下次重试时间
     */
    private LocalDateTime nextRetryTime;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 更新时间
     */
    private LocalDateTime updateDate;
}