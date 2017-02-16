package com.muyundefeng.spring.dao;


import com.muyundefeng.spring.entity.Personinfo;

import java.util.List;

/**
 * Created by lisheng on 17-2-16.
 */
public interface PersonDAO {

    List<Personinfo> selectAll();

    Personinfo selectByPrimayId(String id);

    void insetPerson(Personinfo personinfo);
}
