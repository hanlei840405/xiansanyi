package com.bird.framework.xsy.mall.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * mall_city_time
 *
 * @author
 */
@Data
public class CityTime implements Serializable {
    private static final long serialVersionUID = -128234858468970672L;
    private Long id;

    private String cityCode;

    private String timeCode;

    private String category;
}