package com.qbk.mapper;

import com.qbk.entity.NotificationTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 通知模板Mapper
 */
@Mapper
public interface NotificationTemplateMapper {
    /**
     * 根据订单状态获取通知模板列表
     * @param orderStatus 订单状态
     * @return 通知模板列表
     */
    List<NotificationTemplate> getByOrderStatus(@Param("orderStatus") Integer orderStatus);
}
