package com.muyundefeng.spring.service;


import com.muyundefeng.spring.entity.Personinfo;
import com.muyundefeng.spring.entity.Student;

/**
 * Created by lisheng on 17-2-14.
 */
public interface FetchPersonAllInfo {
    public String info(String id);

    public void insertEntity(Student stu, Personinfo person);
}
