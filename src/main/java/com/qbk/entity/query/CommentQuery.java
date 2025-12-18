package com.qbk.entity.query;

import lombok.Data;

/**
 * 评价查询参数
 */
@Data
public class CommentQuery {
    /**
     * 商品ID
     */
    private Long itemId;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 排序方式：time_desc(按时间降序)、time_asc(按时间升序)、score_desc(按评分降序)、score_asc(按评分升序)
     */
    private String sortBy;
    
    /**
     * 页码
     */
    private Integer pageNum;
    
    /**
     * 每页数量
     */
    private Integer pageSize;
}