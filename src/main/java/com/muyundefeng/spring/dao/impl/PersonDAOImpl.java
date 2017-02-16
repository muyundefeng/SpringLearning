package com.muyundefeng.spring.dao.impl;

import com.muyundefeng.spring.dao.PersonDAO;
import com.muyundefeng.spring.entity.Personinfo;
import com.muyundefeng.spring.mapper.PersoninfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lisheng on 17-2-16.
 */
@Repository
public class PersonDAOImpl implements PersonDAO {

    PersoninfoMapper mapper1;

    @Override
    public List<Personinfo> selectAll() {
        return mapper1.selectAll();
    }

    @Override
    public Personinfo selectByPrimayId(String id) {
        return mapper1.selectByPrimaryKey(id);
    }

    @Override
    public void insetPerson(Personinfo personinfo) {
        mapper1.insert(personinfo);
    }
}
