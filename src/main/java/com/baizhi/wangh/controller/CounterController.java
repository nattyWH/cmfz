package com.baizhi.wangh.controller;

import com.baizhi.wangh.dao.CounterDao;
import com.baizhi.wangh.entity.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("counter")
public class CounterController {
    @Autowired
    CounterDao counterDao;

    //查该用户下的功课的计数器
    @RequestMapping("queryByCounter")
    public Map queryByCounter(String id,String uid){
        HashMap hashMap = new HashMap();
        List<Counter> counters = counterDao.selectByCounter(id,uid);
        hashMap.put("status","200");
        hashMap.put("counters",counters);
        return hashMap;
    }

    //添加计数器
    @RequestMapping("insertCounter")
    public Map insertCounter(Counter counter){
        HashMap hashMap = new HashMap();
        counter.setId(UUID.randomUUID().toString());
        counter.setCreate_date(new Date());
        counterDao.insertCounter(counter);
        List<Counter> counters = counterDao.queryAllCounter();
        hashMap.put("status","200");
        hashMap.put("option",counters);
        return hashMap;
    }

    //删除计数器接口
    @RequestMapping("deleteCounter")
    public Map deleteCounter(String id){
        HashMap hashMap = new HashMap();
        counterDao.deleteCounter(id);
        List<Counter> counters = counterDao.queryAllCounter();
        hashMap.put("status","200");
        hashMap.put("option",counters);
        return hashMap;
    }

    //修改计数器数量接口
    @RequestMapping("updateCounter")
    public Map updateCounter(String id,String uid,Integer counts){
        HashMap hashMap = new HashMap();
        counterDao.updateCounter(id,uid,counts);
        List<Counter> counters = counterDao.queryAllCounter();
        hashMap.put("status","200");
        hashMap.put("option",counters);
        return hashMap;
    }
}
