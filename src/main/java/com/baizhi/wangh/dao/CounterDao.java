package com.baizhi.wangh.dao;

import com.baizhi.wangh.entity.Counter;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CounterDao extends Mapper<Counter> {

    //展示该用户下的计数器
    List<Counter> selectByCounter(@Param("id") String id, @Param("uid") String uid);
    //查所有计数器
    List<Counter> queryAllCounter();
    //添加计数器
    void insertCounter(Counter counter);
    //删除计数器
    void deleteCounter(String id);

    //更改计数器
    void updateCounter(@Param("id") String id,@Param("uid") String uid,@Param("counts") Integer counts);
}
