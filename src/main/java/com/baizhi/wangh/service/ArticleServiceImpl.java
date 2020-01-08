package com.baizhi.wangh.service;

import com.baizhi.wangh.dao.ArticleDao;
import com.baizhi.wangh.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;

    //分页
    @Override
    //@Log(name = "文章分页查询")
    public Map queryPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        Integer records = articleDao.selectCount(null);
        Integer total = records%rows==0?records/rows:records/rows+1;
        List<Article> articles = articleDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records",records);
        hashMap.put("page",page);
        hashMap.put("total",total);
        hashMap.put("rows",articles);
        return hashMap;
    }

    //增
    @Override
    //@Log(name = "文章添加")
    public void save(Article article) {
        articleDao.insert(article);
    }


    //删
    @Override
    //@Log(name = "文章删除")
    public void delete(String[] id) {
        articleDao.deleteByIdList(Arrays.asList(id));
    }

    //改
    @Override
    //@Log(name = "文章修改")
    public void update(Article article) {
        articleDao.updateByPrimaryKey(article);
    }

    @Override
    //@Log(name = "文章修改")
    public void updatePK(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

}
