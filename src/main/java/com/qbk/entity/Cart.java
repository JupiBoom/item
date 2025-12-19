package com.qbk.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 购物车实体类
 */
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 购物车ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 临时购物车标识（未登录用户）
     */
    private String tempCartKey;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    // getter 和 setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTempCartKey() {
        return tempCartKey;
    }

    public void setTempCartKey(String tempCartKey) {
        this.tempCartKey = tempCartKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}