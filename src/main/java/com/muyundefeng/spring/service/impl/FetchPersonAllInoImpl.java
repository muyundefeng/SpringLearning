package com.muyundefeng.spring.service.impl;

import com.muyundefeng.spring.dao.PersonDAO;
import com.muyundefeng.spring.dao.StudentDAO;
import com.muyundefeng.spring.entity.Personinfo;
import com.muyundefeng.spring.entity.Student;
import com.muyundefeng.spring.service.FetchPersonAllInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by lisheng on 17-2-14.
 */
@Service
public class FetchPersonAllInoImpl implements FetchPersonAllInfo {

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    PersonDAO personDAO;

    @Override
    public String info(String id) {
        Student student = studentDAO.selectStudentByPrimaryKey(id);
        String address = student.getAddress();
        Personinfo personinfo = personDAO.selectByPrimayId(id);
        String homeAddress =  personinfo.getHomeAddress();
        return "schoolAddress:" + address + ",home_address:" + homeAddress;
    }

    @Override
    public void insertEntity(Student stu, Personinfo person) {
        studentDAO.insetStudent(stu);
        personDAO.insetPerson(person);
    }
}
