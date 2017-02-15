package com.muyundefeng.spring.mapper;

import com.muyundefeng.spring.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lisheng on 17-2-14.
 */
public interface StudentsMapper {
    //使用注解返回多个结果集,主要将返回类型改为List即可
    @Select("select * from student where name = #{name}")
    List<Student> selectByName(@Param("name") String studentName);

    @Insert("insert into student values(#{name},#{age},#{address},#{score})")
    void insertStudent(@Param("name") String name,@Param("age") String age,@Param("address") String address,@Param("score") String score);

}
