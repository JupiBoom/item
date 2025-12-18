package com.qbk.web.mapper;

import com.qbk.entity.ItemCommentStat;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价统计Mapper接口
 */
@Mapper
public interface ItemCommentStatMapper {
    /**
     * 插入评价统计
     * @param stat 统计信息
     * @return 插入结果
     */
    int insert(ItemCommentStat stat);

    /**
     * 根据商品ID查询统计信息
     * @param itemId 商品ID
     * @return 统计信息
     */
    ItemCommentStat selectByItemId(Integer itemId);

    /**
     * 更新评价统计
     * @param stat 统计信息
     * @return 更新结果
     */
    int update(ItemCommentStat stat);
}
