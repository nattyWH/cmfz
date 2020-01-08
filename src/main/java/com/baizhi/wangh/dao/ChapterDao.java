package com.baizhi.wangh.dao;

import com.baizhi.wangh.entity.Chapter;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ChapterDao extends Mapper<Chapter>, DeleteByIdListMapper<Chapter,String> {
    //查询此专辑下的所有文章
    List<Chapter> queryAllChapter(String albumId);
}
