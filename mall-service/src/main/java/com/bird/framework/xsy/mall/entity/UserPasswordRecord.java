package com.bird.framework.xsy.mall.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * mall_user_password_record
 * @author 
 */
public class UserPasswordRecord implements Serializable {
    private Long id;

    private Long userId;

    private String lastPassword;

    private Date created;

    private static final long serialVersionUID = 1L;

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

    public String getLastPassword() {
        return lastPassword;
    }

    public void setLastPassword(String lastPassword) {
        this.lastPassword = lastPassword;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}