package com.baizhi.wangh.controller;

import com.baizhi.wangh.dao.GuruDao;
import com.baizhi.wangh.dao.GzshDao;
import com.baizhi.wangh.entity.Guru;
import com.baizhi.wangh.entity.Gzguru;
import com.baizhi.wangh.service.GuruService;
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
@RequestMapping("guru")
public class GuruController {
    @Autowired
    GuruService guruService;
    @Autowired
    GuruDao guruDao;
    @Autowired
    GzshDao gzshDao;

    //查询所有上师
    @RequestMapping("queryAllGuru")
    public List<Guru> queryAllGuru(){
        List<Guru> gurus = guruService.queryAll();
        return gurus;
    }

    //分页查询
    @RequestMapping("queryPageGuru")
    public Map queryPageGuru(Integer page,Integer rows){
        Map map = guruService.queryPage(page, rows);
        return map;
    }

    //增删改
    @RequestMapping("/gu")
    public Map gu(Guru guru, String oper,String[] id){
        HashMap hashMap = new HashMap();
        String guruId = UUID.randomUUID().toString();
        if (oper.equals("add")){
            guru.setId(guruId);
            guruService.save(guru);
            hashMap.put("guruId",guruId);
        }
        if(oper.equals("del")){
            guruService.delete(id);
        }
        if(oper.equals("edit")){
            guruService.update(guru);
            hashMap.put("guruId",guru.getId());
        }
        return hashMap;
    }

    //文件上传
    @RequestMapping("uploadGuru")
    public Map uploadAlbum(MultipartFile photo, String guruId, HttpSession session, HttpServletRequest request){
        HashMap hashMap = new HashMap();
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/photo/");
        // 判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(photo, request, "/upload/photo/");
        // 文件上传 工具类完成
        // 更新数据库信息
        Guru guru = new Guru();
        guru.setId(guruId);
        guru.setPhoto(http);
        guruService.updatePK(guru);
        hashMap.put("status", 200);
        return hashMap;
    }

    //展示上师列表接口
    @RequestMapping("showGuruAll")
    public Map showGuruAll(){
        HashMap hashMap = new HashMap();
        List<Guru> gurus = guruDao.selectAll();
        hashMap.put("status","200");
        hashMap.put("message","上师都在这了！");
        hashMap.put("guru",gurus);
        return hashMap;
    }

    //添加关注上师
    @RequestMapping("insertGzsh")
    public Map insertGzsh(String user_id,String guru_id){
        HashMap hashMap = new HashMap();
        String id = UUID.randomUUID().toString();
        gzshDao.insertGzsh(id,user_id,guru_id);
        List<Gzguru> gzgurus = gzshDao.selectAll();
        hashMap.put("status","200");
        hashMap.put("message","关注上师都在这了！");
        hashMap.put("list",gzgurus);
        return  hashMap;
    }
}
