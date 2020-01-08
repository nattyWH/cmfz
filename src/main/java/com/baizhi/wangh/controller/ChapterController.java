package com.baizhi.wangh.controller;

import com.baizhi.wangh.entity.Album;
import com.baizhi.wangh.entity.Chapter;
import com.baizhi.wangh.service.AlbumService;
import com.baizhi.wangh.service.ChapterService;
import com.baizhi.wangh.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;
    @Autowired
    AlbumService albumService;

    //分页
    @RequestMapping("/queryPage")
    public Map queryPage(Integer page,Integer rows,String albumId){
        Map map = chapterService.queryPage(page, rows, albumId);
        return map;
    }
    @RequestMapping("/queryAll")
    public List<Chapter> queryAll(String albumId){
        return chapterService.queryAllChapter(albumId);
    }

    //增删改
    @RequestMapping("/ch")
    public Map ch(Chapter chapter, String oper, String[] id){
        HashMap hashMap = new HashMap();
        Album album = new Album();
        if(oper.equals("add")){
            String chapterId = UUID.randomUUID().toString();
            chapter.setId(chapterId);
            chapterService.save(chapter);
            album.setId(chapter.getAlbumId());
            Album album1 = albumService.selectOne(album);
            album1.setCount(album1.getCount()+1);
            albumService.updateByPK(album1);
            hashMap.put("chapterId",chapterId);
        }
        if(oper.equals("del")){
            chapterService.delete(id);
            album.setId(chapter.getAlbumId());
            Album album1 = albumService.selectOne(album);
            album1.setCount(album1.getCount()-1);
            albumService.updateByPK(album1);
        }
        if(oper.equals("edit")){
            chapterService.update(chapter);
            hashMap.put("chapterId",chapter.getId());
        }
        return hashMap;
    }

    //文件上传
    @RequestMapping("uploadChapter")
    public Map uploadChapter(MultipartFile url, String chapterId, HttpSession session, HttpServletRequest request) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        //设置接受格式以及响应格式
        String s = url.getOriginalFilename();

        //获取文件名   没有后缀
        String realTitle = s.substring(0, s.lastIndexOf("."));
        HashMap hashMap = new HashMap();
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/music/");
        // 判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(url, request, "/upload/music/");
        // 文件上传 工具类完成
        // 更新数据库信息
        Chapter chapter = new Chapter();
        chapter.setTitle(realTitle);
        chapter.setId(chapterId);
        chapter.setUrl(http);

        //计算文件大小
        Double size = Double.valueOf(url.getSize()/1024/1024);
        chapter.setSize(size);

        //计算音频的时长
        String[] split = http.split("/");

        //获取文件名
        String name = split[split.length - 1];

        //通过文件AudioFileIO对象    解析对象
        AudioFile read = AudioFileIO.read(new File(realPath, name));

        //获取头部信息
        MP3AudioHeader audioHeader = (MP3AudioHeader)read.getAudioHeader();

        //获取音频时长
        int trackLength = audioHeader.getTrackLength();
        String time = trackLength/60 + "分" + trackLength%60 + "秒";
        chapter.setTime(time);

        chapterService.updatePK(chapter);
        hashMap.put("status", 200);
        return hashMap;
    }

    /*
    * 文件下载
    * */
    @RequestMapping("downUpload")
    public void downUpload(String url, HttpServletResponse response, HttpSession session) throws IOException {
        //处理url路径   找到文件
        String[] split = url.split("/");
        String realPath = session.getServletContext().getRealPath("/upload/music/");
        String name = split[split.length-1];
        File file = new File(realPath, name);
        // 调用该方法时必须使用 location.href 不能使用ajax ajax不支持下载
        // 通过url获取本地文件
        response.setHeader("Content-Disposition", "attachment; filename="+name);
        ServletOutputStream outputStream = response.getOutputStream();
        FileUtils.copyFile(file,outputStream);
    }
}
