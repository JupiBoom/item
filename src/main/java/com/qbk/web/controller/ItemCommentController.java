package com.qbk.web.controller;

import com.qbk.entity.dto.ItemCommentDTO;
import com.qbk.entity.vo.ItemCommentVO;
import com.qbk.entity.vo.ItemCommentStatVO;
import com.qbk.result.BaseResult;
import com.qbk.result.BaseResultGenerator;
import com.qbk.web.service.ItemCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品评价控制器
 */
@RestController
@RequestMapping("/api/comment")
public class ItemCommentController {

    @Autowired
    private ItemCommentService itemCommentService;

    /**
     * 发布商品评价
     * @param userId 用户ID（可以从token或session中获取）
     * @param commentDTO 评价信息
     * @return 操作结果
     */
    @PostMapping("/publish")
    public BaseResult publishComment(
            @RequestParam Integer userId,
            @Validated @RequestBody ItemCommentDTO commentDTO) {
        boolean success = itemCommentService.publishComment(userId, commentDTO);
        if (success) {
            return BaseResultGenerator.success("评价发布成功");
        } else {
            return BaseResultGenerator.fail("评价发布失败");
        }
    }

    /**
     * 根据商品ID查询评价列表
     * @param itemId 商品ID
     * @param orderBy 排序方式（"time" 按时间排序，"score" 按评分排序）
     * @return 评价列表
     */
    @GetMapping("/item/{itemId}")
    public BaseResult getCommentsByItemId(
            @PathVariable Integer itemId,
            @RequestParam(defaultValue = "time") String orderBy) {
        // 转换排序参数
        String sortOrder = "create_time desc";
        if ("score".equals(orderBy)) {
            sortOrder = "score desc, create_time desc";
        }

        List<ItemCommentVO> comments = itemCommentService.getCommentsByItemId(itemId, sortOrder);
        return BaseResultGenerator.success(comments);
    }

    /**
     * 获取商品评价统计信息
     * @param itemId 商品ID
     * @return 评价统计信息
     */
    @GetMapping("/stat/{itemId}")
    public BaseResult getCommentStatByItemId(@PathVariable Integer itemId) {
        ItemCommentStatVO stat = itemCommentService.getCommentStatByItemId(itemId);
        return BaseResultGenerator.success(stat);
    }

    /**
     * 根据用户ID查询评价历史
     * @param userId 用户ID
     * @return 评价历史列表
     */
    @GetMapping("/history/{userId}")
    public BaseResult getCommentHistoryByUserId(@PathVariable Integer userId) {
        List<ItemCommentVO> history = itemCommentService.getCommentHistoryByUserId(userId);
        return BaseResultGenerator.success(history);
    }
}