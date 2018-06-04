package com.bird.framework.xsy.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 
 */
@Data
public class Group implements Serializable {
    private static final long serialVersionUID = 6888950878100449167L;
    private Long id;

    private String code;

    private String name;

    private String status;

    private Integer version;

}