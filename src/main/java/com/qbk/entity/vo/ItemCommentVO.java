package com.qbk.entity.vo;

import lombok.Data;
import java.util.Date;

/**
 * 商品评价展示对象
 */
@Data
public class ItemCommentVO {
    /**
     * 评价ID
     */
    private Integer id;
    /**
     * 商品ID
     */
    private Integer itemId;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户昵称
     */
    private String userNickname;
    /**
     * 评分（1-5星）
     */
    private Integer score;
    /**
     * 评价内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date createTime;
}
