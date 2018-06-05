package com.bird.framework.xsy.mall.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * mall_role
 * @author 
 */
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 8227327429900233185L;
    private Long id;

    private String code;

    private String name;

    private String status;

    private Integer version;

    private Date created;
}