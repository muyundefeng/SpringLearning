package com.muyundefeng.spring.mapper;

import com.muyundefeng.spring.entity.NewStudent;
import com.muyundefeng.spring.entity.Student;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * Created by lisheng on 17-3-7.
 */
public interface StudentNewMapper {

    @Select("select * from student")
    List<Student> getAllStudents();
//
    @Select("select * from student where sno=#{sno}")
    Student getOne(@Param("sno") Integer sno);

//
    @Select("select * from student where sno=#{sno}")
    @Results({
            @Result(id = true, column = "sno", property = "sno"),
            @Result(column = "sname", property = "sname"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "dept", property = "dept"),
            @Result(column = "birth", property = "birth"),
            @Result(column = "classId", property = "clazz",
                    one = @One(
                            select = "com.muyundefeng.spring.mapper.ClassMapper.getOneClazz",
                            fetchType = FetchType.EAGER
                    ))
    }
    )
    NewStudent getStudensNew(@Param("sno") Integer sno);

    //动态sql
    @SelectProvider(type = SelectStudentsProvider.class,method = "selectBySno")
    List<Student> getStus(Integer sno);
}