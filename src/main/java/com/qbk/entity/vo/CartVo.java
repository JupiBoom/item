package com.qbk.entity.vo;

import java.util.List;

/**
 * 购物车 VO 类
 */
public class CartVo {
    /**
     * 购物车商品项列表
     */
    private List<CartItemVo> cartItems;

    /**
     * 购物车总金额
     */
    private Double totalAmount;

    /**
     * 商品总数量
     */
    private Integer totalQuantity;

    // getter 和 setter 方法
    public List<CartItemVo> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemVo> cartItems) {
        this.cartItems = cartItems;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}