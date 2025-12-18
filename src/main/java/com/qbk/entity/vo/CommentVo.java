package com.qbk.entity.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 评价展示VO
 */
@Data
public class CommentVo {
    /**
     * 评价ID
     */
    private Long id;
    
    /**
     * 商品ID
     */
    private Long itemId;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 评价内容
     */
    private String content;
    
    /**
     * 评分（1-5星）
     */
    private Integer score;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}