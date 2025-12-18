package com.qbk.entity.vo;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 商品评价统计展示对象
 */
@Data
public class ItemCommentStatVO {
    /**
     * 商品ID
     */
    private Integer itemId;
    /**
     * 总评价数
     */
    private Integer totalComments;
    /**
     * 好评数（4-5星）
     */
    private Integer goodComments;
    /**
     * 中评数（3星）
     */
    private Integer mediumComments;
    /**
     * 差评数（1-2星）
     */
    private Integer badComments;
    /**
     * 平均评分
     */
    private BigDecimal avgScore;
    /**
     * 好评率
     */
    private BigDecimal goodRate;
}
