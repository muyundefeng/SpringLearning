package com.designmodel.bridge;

/**桥接模式只在抽象层建立相关的对应的关系
 * Created by lisheng on 17-3-10.
 */
public abstract  class University {
    Student student = null;

    public void setStudent(Student stu) {
        this.student = stu;
    }

    public abstract String getNameAndSchool();
}
