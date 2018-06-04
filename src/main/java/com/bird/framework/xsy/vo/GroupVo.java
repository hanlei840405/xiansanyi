package com.bird.framework.xsy.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jesse.Han
 */
@Data
public class GroupVo implements Serializable {
    private static final long serialVersionUID = 4902687911233892744L;
    private Long id;

    private String code;

    private String name;

    private String status;

    private Long version;
}
