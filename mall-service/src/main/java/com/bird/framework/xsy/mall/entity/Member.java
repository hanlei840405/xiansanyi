package com.bird.framework.xsy.mall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * mall_user
 *
 * @author
 */
@Data
public class Member implements Serializable {
    private static final long serialVersionUID = 980396937362462211L;
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private String nick;

    private String firstName;

    private String lastName;

    private String mobile;

    private String role;

    private String gender;

    private String status;

    private Integer version;

    private Date created;
}