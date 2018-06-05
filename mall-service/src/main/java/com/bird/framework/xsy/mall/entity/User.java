package com.bird.framework.xsy.mall.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * mall_user
 *
 * @author
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 980396937362462211L;
    private Long id;

    private String username;

    private String password;

    private String nick;

    private String firstName;

    private String lastName;

    private String mobile;

    private String status;

    private Integer version;

    private Date created;

    private List<Role> roles = new ArrayList<>();
}