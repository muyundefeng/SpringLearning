package com.designmodel.prototype;

/**
 * Created by lisheng on 17-3-8.
 */
public class PrototypeClone implements Cloneable {

    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PrototypeClone clone() throws CloneNotSupportedException {
        return (PrototypeClone)super.clone();
    }
}
