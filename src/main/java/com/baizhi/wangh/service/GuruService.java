package com.baizhi.wangh.service;

import com.baizhi.wangh.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    //查所有
    List<Guru> queryAll();

    //分页
    Map queryPage(Integer page,Integer rows);

    //增
    void save(Guru guru);

    //删
    void delete(String[] id);

    //改
    void update(Guru guru);

    void updatePK(Guru guru);

}
