package com.baizhi.wangh.service;

import com.baizhi.wangh.dao.ChapterDao;
import com.baizhi.wangh.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;

    //分页
    @Override
    //@Log(name = "章节分页查询")
    public Map queryPage(Integer page, Integer rows, String albumId) {
        Example example = new Example(Chapter.class);
        example.createCriteria().andEqualTo("albumId",albumId);
        HashMap hashMap = new HashMap();
        Integer records = chapterDao.selectCount(null);
        Integer total = records%rows==0?records/rows:records/rows+1;
        List<Chapter> chapters = chapterDao.selectByExampleAndRowBounds(example, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records",records);
        hashMap.put("page",page);
        hashMap.put("total",total);
        hashMap.put("rows",chapters);
        return hashMap;
    }

    //查所有
    @Override
    public List<Chapter> queryAllChapter(String albumId) {
        List<Chapter> chapters = chapterDao.queryAllChapter(albumId);
        return chapters;
    }

    //增
    @Override
    //@Log(name = "章节添加")
    public void save(Chapter chapter) {
        chapterDao.insert(chapter);
    }

    //删
    @Override
    //@Log(name = "章节删除")
    public void delete(String[] id) {
        chapterDao.deleteByIdList(Arrays.asList(id));
    }

    //改
    @Override
    //@Log(name = "章节修改")
    public void update(Chapter chapter) {
        chapterDao.updateByPrimaryKey(chapter);
    }

    @Override
    //@Log(name = "章节修改")
    public void updatePK(Chapter chapter) {
        chapterDao.updateByPrimaryKeySelective(chapter);
    }
}
