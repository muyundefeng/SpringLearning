package com.muyundefeng.spring.controller;

/**
 * Created by lisheng on 17-2-24.
 */

import com.muyundefeng.spring.entity.Clazz;
import com.muyundefeng.spring.entity.NewStudent;
import com.muyundefeng.spring.entity.Student;
import com.muyundefeng.spring.mapper.ClassMapper;
import com.muyundefeng.spring.mapper.StudentMapper;
import com.muyundefeng.spring.mapper.StudentNewMapper;
import com.muyundefeng.spring.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    BlogService blogService;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    ClassMapper classMapper;

    @Autowired
    StudentNewMapper studentNewMapper;

    @RequestMapping(value = "/insertBlog", produces = "application/json")
    @ResponseBody
    public String addBlog(@RequestBody String body, HttpServletResponse response) {
        response.setContentType("application/json;charset=utf-8");
        return blogService.addBlog(body);
    }

    @RequestMapping(value = "/deleteBlog")
    @ResponseBody
    public String deleteBlog(@RequestParam("blogId") String blogId) {
        return blogService.deleteBlog(blogId);
    }

    @RequestMapping("/getBlogs")
    @ResponseBody
    public String getBlogs(@RequestParam("cateId") String cateId, HttpServletResponse response) {
        response.setContentType("application/json; charset=utf-8");
        int id = Integer.parseInt(cateId);
        return blogService.getBlogList(id);
    }

    @RequestMapping("/getComment")
    @ResponseBody
    public String getCommentsByBlogId(@RequestParam("pageIndex") String pageIndex, @RequestParam("pageCount") String pageCount, @RequestParam("blogId") String blogId) {
        return blogService.getComments(blogId, pageIndex, pageCount);
    }

    @RequestMapping("/getSingleBlog")
    @ResponseBody
    public String getSingleBlog(@RequestParam("blogId") String blogId, HttpServletResponse response) {
        response.setContentType("application/json; charset=utf-8");
        return blogService.getSingleBlog(blogId);
    }

    @RequestMapping("/addComment")
    @ResponseBody
    public String addCommentByBlogId(@RequestBody String Body) {
        return blogService.addComment(Body);
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
//        Student student = studentMapper.selectByPrimaryKey(1234);
//        System.out.println(student.getOfClass().getClassname());
//        System.out.println(student);
//        System.out.println(classMapper.selectByPrimaryKey(12).getStudentList().size());
//        return classMapper.selectByPrimaryKey(12).getStudentList().get(0).getSname();
        List<Clazz> Classes = studentMapper.selectByIf(12);
        System.out.println(Classes);
        List<Clazz> Classes1 = studentMapper.selectByIf(null);
        System.out.println(Classes1);

        List<Clazz> clazzs = studentMapper.selectByWhere(12,"Dep1");
        System.out.println(clazzs);
        List<Clazz> clazzs1 = studentMapper.selectByWhere(null,null);
        System.out.println(clazzs1);

        List<Integer> ids = new ArrayList<>();
        ids.add(12);
        ids.add(13);
        List<Clazz> clazzList = studentMapper.selectByForeach(ids);
        System.out.println(clazzList);

        classMapper.selectByPrimaryKey(13);
        Student student = studentMapper.selectByPrimaryKey(1234);
        System.out.println(student);

        return "success";
    }

    @RequestMapping("/testAnno")
    @ResponseBody
    public String testAnno() {
        List<Student> students = studentNewMapper.getAllStudents();
        System.out.println(students.size());
        Student student = studentNewMapper.getOne(1245);
        System.out.println(student.getSname());
        NewStudent newStudent = studentNewMapper.getStudensNew(1245);
        System.out.println(newStudent.getClazz().getClassname());
        List<Student> newStudent1 = studentNewMapper.getStus(1245);
        System.out.println(newStudent1.get(0).getSname());
        return "success";
    }
}
