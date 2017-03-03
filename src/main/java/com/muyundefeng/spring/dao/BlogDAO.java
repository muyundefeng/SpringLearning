package com.muyundefeng.spring.dao;

import com.muyundefeng.spring.entity.Blog;

import java.util.List;

/**
 * Created by lisheng on 17-3-2.
 */
public interface BlogDAO {

    public String addBlog(String blogTitle,String content,int cateId,int isOrigina,String cite);

    public String deleteBlogById(String blogId);

    public List<Blog> selectAllBlogs();

    public Blog getSingeBlog(String blogId);

    public String updateBlog(String blogId,int support,int hits,int commentNum);
}
