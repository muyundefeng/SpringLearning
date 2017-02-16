package com.muyundefeng.spring.dao.impl;

import com.muyundefeng.spring.dao.StudentDAO;
import com.muyundefeng.spring.entity.Student;
import com.muyundefeng.spring.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lisheng on 17-2-16.
 */
@Repository
public class StudentDAOImpl implements StudentDAO {

    @Autowired
    StudentMapper mapper;

    @Override
    public List<Student> getStudents() {
        return mapper.selectAll();
    }
}
