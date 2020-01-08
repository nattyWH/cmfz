package com.baizhi.wangh.controller;

import com.baizhi.wangh.dao.AlbumDao;
import com.baizhi.wangh.dao.ChapterDao;
import com.baizhi.wangh.entity.Album;
import com.baizhi.wangh.entity.Chapter;
import com.baizhi.wangh.service.AlbumService;
import com.baizhi.wangh.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    AlbumService albumService;
    @Autowired
    AlbumDao albumDao;
    @Autowired
    ChapterDao chapterDao;

    //分页
    @RequestMapping("/queryPage")
    public Map queryPage(Integer page,Integer rows){
        Map map = albumService.queryPage(page, rows);
        return  map;
    }

    //增删改
    @RequestMapping("/al")
    public Map al(Album album,String oper,String[] id){
        HashMap hashMap = new HashMap();
        if(oper.equals("add")){
            String albumId = UUID.randomUUID().toString();
            album.setId(albumId);
            albumService.save(album);
            hashMap.put("albumId",albumId);
        }
        if(oper.equals("edit")){
            albumService.update(album);
            hashMap.put("albumId",album.getId());
        }
        if(oper.equals("del")){
            albumService.delete(id);
        }
        return hashMap;
    }

    //文件上传
    @RequestMapping("uploadAlbum")
    public Map uploadAlbum(MultipartFile status, String albumId, HttpSession session, HttpServletRequest request){
        HashMap hashMap = new HashMap();
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        // 判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(status, request, "/upload/img/");
        // 文件上传 工具类完成
        // 更新数据库信息
        Album album = new Album();
        album.setId(albumId);
        album.setStatus(http);
        albumService.updateByPK(album);
        hashMap.put("status", 200);
        return hashMap;
    }

    //专辑详情接口
    @RequestMapping("albumAndChapterAll")
    public Map albumAndChapterAll(String id){
        HashMap hashMap = new HashMap();
        Album album = albumDao.queryAlbumById(id);
        List<Chapter> chapters = chapterDao.queryAllChapter(id);
        if(album==null){
            hashMap.put("status","-200");
            hashMap.put("message","没有该专辑");
        }else {
            if(chapters.size()==0){
                hashMap.put("status","-200");
                hashMap.put("message","该专辑下没有文章");
            }else {
                hashMap.put("status","200");
                hashMap.put("album",album);
                hashMap.put("list",chapters);
            }
        }
        return hashMap;
    }

}
