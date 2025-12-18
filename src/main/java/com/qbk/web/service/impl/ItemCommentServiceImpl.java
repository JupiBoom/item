package com.qbk.web.service.impl;

import com.qbk.entity.ItemComment;
import com.qbk.entity.ItemCommentStat;
import com.qbk.entity.dto.ItemCommentDTO;
import com.qbk.entity.vo.ItemCommentVO;
import com.qbk.entity.vo.ItemCommentStatVO;
import com.qbk.web.mapper.ItemCommentMapper;
import com.qbk.web.mapper.ItemCommentStatMapper;
import com.qbk.web.service.ItemCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品评价服务实现类
 */
@Slf4j
@Service
public class ItemCommentServiceImpl implements ItemCommentService {

    @Autowired
    private ItemCommentMapper itemCommentMapper;

    @Autowired
    private ItemCommentStatMapper itemCommentStatMapper;

    /**
     * 发布商品评价
     * @param userId 用户ID
     * @param commentDTO 评价信息
     * @return 评价是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean publishComment(Integer userId, ItemCommentDTO commentDTO) {
        // 检查订单是否已完成（这里可以根据实际业务逻辑实现）
        // 暂时假设订单已完成

        // 检查是否已经评价过该订单
        ItemComment existingComment = itemCommentMapper.selectByOrderId(commentDTO.getOrderId());
        if (existingComment != null) {
            log.warn("用户 {} 已经评价过订单 {}", userId, commentDTO.getOrderId());
            return false;
        }

        // 创建新评价
        ItemComment newComment = new ItemComment();
        newComment.setItemId(commentDTO.getItemId());
        newComment.setOrderId(commentDTO.getOrderId());
        newComment.setUserId(userId);
        newComment.setScore(commentDTO.getScore());
        newComment.setContent(commentDTO.getContent());

        // 插入评价
        int insertResult = itemCommentMapper.insert(newComment);
        if (insertResult <= 0) {
            log.error("插入评价失败，用户ID: {}, 商品ID: {}", userId, commentDTO.getItemId());
            return false;
        }

        // 更新评价统计
        updateCommentStat(commentDTO.getItemId());

        return true;
    }

    /**
     * 根据商品ID查询评价列表
     * @param itemId 商品ID
     * @param orderBy 排序方式
     * @return 评价列表
     */
    @Override
    public List<ItemCommentVO> getCommentsByItemId(Integer itemId, String orderBy) {
        return itemCommentMapper.selectByItemId(itemId, orderBy);
    }

    /**
     * 根据用户ID查询评价历史
     * @param userId 用户ID
     * @return 评价历史列表
     */
    @Override
    public List<ItemCommentVO> getCommentHistoryByUserId(Integer userId) {
        return itemCommentMapper.selectByUserId(userId);
    }

    /**
     * 获取商品评价统计信息
     * @param itemId 商品ID
     * @return 评价统计信息
     */
    @Override
    public ItemCommentStatVO getCommentStatByItemId(Integer itemId) {
        ItemCommentStat stat = itemCommentStatMapper.selectByItemId(itemId);
        if (stat == null) {
            return null;
        }

        ItemCommentStatVO statVO = new ItemCommentStatVO();
        statVO.setItemId(stat.getItemId());
        statVO.setTotalComments(stat.getTotalComments());
        statVO.setGoodComments(stat.getGoodComments());
        statVO.setMediumComments(stat.getMediumComments());
        statVO.setBadComments(stat.getBadComments());
        statVO.setAvgScore(stat.getAvgScore());

        // 计算好评率
        if (stat.getTotalComments() > 0) {
            BigDecimal goodRate = new BigDecimal(stat.getGoodComments())
                    .divide(new BigDecimal(stat.getTotalComments()), 4, BigDecimal.ROUND_HALF_UP);
            statVO.setGoodRate(goodRate);
        } else {
            statVO.setGoodRate(BigDecimal.ZERO);
        }

        return statVO;
    }

    /**
     * 更新商品评价统计信息
     * @param itemId 商品ID
     */
    private void updateCommentStat(Integer itemId) {
        // 查询该商品的所有评价
        List<ItemCommentVO> comments = itemCommentMapper.selectByItemId(itemId, null);
        if (comments == null || comments.isEmpty()) {
            return;
        }

        // 计算统计数据
        int totalComments = comments.size();
        int goodComments = 0;
        int mediumComments = 0;
        int badComments = 0;
        int totalScore = 0;

        for (ItemCommentVO comment : comments) {
            int score = comment.getScore();
            totalScore += score;

            if (score >= 4) {
                goodComments++;
            } else if (score == 3) {
                mediumComments++;
            } else {
                badComments++;
            }
        }

        BigDecimal avgScore = new BigDecimal(totalScore)
                .divide(new BigDecimal(totalComments), 2, BigDecimal.ROUND_HALF_UP);

        // 查询是否已有统计信息
        ItemCommentStat existingStat = itemCommentStatMapper.selectByItemId(itemId);

        if (existingStat == null) {
            // 创建新统计信息
            ItemCommentStat newStat = new ItemCommentStat();
            newStat.setItemId(itemId);
            newStat.setTotalComments(totalComments);
            newStat.setGoodComments(goodComments);
            newStat.setMediumComments(mediumComments);
            newStat.setBadComments(badComments);
            newStat.setAvgScore(avgScore);

            itemCommentStatMapper.insert(newStat);
        } else {
            // 更新现有统计信息
            existingStat.setTotalComments(totalComments);
            existingStat.setGoodComments(goodComments);
            existingStat.setMediumComments(mediumComments);
            existingStat.setBadComments(badComments);
            existingStat.setAvgScore(avgScore);

            itemCommentStatMapper.update(existingStat);
        }
    }
}