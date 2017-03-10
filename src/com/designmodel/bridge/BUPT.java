package com.designmodel.bridge;

/**
 * Created by lisheng on 17-3-10.
 */
public class BUPT extends University {

    @Override
    public String getNameAndSchool() {

        return "bupt"+student.getType() ;
    }
}
