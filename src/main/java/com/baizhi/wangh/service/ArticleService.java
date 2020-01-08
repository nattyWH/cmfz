package com.baizhi.wangh.service;

import com.baizhi.wangh.entity.Article;

import java.util.Map;

public interface ArticleService {
    //分页查询
    Map queryPage(Integer page,Integer rows);

    //增
    void save(Article article);

    //删
    void delete(String[] id);

    //改
    void update(Article article);

    void updatePK(Article article);
}
