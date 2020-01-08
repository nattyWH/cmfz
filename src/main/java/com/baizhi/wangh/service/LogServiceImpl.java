package com.baizhi.wangh.service;

import com.baizhi.wangh.dao.LogDao;
import com.baizhi.wangh.entity.Logentity;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
    LogDao logDao;

    //查所有
    @Override
    public List<Logentity> queryAll() {
        List<Logentity> logEntities = logDao.selectAll();
        return logEntities;
    }

    //添加
    @Override
    public void save(Logentity logentity) {
        logDao.insert(logentity);
    }

    //分页查询
    @Override
    public Map queryPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        Integer records = logDao.selectCount(null);
        Integer total = records%rows==0?records/rows:records/rows+1;
        List<Logentity> logentities = logDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records",records);
        hashMap.put("page",page);
        hashMap.put("total",total);
        hashMap.put("rows",logentities);
        return hashMap;
    }
}
