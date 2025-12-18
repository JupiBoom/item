package com.qbk.entity.param;

import lombok.Data;
import javax.validation.constraints.*;

/**
 * 添加评价参数
 */
@Data
public class CommentAddParam {
    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    private Long itemId;
    
    /**
     * 订单ID
     */
    @NotNull(message = "订单ID不能为空")
    private Long orderId;
    
    /**
     * 评价内容
     */
    @NotBlank(message = "评价内容不能为空")
    @Size(max = 500, message = "评价内容不能超过500字")
    private String content;
    
    /**
     * 评分（1-5星）
     */
    @NotNull(message = "评分不能为空")
    @Min(value = 1, message = "评分不能低于1星")
    @Max(value = 5, message = "评分不能高于5星")
    private Integer score;
}