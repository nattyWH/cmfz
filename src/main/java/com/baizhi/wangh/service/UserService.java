package com.baizhi.wangh.service;

import com.baizhi.wangh.entity.User;

import java.util.Map;

public interface UserService {

    //分页
    Map queryPage(Integer page,Integer rows);

    //增
    void save(User user);

    //删
    void delete(String[] id);

    //改
    void update(User user);

    void updatePK(User user);

}
