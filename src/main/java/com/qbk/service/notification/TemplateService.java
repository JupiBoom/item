package com.qbk.service.notification;

import com.qbk.entity.dto.OrderInfoDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板服务类，用于处理模板变量替换
 */
@Service
public class TemplateService {
    
    /**
     * 替换模板中的变量
     * @param templateContent 模板内容
     * @param orderInfo 订单信息
     * @return 替换后的内容
     */
    public String replaceVariables(String templateContent, OrderInfoDTO orderInfo) {
        if (templateContent == null || orderInfo == null) {
            return templateContent;
        }
        
        Map<String, String> variables = buildVariablesMap(orderInfo);
        
        String result = templateContent;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        
        return result;
    }
    
    /**
     * 构建变量映射
     * @param orderInfo 订单信息
     * @return 变量映射
     */
    private Map<String, String> buildVariablesMap(OrderInfoDTO orderInfo) {
        Map<String, String> variables = new HashMap<>();
        
        variables.put("orderId", orderInfo.getOrderId());
        variables.put("productNames", String.join(", ", orderInfo.getProductNames()));
        variables.put("totalAmount", orderInfo.getTotalAmount().toString());
        variables.put("orderTime", 
            orderInfo.getOrderTime() != null ? orderInfo.getOrderTime().toString() : "");
        variables.put("shipTime", 
            orderInfo.getShipTime() != null ? orderInfo.getShipTime().toString() : "");
        variables.put("deliverTime", 
            orderInfo.getDeliverTime() != null ? orderInfo.getDeliverTime().toString() : "");
        
        return variables;
    }
}