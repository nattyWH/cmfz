package com.baizhi.wangh.service;

import com.baizhi.wangh.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    //分页
    Map queryPage(Integer page, Integer rows, String albumId);
    //查询此专辑下的所有文章
    List<Chapter> queryAllChapter(String albumId);

    //增
    void save(Chapter chapter);

    //删
    void delete(String[] id);

    //改
    void update(Chapter chapter);
    void updatePK(Chapter chapter);
}
