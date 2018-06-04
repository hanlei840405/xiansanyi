package com.bird.framework.xsy.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jesse.Han
 */
@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = -1599812044787127727L;
    private Long id;

    private String code;

    private String username;

    private String nick;

    private String firstName;

    private String lastName;

    private String mobile;

    private String status;

    private Long version;
}
