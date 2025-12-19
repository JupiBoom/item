package com.qbk.entity.param;

import javax.validation.constraints.NotNull;

/**
 * 购物车参数类
 */
public class CartParam {
    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    /**
     * 商品数量
     */
    @NotNull(message = "商品数量不能为空")
    private Integer quantity;

    /**
     * 临时购物车标识
     */
    private String tempCartKey;

    // getter 和 setter 方法
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTempCartKey() {
        return tempCartKey;
    }

    public void setTempCartKey(String tempCartKey) {
        this.tempCartKey = tempCartKey;
    }
}