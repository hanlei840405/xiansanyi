package com.bird.framework.xsy.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 5992432388215325478L;
    private Long id;

    private String username;

    private String password;

    private String nick;

    private String firstName;

    private String lastName;

    private String mobile;

    private String status;

    private Integer version;
}