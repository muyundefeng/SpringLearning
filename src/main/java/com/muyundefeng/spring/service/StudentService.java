package com.muyundefeng.spring.service;

import com.muyundefeng.spring.entity.Student;

import java.util.List;

/**
 * Created by lisheng on 17-2-14.
 */
public interface StudentService {

    public List<Student> getStudent(String name);

    public void addStudent(String name, String age, String score);

}
