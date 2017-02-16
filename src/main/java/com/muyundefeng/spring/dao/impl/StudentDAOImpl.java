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

    StudentMapper mapper;

    @Override
    public List<Student> selectAllStudents() {
        return mapper.selectAll();
    }

    @Override
    public void insetStudent(Student student) {
        mapper.insert(student);
    }

    @Override
    public Student selectStudentByPrimaryKey(String id) {
        return mapper.selectByPrimaryKey(id);
    }
}
