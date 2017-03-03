package com.muyundefeng.spring.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muyundefeng.spring.dao.BlogDAO;
import com.muyundefeng.spring.dao.CommentDAO;
import com.muyundefeng.spring.entity.Blog;
import com.muyundefeng.spring.entity.Comment;
import com.muyundefeng.spring.service.BlogService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lisheng on 17-3-2.
 */
@Service
public class BlogServiceImpl implements BlogService {

    private static final String TYPE[] = {"全部", "编程语言", "机器学习", "服务器开发", "自然语言处理", "大数据", "移动开发"};

    @Autowired
    BlogDAO blogDAO;

    @Autowired
    CommentDAO commentDAO;

    @Override
    public String addBlog(String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = null;
        try {
            map = objectMapper.readValue(body, Map.class);
        } catch (IOException e) {
            return "parse json fail!";
        }
        String title = map.get("blog_title").toString();
        String conetnt = map.get("blog_content").toString();
        Integer cateId = (Integer) map.get("blog_cateId");
        Integer isOrigina = (Integer) map.get("blog_isOrigina");
        String originaCite = map.get("blog_cite_origina").toString();
        return blogDAO.addBlog(title, conetnt, cateId, isOrigina, originaCite);
    }

    @Override
    public String getComments(String blogId, String pageIndex, String pageCount) {
        int pagIndex = Integer.parseInt(pageIndex);
        int pagCount = Integer.parseInt(pageCount);
        List<Comment> commentList = commentDAO.getComments();
        List<Comment> commentSpec = new ArrayList<>();
        for (Comment comment : commentList) {
            if (comment.getBlogId().equals(blogId))
                commentSpec.add(comment);
        }
        List<Comment> subComments = null;
        int total = commentSpec.size();
        int maxPage = total % pagCount == 0 ? total / pagCount : total / pagCount + 1;
        if (pagIndex > maxPage) {
            return "error page index";
        } else {
            if (total - (pagIndex - 1) * pagCount < pagCount) {
                subComments = commentSpec.subList((pagIndex - 1) * pagCount, total);
            } else {
                subComments = commentSpec.subList((pagIndex - 1) * pagCount, pagIndex * pagCount);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 1);
        jsonObject.put("msg", "success");
        jsonObject.put("total", total);
        jsonObject.put("details", subComments);
        return jsonObject.toString();
    }

    @Override
    public String deleteBlog(String blogId) {
        return blogDAO.deleteBlogById(blogId);
    }

    @Override
    public String getBlogList(int cateId) {
        List<Blog> blogs = blogDAO.selectAllBlogs();
        JSONArray jsonArray = new JSONArray();
        int index = 0;
        for (Blog blog : blogs) {
            if (blog.getBlogCateid() == cateId) {
                String blogTitle = blog.getBlogTitle();
                String cate = TYPE[blog.getBlogCateid()];
                int commnetNum = blog.getBlogCommentNum();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title", blogTitle);
                jsonObject.put("cate", cate);
                jsonObject.put("commentNum", commnetNum);
                jsonArray.put(index, jsonObject);
                index++;
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "success");
        jsonObject.put("list", jsonArray);
        return jsonObject.toString();
    }

    @Override
    public String getSingleBlog(String blogId) {
        Blog blog = blogDAO.getSingeBlog(blogId);
        ObjectMapper objectMapper = new ObjectMapper();
        String str = null;
        try {
            str =  objectMapper.writeValueAsString(blog);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public String addComment(String body) {
        String str = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> info = objectMapper.readValue(body, Map.class);
            String blogId = info.get("blog_id").toString();
            String comment_content = info.get("comment_content").toString();
            String comment_user = info.get("commnet_user").toString();
            str = commentDAO.addCommnet(blogId, comment_content, comment_user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
