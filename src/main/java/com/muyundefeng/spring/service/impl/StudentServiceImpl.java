package com.muyundefeng.spring.service.impl;

import com.muyundefeng.spring.dao.StudentDAO;
import com.muyundefeng.spring.entity.Student;
import com.muyundefeng.spring.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lisheng on 17-2-14.
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentDAO dao;

    public List<Student> getStudent(String name) {
        return dao.getStudents();
    }

}
