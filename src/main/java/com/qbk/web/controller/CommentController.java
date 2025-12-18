package com.qbk.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qbk.entity.Comment;
import com.qbk.entity.CommentStatistics;
import com.qbk.entity.param.CommentAddParam;
import com.qbk.entity.query.CommentQuery;
import com.qbk.entity.vo.CommentVo;
import com.qbk.result.BaseResult;
import com.qbk.result.BaseResultGenerator;
import com.qbk.web.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评价管理Controller
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评价管理")
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    /**
     * 发表商品评价
     */
    @PostMapping("/add")
    @ApiOperation("发表商品评价")
    public BaseResult<Comment> addComment(@Validated @RequestBody CommentAddParam commentAddParam) {
        // TODO: 从登录信息中获取用户ID
        Long userId = 1L; // 临时测试数据
        
        Comment comment = commentService.addComment(commentAddParam, userId);
        return BaseResultGenerator.success(comment);
    }
    
    /**
     * 删除评价
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除评价")
    public BaseResult<Boolean> deleteComment(@PathVariable Long id) {
        boolean result = commentService.deleteComment(id);
        return BaseResultGenerator.success(result);
    }
    
    /**
     * 根据ID查询评价
     */
    @GetMapping("/get/{id}")
    @ApiOperation("根据ID查询评价")
    public BaseResult<Comment> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        return BaseResultGenerator.success(comment);
    }
    
    /**
     * 查询评价列表
     */
    @GetMapping("/list")
    @ApiOperation("查询评价列表")
    public BaseResult<PageInfo<CommentVo>> getCommentList(
            @RequestParam(required = false) Long itemId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        
        CommentQuery commentQuery = new CommentQuery();
        commentQuery.setItemId(itemId);
        commentQuery.setUserId(userId);
        commentQuery.setSortBy(sortBy);
        
        PageHelper.startPage(pageNum, pageSize);
        List<CommentVo> commentList = commentService.getCommentList(commentQuery);
        PageInfo<CommentVo> pageInfo = new PageInfo<>(commentList);
        
        return BaseResultGenerator.success(pageInfo);
    }
    
    /**
     * 获取商品评价统计
     */
    @GetMapping("/statistics/{itemId}")
    @ApiOperation("获取商品评价统计")
    public BaseResult<CommentStatistics> getCommentStatistics(@PathVariable Long itemId) {
        CommentStatistics statistics = commentService.getCommentStatistics(itemId);
        return BaseResultGenerator.success(statistics);
    }
    
    /**
     * 更新商品评价统计
     */
    @PostMapping("/statistics/update/{itemId}")
    @ApiOperation("更新商品评价统计")
    public BaseResult<CommentStatistics> updateCommentStatistics(@PathVariable Long itemId) {
        CommentStatistics statistics = commentService.updateCommentStatistics(itemId);
        return BaseResultGenerator.success(statistics);
    }
}