package com.qbk.web;

import com.qbk.entity.dto.OrderInfoDTO;
import com.qbk.entity.enums.OrderStatusEnum;
import com.qbk.event.OrderStatusChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知测试控制器
 */
@RestController
@RequestMapping("/api/notification")
public class NotificationTestController {
    
    @Autowired
    private ApplicationContext applicationContext;
    
    /**
     * 测试订单状态变更通知
     * @param orderInfo 订单信息
     * @return 测试结果
     */
    @PostMapping("/test-order-status-change")
    public String testOrderStatusChange(@RequestBody OrderInfoDTO orderInfo) {
        try {
            // 补全必要信息
            if (orderInfo.getOrderTime() == null) {
                orderInfo.setOrderTime(LocalDateTime.now());
            }
            
            if (orderInfo.getProductNames() == null) {
                orderInfo.setProductNames(List.of("测试商品1", "测试商品2"));
            }
            
            if (orderInfo.getTotalAmount() == null) {
                orderInfo.setTotalAmount(new BigDecimal(100.00));
            }
            
            // 发布订单状态变更事件
            applicationContext.publishEvent(
                new OrderStatusChangeEvent(this, orderInfo)
            );
            
            return "订单状态变更通知测试已触发，订单ID: " + orderInfo.getOrderId();
            
        } catch (Exception e) {
            return "测试失败: " + e.getMessage();
        }
    }
    
    /**
     * 测试支付成功通知
     * @param orderId 订单ID
     * @param userId 用户ID
     * @return 测试结果
     */
    @PostMapping("/test-payment-success")
    public String testPaymentSuccess(String orderId, Integer userId) {
        OrderInfoDTO orderInfo = OrderInfoDTO.builder()
            .orderId(orderId)
            .userId(userId)
            .orderStatus(OrderStatusEnum.PAID.getCode())
            .productNames(List.of("手机", "耳机"))
            .totalAmount(new BigDecimal(2999.00))
            .orderTime(LocalDateTime.now())
            .build();
            
        applicationContext.publishEvent(
            new OrderStatusChangeEvent(this, orderInfo)
        );
        
        return "支付成功通知测试已触发，订单ID: " + orderId;
    }
    
    /**
     * 测试发货通知
     * @param orderId 订单ID
     * @param userId 用户ID
     * @return 测试结果
     */
    @PostMapping("/test-shipped")
    public String testShipped(String orderId, Integer userId) {
        OrderInfoDTO orderInfo = OrderInfoDTO.builder()
            .orderId(orderId)
            .userId(userId)
            .orderStatus(OrderStatusEnum.SHIPPED.getCode())
            .productNames(List.of("手机", "耳机"))
            .totalAmount(new BigDecimal(2999.00))
            .orderTime(LocalDateTime.now())
            .shipTime(LocalDateTime.now())
            .build();
            
        applicationContext.publishEvent(
            new OrderStatusChangeEvent(this, orderInfo)
        );
        
        return "发货通知测试已触发，订单ID: " + orderId;
    }
    
    /**
     * 测试签收通知
     * @param orderId 订单ID
     * @param userId 用户ID
     * @return 测试结果
     */
    @PostMapping("/test-delivered")
    public String testDelivered(String orderId, Integer userId) {
        OrderInfoDTO orderInfo = OrderInfoDTO.builder()
            .orderId(orderId)
            .userId(userId)
            .orderStatus(OrderStatusEnum.DELIVERED.getCode())
            .productNames(List.of("手机", "耳机"))
            .totalAmount(new BigDecimal(2999.00))
            .orderTime(LocalDateTime.now())
            .shipTime(LocalDateTime.now().minusDays(1))
            .deliverTime(LocalDateTime.now())
            .build();
            
        applicationContext.publishEvent(
            new OrderStatusChangeEvent(this, orderInfo)
        );
        
        return "签收通知测试已触发，订单ID: " + orderId;
    }
}