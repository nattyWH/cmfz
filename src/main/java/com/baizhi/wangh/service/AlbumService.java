package com.baizhi.wangh.service;

import com.baizhi.wangh.entity.Album;

import java.util.Map;

public interface AlbumService {
    //分页
    Map queryPage(Integer page, Integer rows);

    //增
    void save(Album album);

    //删
    void delete(String[] id);

    //改
    void update(Album album);
    void updateByPK(Album album);

    //查一个
    Album selectOne(Album album);
}
