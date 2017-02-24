package com.muyundefeng.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muyundefeng.spring.entity.Personinfo;
import com.muyundefeng.spring.entity.Student;
import com.muyundefeng.spring.service.FetchPersonAllInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by lisheng on 17-2-14.
 *
 * @email 2533604335@qq.com
 */
@Controller
@RequestMapping(value = "/")
public class TestController {

    @Autowired
    FetchPersonAllInfo service;

    @RequestMapping(value = "/student")
    @ResponseBody
    public String getJson(HttpServletRequest servlet) {
        String id = servlet.getParameter("id");
        String info = service.info(id);
        return info;
    }

    /**
     * @param body {
     *             "id":"1112332",
     *             "school_address":"Bupt Haidian District ,Beijing",
     *             "age":"30",
     *             "name":"tom",
     *             "score":"98",
     *             "home_address":"New York",
     *             "family_number":"6",
     *             "language":"English,Chinese"
     *             }
     * @return
     */
    @PostMapping(value = "/inserEntity", produces = "application/json")
    @ResponseBody
    public String addEntity(@RequestBody String body) {
        System.out.println(body);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> toMap = null;
        try {
            toMap = objectMapper.readValue(body, Map.class);
        } catch (IOException e) {
            return "parse json failed";
        }
        String id = toMap.get("id");
        String schoolAddress = toMap.get("school_address");
        String age = toMap.get("age");
        String name = toMap.get("name");
        String score = toMap.get("score");
        String homeAddress = toMap.get("home_address");
        String familyNumber = toMap.get("family_number");
        String language = toMap.get("language");
        Student student = new Student();
        student.setName(name);
        student.setScore(score);
        student.setId(id);
        student.setAge(age);
        student.setAddress(schoolAddress);
        Personinfo person = new Personinfo();
        person.setId(id);
        person.setHomeAddress(homeAddress);
        person.setFamilyNumber(familyNumber);
        person.setLanguage(language);
        service.insertEntity(student, person);
        return "success";
    }

    @RequestMapping(value = "/getAllStudents")
    @ResponseBody
    public String getAll() {
        List<Student> studentList = service.getAllStudents();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(studentList);
        } catch (IOException e) {
            return "parse json failed";
        }
        return json == null?"{\"result\":\"failed\"}":json;
    }
}
