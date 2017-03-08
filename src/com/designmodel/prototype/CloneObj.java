package com.designmodel.prototype;

/**通用的clone方法
 * Created by lisheng on 17-3-8.
 */
public class CloneObj {

    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CloneObj clone(){
        CloneObj cloneObj = new CloneObj();
        cloneObj.setName(this.getName());
        return cloneObj;
    }
}
