package com.bird.framework.xsy.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 
 */
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = -2405795147093061895L;
    private Long id;

    private String code;

    private String name;

    private String status;

    private Integer version;
}