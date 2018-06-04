package com.bird.framework.xsy.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jesse.Han
 */
@Data
public class RoleVo implements Serializable {

    private static final long serialVersionUID = 3781248319994249069L;
    private Long id;

    private String code;

    private String name;

    private String status;

    private Long version;
}
