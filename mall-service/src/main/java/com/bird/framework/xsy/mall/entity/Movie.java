package com.bird.framework.xsy.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * mall_movie
 *
 * @author
 */
@Data
public class Movie implements Serializable {
    private static final long serialVersionUID = -8078860943119021800L;
    private Long id;

    private String code;

    private String location;

    private String cinema;

    private String movie;

    private Date time;

    private String mobile;

    private Integer price;

    private Integer discount;

    private Integer fee;

    private String buyer;

    private String seller;

    private Date paid;

    private String payload;

    private Date created;

    private Date sent2seller;

    private Date sent2buyer;

    private String status;
}