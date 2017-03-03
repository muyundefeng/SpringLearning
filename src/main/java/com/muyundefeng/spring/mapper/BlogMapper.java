package com.muyundefeng.spring.mapper;

import com.muyundefeng.spring.entity.Blog;
import java.util.List;

public interface BlogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog_table
     *
     * @mbggenerated Thu Mar 02 14:46:56 CST 2017
     */
    int deleteByPrimaryKey(String blogId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog_table
     *
     * @mbggenerated Thu Mar 02 14:46:56 CST 2017
     */
    int insert(Blog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog_table
     *
     * @mbggenerated Thu Mar 02 14:46:56 CST 2017
     */
    Blog selectByPrimaryKey(String blogId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog_table
     *
     * @mbggenerated Thu Mar 02 14:46:56 CST 2017
     */
    List<Blog> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog_table
     *
     * @mbggenerated Thu Mar 02 14:46:56 CST 2017
     */
    int updateByPrimaryKey(Blog record);
}