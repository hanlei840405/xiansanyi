package com.bird.framework.xsy.mall.enums;

public enum MovieEnum {
    CREATED("1"), PAID("2"), SENT2SELLER("3"), ASSIGNED("4"), AUDIT("5"), SENT2AUDIT("6"), SENT2BUYER("7"), FINISHED("9"), CANCELLED("0");

    private final String value;

    MovieEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
