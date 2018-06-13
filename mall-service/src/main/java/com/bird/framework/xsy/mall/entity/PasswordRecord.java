package com.bird.framework.xsy.mall.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * mall_user_password_record
 *
 * @author
 */
@Data
public class PasswordRecord implements Serializable {
    private static final long serialVersionUID = -1481078222412677464L;
    private Long id;

    private Long memberId;

    private String lastPassword;

    private Date created;
}