package com.designmodel.test;

import com.designmodel.visitor.*;

/**
 * Created by lisheng on 17-3-27.
 */
public class VisitorTest {
    public static void main(String[] args) {
        EmployeeList list = new EmployeeList();
        Employee fte1,fte2,fte3,pte1,pte2;

        fte1 = new FulltimeEmployee("张无忌",3200.00,45);
        fte2 = new FulltimeEmployee("杨过",2000.00,40);
        fte3 = new FulltimeEmployee("段誉",2400.00,38);
        pte1 = new ParttimeEmployee("洪七公",80.00,20);
        pte2 = new ParttimeEmployee("郭靖",60.00,18);

        list.addEmployee(fte1);
        list.addEmployee(fte2);
        list.addEmployee(fte3);
        list.addEmployee(pte1);
        list.addEmployee(pte2);

        Department department = new FADepartment();
        list.accept(department);

        Department department1 = new HRDepartment();
        list.accept(department1);
    }
}
