package com.baizhi.wangh.service;

import com.baizhi.wangh.dao.AlbumDao;
import com.baizhi.wangh.entity.Album;
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
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumDao albumDao;

    //分页
    @Override
    //@Log(name = "专辑分页查询")
    public Map queryPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        Integer records = albumDao.selectCount(null);
        Integer total = records%rows==0?records/rows:records/rows+1;
        List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records",records);
        hashMap.put("page",page);
        hashMap.put("total",total);
        hashMap.put("rows",albums);
        return hashMap;
    }

    //增
    @Override
    //@Log(name = "专辑添加")
    public void save(Album album) {
        albumDao.insert(album);
    }

    //删
    @Override
    //@Log(name = "专辑删除")
    public void delete(String[] id) {
        albumDao.deleteByIdList(Arrays.asList(id));
    }

    //改
    @Override
    //@Log(name = "专辑修改")
    public void update(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }

    @Override
    //@Log(name = "专辑修改")
    public void updateByPK(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }

    //查一个
    @Override
    //@Log(name = "专辑查一个")
    public Album selectOne(Album album) {
        Album album1 = albumDao.selectOne(album);
        return album1;
    }
}
