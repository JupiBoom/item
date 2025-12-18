package com.qbk.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 商品评价统计实体类
 */
@Data
public class CommentStatistics {
    /**
     * 统计ID
     */
    private Long id;
    
    /**
     * 商品ID
     */
    private Long itemId;
    
    /**
     * 总评价数
     */
    private Integer totalCount;
    
    /**
     * 好评数（4-5星）
     */
    private Integer positiveCount;
    
    /**
     * 中评数（3星）
     */
    private Integer neutralCount;
    
    /**
     * 差评数（1-2星）
     */
    private Integer negativeCount;
    
    /**
     * 平均评分
     */
    private Double averageScore;
    
    /**
     * 好评率
     */
    private Double positiveRate;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}