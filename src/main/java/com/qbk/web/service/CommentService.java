package com.qbk.web.service;

import com.qbk.entity.Comment;
import com.qbk.entity.CommentStatistics;
import com.qbk.entity.param.CommentAddParam;
import com.qbk.entity.query.CommentQuery;
import com.qbk.entity.vo.CommentVo;

import java.util.List;

/**
 * 评价Service接口
 */
public interface CommentService {
    /**
     * 发表商品评价
     * @param commentAddParam 评价参数
     * @param userId 用户ID
     * @return 评价信息
     */
    Comment addComment(CommentAddParam commentAddParam, Long userId);
    
    /**
     * 删除评价
     * @param id 评价ID
     * @return 是否删除成功
     */
    boolean deleteComment(Long id);
    
    /**
     * 根据ID查询评价
     * @param id 评价ID
     * @return 评价信息
     */
    Comment getCommentById(Long id);
    
    /**
     * 查询评价列表
     * @param commentQuery 查询参数
     * @return 评价列表
     */
    List<CommentVo> getCommentList(CommentQuery commentQuery);
    
    /**
     * 查询评价总数
     * @param commentQuery 查询参数
     * @return 评价总数
     */
    int getCommentCount(CommentQuery commentQuery);
    
    /**
     * 获取商品评价统计
     * @param itemId 商品ID
     * @return 评价统计信息
     */
    CommentStatistics getCommentStatistics(Long itemId);
    
    /**
     * 更新商品评价统计
     * @param itemId 商品ID
     * @return 评价统计信息
     */
    CommentStatistics updateCommentStatistics(Long itemId);
    
    /**
     * 验证用户是否可以评价该订单
     * @param orderId 订单ID
     * @param userId 用户ID
     * @return 是否可以评价
     */
    boolean canComment(Long orderId, Long userId);
}