package entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jesse.Han
 */
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = -9007992416413571723L;
    private Long id;

    private String code;

    private String name;

    private String status;

    private Long version;
}
