package com.baizhi.wangh.controller;

import com.baizhi.wangh.entity.Banner;
import com.baizhi.wangh.entity.BannerDto;
import com.baizhi.wangh.service.BannerService;
import com.baizhi.wangh.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    //分页
    @RequestMapping("/queryPage")
    @ResponseBody
    public BannerDto queryPage(Banner banner,Integer page, Integer rows){
        BannerDto bannerDto = bannerService.queryPage(banner, page, rows);
        return bannerDto;
    }

    //增删改
    @RequestMapping("/ba")
    public Map ba(Banner banner,String oper){
        HashMap hashMap = new HashMap();
        String id = UUID.randomUUID().toString();
        if (oper.equals("add")){
            banner.setId(id);
            bannerService.save(banner);
            hashMap.put("bannerId",id);
        }
        if(oper.equals("del")){
            bannerService.updateStatus(banner.getId());
        }
        if(oper.equals("edit")){
            bannerService.update(banner);
            hashMap.put("bannerId",banner.getId());
        }
        return hashMap;
    }

    //图片上传
    @RequestMapping("/uploadBanner")
    public Map uploadBanner(MultipartFile url, String bannerId, HttpSession session, HttpServletRequest request){
        HashMap hashMap = new HashMap();
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/img");
        //判断该文件是否存在
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(url, request, "/upload/img/");
            Banner banner = new Banner();
            banner.setId(bannerId);
            banner.setUrl(http);
            bannerService.updateByPK(banner);
        hashMap.put("status",200);
        return hashMap;
    }
}
