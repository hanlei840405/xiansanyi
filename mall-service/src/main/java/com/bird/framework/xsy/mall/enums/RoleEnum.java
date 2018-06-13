package com.bird.framework.xsy.mall.enums;

public enum RoleEnum {
    BUYER("1"), SELLER("2"), ALL("3");

    private final String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String getName(String value) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (value.equals(roleEnum.toString())) {
                return roleEnum.name();
            }
        }
        return null;
    }
}
