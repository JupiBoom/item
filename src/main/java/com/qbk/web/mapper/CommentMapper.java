package com.qbk.web.mapper;

import com.qbk.entity.Comment;
import com.qbk.entity.CommentStatistics;
import com.qbk.entity.query.CommentQuery;
import com.qbk.entity.vo.CommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评价Mapper接口
 */
@Mapper
public interface CommentMapper {
    /**
     * 添加评价
     * @param comment 评价信息
     * @return 影响行数
     */
    int addComment(Comment comment);
    
    /**
     * 更新评价
     * @param comment 评价信息
     * @return 影响行数
     */
    int updateComment(Comment comment);
    
    /**
     * 根据ID删除评价
     * @param id 评价ID
     * @return 影响行数
     */
    int deleteCommentById(Long id);
    
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
     * 根据商品ID获取评价统计
     * @param itemId 商品ID
     * @return 评价统计信息
     */
    CommentStatistics getCommentStatisticsByItemId(Long itemId);
    
    /**
     * 更新商品评价统计
     * @param commentStatistics 评价统计信息
     * @return 影响行数
     */
    int updateCommentStatistics(CommentStatistics commentStatistics);
    
    /**
     * 添加商品评价统计
     * @param commentStatistics 评价统计信息
     * @return 影响行数
     */
    int addCommentStatistics(CommentStatistics commentStatistics);
    
    /**
     * 计算商品评价统计
     * @param itemId 商品ID
     * @return 评价统计信息
     */
    CommentStatistics calculateCommentStatistics(Long itemId);
}