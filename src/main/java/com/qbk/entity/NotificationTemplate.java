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
 * 通知模板实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationTemplate implements Serializable {

    private static final long serialVersionUID = -5474923498508234598L;

    /**
     * 模板ID
     */
    private Integer id;

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
     * 模板标题
     */
    private String title;

    /**
     * 模板内容
     */
    private String content;

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