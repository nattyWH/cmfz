package com.baizhi.wangh.service;

import com.baizhi.wangh.entity.Banner;
import com.baizhi.wangh.entity.BannerDto;

public interface BannerService {
    //分页
    public BannerDto queryPage(Banner banner, Integer page, Integer count);

    //增
    public void save(Banner banner);

    //删
    public void updateStatus(String id);

    //改
    public void update(Banner banner);
    public void updateByPK(Banner banner);
}
