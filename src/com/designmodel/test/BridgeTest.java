package com.designmodel.test;

import com.designmodel.bridge.BUPT;
import com.designmodel.bridge.Student;
import com.designmodel.bridge.University;
import com.designmodel.bridge.impl.Undergraduate;

/**
 * Created by lisheng on 17-3-10.
 */
public class BridgeTest {
    public static void main(String[] args) {
        Student student = new Undergraduate();
        University university = new BUPT();
        university.setStudent(student);
        System.out.println(university.getNameAndSchool());
    }
}
