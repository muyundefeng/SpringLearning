package com.muyundefeng.spring.service.impl;

import com.muyundefeng.spring.entity.Student;
import com.muyundefeng.spring.mapper.StudentMapper;
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
    StudentMapper mapper;

    public List<Student> getStudent(String name) {
        return mapper.selectAll();
    }

    public void addStudent(String name, String age, String score) {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setScore(score);
        mapper.insert(student);
    }
}
