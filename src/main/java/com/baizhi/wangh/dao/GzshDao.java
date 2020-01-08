package com.baizhi.wangh.dao;

import com.baizhi.wangh.entity.Gzguru;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface GzshDao extends Mapper<Gzguru> {
    //添加关注上师
    void insertGzsh(@Param("id") String id, @Param("user_id") String user_id, @Param("guru_id") String guru_id);
}
