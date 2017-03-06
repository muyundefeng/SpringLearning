package com.muyundefeng.spring.controller;

/**
 * Created by lisheng on 17-2-24.
 */

import com.muyundefeng.spring.entity.Student;
import com.muyundefeng.spring.mapper.ClassMapper;
import com.muyundefeng.spring.mapper.StudentMapper;
import com.muyundefeng.spring.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @Autowired
    BlogService blogService;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    ClassMapper classMapper;

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
    public String getBlogs(@RequestParam("cateId") String cateId,HttpServletResponse response) {
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
    public String getSingleBlog(@RequestParam("blogId")String blogId,HttpServletResponse response){
        response.setContentType("application/json; charset=utf-8");
        return blogService.getSingleBlog(blogId);
    }
    @RequestMapping("/addComment")
    @ResponseBody
    public String addCommentByBlogId(@RequestBody String Body){
        return blogService.addComment(Body);
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
//        Student student = studentMapper.selectByPrimaryKey(1234);
//        System.out.println(student.getOfClass().getClassname());
//        System.out.println(student);
        System.out.println(classMapper.selectByPrimaryKey(12).getStudentList().size());
        return classMapper.selectByPrimaryKey(12).getStudentList().get(0).getSname();


    }
}
