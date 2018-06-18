package com.bird.framework.xsy.mall.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 
 */
@Data
public class JdCinema implements Serializable {
    private static final long serialVersionUID = -7569064931360331395L;
    private Long id;

    private String cityCode;

    private String regionCode;

    private String code;

    private String name;

    private String address;
}