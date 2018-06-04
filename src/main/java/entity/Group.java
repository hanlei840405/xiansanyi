package entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jesse.Han
 */
@Data
public class Group implements Serializable {
    private static final long serialVersionUID = -7558445848038809366L;
    private Long id;

    private String code;

    private String name;

    private String status;

    private Long version;
}
