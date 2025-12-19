package com.qbk.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单信息 DTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoDTO implements Serializable {

    private static final long serialVersionUID = 987654321098765432L;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 商品名称列表
     */
    private List<String> productNames;

    /**
     * 订单金额
     */
    private BigDecimal totalAmount;

    /**
     * 下单时间
     */
    private LocalDateTime orderTime;

    /**
     * 发货时间
     */
    private LocalDateTime shipTime;

    /**
     * 签收时间
     */
    private LocalDateTime deliverTime;
}