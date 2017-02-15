package com.muyundefeng.spring.controller;

import com.muyundefeng.spring.entity.Student;
import com.muyundefeng.spring.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lisheng on 17-2-14.
 */
@Controller
@RequestMapping(value = "/")
public class TestController {
    @Autowired
    StudentService service;

    @PostMapping(value = "/student")
    @ResponseBody
    public String getJson(HttpServletRequest servlet) {
        String name = servlet.getParameter("name");
        List<Student> students = service.getStudent(name);
        return students.toString();
    }
    @PostMapping(value = "/insertStudent")
    @ResponseBody
    public String insertStudent(HttpServletRequest servlet) {
        String name = servlet.getParameter("name");
        String age = servlet.getParameter("age");
        String score = servlet.getParameter("score");

        service.addStudent(name,age,score);
        return "sucess";
    }
}
