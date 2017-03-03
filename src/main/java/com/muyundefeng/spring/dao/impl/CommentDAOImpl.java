package com.muyundefeng.spring.dao.impl;

import com.muyundefeng.spring.Utils.HashUtils;
import com.muyundefeng.spring.dao.CommentDAO;
import com.muyundefeng.spring.entity.Comment;
import com.muyundefeng.spring.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by lisheng on 17-3-2.
 */
@Repository
public class CommentDAOImpl implements CommentDAO {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public String addCommnet(String blogId, String content, String user) {
        Comment comment = new Comment();
        comment.setBlogId(blogId);
        comment.setCommentContent(content);
        comment.setCommnetUser(user);
        comment.setCommentTime(new Date());
        comment.setCommentId(HashUtils.generateHash(user+new Date().toString()));
        return commentMapper.insert(comment) == 0?"fail":"success";
    }

    @Override
    public List<Comment> getComments() {
        return commentMapper.selectAll();
    }
}
