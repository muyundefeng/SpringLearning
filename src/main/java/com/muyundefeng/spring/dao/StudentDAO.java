package com.muyundefeng.spring.dao;

import com.muyundefeng.spring.entity.Student;

import java.util.List;

/**
 * Created by lisheng on 17-2-16.
 */
public interface StudentDAO {

  List<Student> getStudents();
}
