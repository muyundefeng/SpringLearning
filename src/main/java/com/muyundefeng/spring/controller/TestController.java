package com.muyundefeng.spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(students);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json == null ? "false response" : json;
    }
}
