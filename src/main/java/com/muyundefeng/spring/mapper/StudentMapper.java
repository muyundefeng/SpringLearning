package com.muyundefeng.spring.mapper;

import com.muyundefeng.spring.entity.Student;
import java.util.List;

public interface StudentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated Thu Feb 16 15:32:19 CST 2017
     */
    int insert(Student record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated Thu Feb 16 15:32:19 CST 2017
     */
    List<Student> selectAll();
}