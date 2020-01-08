package com.baizhi.wangh.service;

import com.baizhi.wangh.dao.GuruDao;
import com.baizhi.wangh.entity.Guru;
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
public class GuruServiceImpl implements GuruService {
    @Autowired
    GuruDao guruDao;


    @Override
    //@Log(name = "上师查所有")
    public List<Guru> queryAll() {
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }

    @Override
    //@Log(name = "上师分页查询")
    public Map queryPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        Integer records = guruDao.selectCount(null);
        Integer total = records%rows==0?records/rows:records/rows+1;
        List<Guru> gurus = guruDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records",records);
        hashMap.put("page",page);
        hashMap.put("total",total);
        hashMap.put("rows",gurus);
        return hashMap;
    }

    @Override
    //@Log(name = "上师添加")
    public void save(Guru guru) {
        guruDao.insert(guru);
    }

    @Override
    //@Log(name = "上师删除")
    public void delete(String[] id) {
        guruDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    //@Log(name = "上师修改")
    public void update(Guru guru) {
        guruDao.updateByPrimaryKey(guru);
    }

    @Override
    //@Log(name = "上师修改")
    public void updatePK(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);
    }
}
