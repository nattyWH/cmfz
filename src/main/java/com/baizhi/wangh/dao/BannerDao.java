package com.baizhi.wangh.dao;

import com.baizhi.wangh.entity.Banner;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BannerDao extends Mapper<Banner>, DeleteByIdListMapper<Banner,String> {
    //删除    -----修改轮播图状态
    void updateStatus(String id);

    List<Banner> queryBannersByTime();
}
