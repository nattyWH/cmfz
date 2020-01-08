package com.baizhi.wangh.service;

import com.baizhi.wangh.entity.Logentity;

import java.util.List;
import java.util.Map;

public interface LogService {
    //查所有
    List<Logentity> queryAll();

    //添加
    void save(Logentity log);

    //分页
    Map queryPage(Integer page,Integer rows);
}
