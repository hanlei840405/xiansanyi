package entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jesse.Han
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1626898892132741422L;
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
