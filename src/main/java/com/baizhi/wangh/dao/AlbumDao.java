package com.baizhi.wangh.dao;

import com.baizhi.wangh.entity.Album;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface AlbumDao extends Mapper<Album>, DeleteByIdListMapper<Album,String> {

    //根据id查询专辑
    Album queryAlbumById(String id);

}
