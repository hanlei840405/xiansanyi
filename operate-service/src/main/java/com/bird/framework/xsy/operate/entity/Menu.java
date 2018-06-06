package com.bird.framework.xsy.operate.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 
 */
@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = -4729268098830403699L;
    private Long id;

    private String code;

    private String name;

    private String category;

    private Long parentId;

    private String url;

    private String status;

    private Integer version;
}