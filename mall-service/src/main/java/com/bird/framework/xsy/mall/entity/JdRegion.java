package com.bird.framework.xsy.mall.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 
 */
@Data
public class JdRegion implements Serializable {
    private static final long serialVersionUID = -28784739964299244L;
    private Long id;

    private String districtCode;

    private String jdCityCode;

    private String jdRegionCode;
}