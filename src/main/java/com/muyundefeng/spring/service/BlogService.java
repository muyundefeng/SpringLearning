package com.muyundefeng.spring.service;

/**
 * Created by lisheng on 17-3-2.
 */
public interface BlogService {

    public String addBlog(String body);

    public String getComments(String blogId,String pageIndex,String pageCount);

    public String deleteBlog(String blogId);

    public String getBlogList(int cateId);

    public String getSingleBlog(String blog);

    public String addComment(String body);
}
