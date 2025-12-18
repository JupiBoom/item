package com.qbk.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 商品评价数据传输对象
 */
@Data
public class ItemCommentDTO {
    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    private Integer itemId;
    /**
     * 订单ID
     */
    @NotNull(message = "订单ID不能为空")
    private Integer orderId;
    /**
     * 评分（1-5星）
     */
    @NotNull(message = "评分不能为空")
    @Min(value = 1, message = "评分不能低于1星")
    @Max(value = 5, message = "评分不能高于5星")
    private Integer score;
    /**
     * 评价内容
     */
    private String content;
}