package com.baizhi.wangh.service;

import com.baizhi.wangh.dao.BannerDao;
import com.baizhi.wangh.entity.Banner;
import com.baizhi.wangh.entity.BannerDto;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("bannerService")
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerDao bannerDao;

    //分页
    @Override
    /*
    * page 当前页数
    * count 每页展示的条数
    * */
    //@Log(name = "轮播图分页查询")
    public BannerDto queryPage(Banner banner, Integer page, Integer count) {
        BannerDto bannerDto = new BannerDto();
        bannerDto.setPage(page);
        int i = bannerDao.selectCount(banner);
        bannerDto.setRecords(i);
        bannerDto.setTotal(i%count==0?i/count:i/count+1);
        Integer inx=(page-1)*count;
        bannerDto.setRows(bannerDao.selectByRowBounds(banner,new RowBounds(inx,count)));
        return bannerDto;
    }

    //增
    @Override
    //@Log(name = "轮播图添加")
    public void save(Banner banner) {
        bannerDao.insert(banner);
    }

    //删
    @Override
    //@Log(name = "轮播图删除")
    public void updateStatus(String id) {
        Banner banner = bannerDao.selectByPrimaryKey(id);
        if("1".equals(banner.getStatus())){
            banner.setStatus("2");
        }else{
            banner.setStatus("1");
        }
        bannerDao.updateStatus(id);
    }

    //改
    @Override
    //@Log(name = "轮播图修改")
    public void update(Banner banner) {
        bannerDao.updateByPrimaryKey(banner);
    }

    @Override
    //@Log(name = "轮播图修改")
    public void updateByPK(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }
}
