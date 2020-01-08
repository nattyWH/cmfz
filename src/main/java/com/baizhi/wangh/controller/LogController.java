package com.baizhi.wangh.controller;

import com.baizhi.wangh.entity.Logentity;
import com.baizhi.wangh.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/log")
@Slf4j
public class LogController {
    @Autowired
    LogService logService;

    //查所有
    @RequestMapping("/queryAll")
    public List<Logentity> queryAll(){
        List<Logentity> logs = logService.queryAll();
        return logs;
    }

    //分页
    @RequestMapping("/queryPage")
    public Map queryPage(Integer page,Integer rows){
        Map map = logService.queryPage(page, rows);
        return map;
    }
}
