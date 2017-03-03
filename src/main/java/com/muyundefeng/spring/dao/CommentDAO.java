package com.muyundefeng.spring.dao;

import com.muyundefeng.spring.entity.Comment;

import java.util.List;

/**
 * Created by lisheng on 17-3-2.
 */
public interface CommentDAO {

    public String addCommnet(String blogId,String content,String user);

    public List<Comment> getComments();


}
