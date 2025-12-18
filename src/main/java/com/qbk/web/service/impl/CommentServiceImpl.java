package com.qbk.web.service.impl;

import com.qbk.entity.Comment;
import com.qbk.entity.CommentStatistics;
import com.qbk.entity.param.CommentAddParam;
import com.qbk.entity.query.CommentQuery;
import com.qbk.entity.vo.CommentVo;
import com.qbk.web.mapper.CommentMapper;
import com.qbk.web.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评价Service实现类
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    
    @Autowired
    private CommentMapper commentMapper;
    
    /**
     * 发表商品评价
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Comment addComment(CommentAddParam commentAddParam, Long userId) {
        // 验证用户是否可以评价
        if (!canComment(commentAddParam.getOrderId(), userId)) {
            throw new RuntimeException("您无法评价该订单");
        }
        
        // 创建评价对象
        Comment comment = new Comment();
        comment.setItemId(commentAddParam.getItemId());
        comment.setUserId(userId);
        comment.setOrderId(commentAddParam.getOrderId());
        comment.setContent(commentAddParam.getContent());
        comment.setScore(commentAddParam.getScore());
        comment.setStatus(0);
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());
        
        // 添加评价
        commentMapper.addComment(comment);
        
        // 更新商品评价统计
        updateCommentStatistics(commentAddParam.getItemId());
        
        return comment;
    }
    
    /**
     * 删除评价
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteComment(Long id) {
        Comment comment = commentMapper.getCommentById(id);
        if (comment == null) {
            return false;
        }
        
        // 更新评价状态为删除
        comment.setStatus(1);
        comment.setUpdateTime(LocalDateTime.now());
        int result = commentMapper.updateComment(comment);
        
        // 更新商品评价统计
        updateCommentStatistics(comment.getItemId());
        
        return result > 0;
    }
    
    /**
     * 根据ID查询评价
     */
    @Override
    public Comment getCommentById(Long id) {
        return commentMapper.getCommentById(id);
    }
    
    /**
     * 查询评价列表
     */
    @Override
    public List<CommentVo> getCommentList(CommentQuery commentQuery) {
        return commentMapper.getCommentList(commentQuery);
    }
    
    /**
     * 查询评价总数
     */
    @Override
    public int getCommentCount(CommentQuery commentQuery) {
        return commentMapper.getCommentCount(commentQuery);
    }
    
    /**
     * 获取商品评价统计
     */
    @Override
    public CommentStatistics getCommentStatistics(Long itemId) {
        CommentStatistics statistics = commentMapper.getCommentStatisticsByItemId(itemId);
        if (statistics == null) {
            // 如果没有统计信息，计算并添加
            statistics = updateCommentStatistics(itemId);
        }
        return statistics;
    }
    
    /**
     * 更新商品评价统计
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommentStatistics updateCommentStatistics(Long itemId) {
        // 计算评价统计
        CommentStatistics statistics = commentMapper.calculateCommentStatistics(itemId);
        
        // 检查是否已有统计信息
        CommentStatistics existingStatistics = commentMapper.getCommentStatisticsByItemId(itemId);
        
        if (existingStatistics == null) {
            // 添加新的统计信息
            commentMapper.addCommentStatistics(statistics);
        } else {
            // 更新现有统计信息
            statistics.setId(existingStatistics.getId());
            commentMapper.updateCommentStatistics(statistics);
        }
        
        return statistics;
    }
    
    /**
     * 验证用户是否可以评价该订单
     * 注意：这里需要根据实际的订单系统来实现
     * 目前简单返回true，表示可以评价
     */
    @Override
    public boolean canComment(Long orderId, Long userId) {
        // TODO: 实现订单验证逻辑
        // 1. 验证订单是否存在
        // 2. 验证订单是否属于该用户
        // 3. 验证订单是否已完成
        // 4. 验证该订单是否已评价
        return true;
    }
}