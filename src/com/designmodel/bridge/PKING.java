package com.designmodel.bridge;

/**在抽象层做关联，在子类中实现业务操作
 * Created by lisheng on 17-3-10.
 */
public class PKING extends  University {
    @Override
    public String getNameAndSchool() {
        return "Peking"+student.getType();
    }
}
