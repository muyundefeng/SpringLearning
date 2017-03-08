package com.designmodel.prototype;

import java.util.List;

/**浅复制
 * Created by lisheng on 17-3-8.
 */
public class ShallowClone implements  Cloneable {

    public List<String> list;

    public String name;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShallowClone clone() throws CloneNotSupportedException {
        return (ShallowClone)super.clone();
    }
}
