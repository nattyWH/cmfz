package com.baizhi.wangh.dao;

import com.baizhi.wangh.entity.Article;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface ArticleDao extends Mapper<Article>, DeleteByIdListMapper<Article,String> {
    Article queryOneById(String id);
}
