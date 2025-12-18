package com.qbk.entity;

import lombok.Data;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 商品评价统计实体类
 */
@Data
public class ItemCommentStat {
    /**
     * 统计ID
     */
    private Integer id;
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
     * 更新时间
     */
    private Date updateTime;
}