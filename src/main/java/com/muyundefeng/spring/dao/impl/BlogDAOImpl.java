package com.muyundefeng.spring.dao.impl;

import com.muyundefeng.spring.Utils.HashUtils;
import com.muyundefeng.spring.dao.BlogDAO;
import com.muyundefeng.spring.entity.Blog;
import com.muyundefeng.spring.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by lisheng on 17-3-2.
 */
@Repository
public class BlogDAOImpl implements BlogDAO {

    @Autowired
    BlogMapper blogMapper;

    @Override
    public String addBlog(String blogTitle, String content, int cateId, int isOrigina, String cite) {
        Blog blog = getBlog(0,0,0,blogTitle,content,cateId,isOrigina,cite);
        return blogMapper.insert(blog) == 0?"fail":"success";
    }

    @Override
    public String deleteBlogById(String blogId) {
        return blogMapper.deleteByPrimaryKey(blogId)==0?"fail":"success";
    }

    @SuppressWarnings("Since15")
    @Override
    public List<Blog> selectAllBlogs() {

        List<Blog> blogs = blogMapper.selectAll();
        blogs.sort(new Comparator<Blog>() {
            @Override
            public int compare(Blog o1, Blog o2) {
                if(o1.getBlogReleaseTime().after(o2.getBlogReleaseTime()))
                    return 1;
                if(o1.getBlogReleaseTime().before(o2.getBlogReleaseTime()))
                    return -1;
                else
                    return 0;
            }
        });
        return blogs;
    }

    @Override
    public Blog getSingeBlog(String blogId) {
        return blogMapper.selectByPrimaryKey(blogId);
    }

    @Override
    public String updateBlog(String blogId,int support,int hits,int commentNum) {
        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        blog.setBlogHits(hits);
        blog.setBlogSupport(support);
        blog.setBlogCommentNum(commentNum);
        return blogMapper.insert(blog)==0?"fail":"success";
    }

    public Blog getBlog(int hits, int commentNum, int support,String blogTitle, String content, int cateId, int isOrigina, String cite){
        Blog blog = new Blog();
        blog.setBlogHits(hits);
        blog.setBlogTitle(blogTitle);
        blog.setBlogContent(content);
        blog.setBlogReleaseTime(new Date());
        blog.setBlogIsorigina(isOrigina);
        blog.setBlogCiteOrigina(cite);
        blog.setBlogCateid(cateId);
        blog.setBlogId(HashUtils.generateHash(blogTitle+new Date().toString()));
        blog.setBlogWordcount(content.length());
        blog.setBlogCommentNum(commentNum);
        blog.setBlogSupport(support);
        return blog;
    }
}
