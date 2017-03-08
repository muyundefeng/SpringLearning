package com.designmodel.entity;

import java.io.Serializable;

/**
 * Created by lisheng on 17-3-8.
 */
public class Person implements Serializable{

    private static final long serialVersionUID = 1L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
