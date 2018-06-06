package com.bird.framework.xsy.operate.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Tree<ID extends Serializable> implements Serializable {
    private static final long serialVersionUID = 5407244077014983660L;
    private ID id;

    private String text;

    private String iconCls;

    private boolean checked;

    private String state = "closed";

    private Map<String, Object> attributes = new HashMap<>();

    public Tree() {

    }

    public Tree(ID id, String text, String iconCls, Map<String,Object> attributes) {
        this.id = id;
        this.text = text;
        this.iconCls = iconCls;
        this.attributes.putAll(attributes);
    }
}
