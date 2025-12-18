package com.qbk.web.mapper;

import com.qbk.entity.ItemComment;
import com.qbk.entity.vo.ItemCommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品评价Mapper接口
 */
@Mapper
public interface ItemCommentMapper {
    /**
     * 插入评价
     * @param comment 评价信息
     * @return 插入结果
     */
    int insert(ItemComment comment);

    /**
     * 根据商品ID查询评价列表
     * @param itemId 商品ID
     * @param orderBy 排序方式
     * @return 评价列表
     */
    List<ItemCommentVO> selectByItemId(@Param("itemId") Integer itemId, @Param("orderBy") String orderBy);

    /**
     * 根据用户ID查询评价历史
     * @param userId 用户ID
     * @return 评价列表
     */
    List<ItemCommentVO> selectByUserId(Integer userId);

    /**
     * 根据订单ID查询评价
     * @param orderId 订单ID
     * @return 评价信息
     */
    ItemComment selectByOrderId(Integer orderId);

    /**
     * 更新评价
     * @param comment 评价信息
     * @return 更新结果
     */
    int update(ItemComment comment);
}
