package com.muyundefeng.spring.mapper;

import org.apache.ibatis.jdbc.SQL;

/**
 * Created by lisheng on 17-3-7.
 */
public class SelectStudentsProvider {
    public String selectBySno(Integer sno) {
        return new SQL() {{
            SELECT("*");
            FROM("student");
            if ("#{sno}" != null)
                WHERE("sno=#{sno}");
        }}.toString();
    }
}
