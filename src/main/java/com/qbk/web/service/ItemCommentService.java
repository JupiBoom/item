package com.qbk.web.service;

import com.qbk.entity.dto.ItemCommentDTO;
import com.qbk.entity.vo.ItemCommentVO;
import com.qbk.entity.vo.ItemCommentStatVO;

import java.util.List;

/**
 * 商品评价服务接口
 */
public interface ItemCommentService {
    /**
     * 发布商品评价
     * @param userId 用户ID
     * @param commentDTO 评价信息
     * @return 评价是否成功
     */
    boolean publishComment(Integer userId, ItemCommentDTO commentDTO);

    /**
     * 根据商品ID查询评价列表
     * @param itemId 商品ID
     * @param orderBy 排序方式（"create_time desc" 或 "score desc"）
     * @return 评价列表
     */
    List<ItemCommentVO> getCommentsByItemId(Integer itemId, String orderBy);

    /**
     * 根据用户ID查询评价历史
     * @param userId 用户ID
     * @return 评价历史列表
     */
    List<ItemCommentVO> getCommentHistoryByUserId(Integer userId);

    /**
     * 获取商品评价统计信息
     * @param itemId 商品ID
     * @return 评价统计信息
     */
    ItemCommentStatVO getCommentStatByItemId(Integer itemId);
}
