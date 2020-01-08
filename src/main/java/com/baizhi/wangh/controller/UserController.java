package com.baizhi.wangh.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.excel.EasyExcel;
import com.baizhi.wangh.dao.AlbumDao;
import com.baizhi.wangh.dao.ArticleDao;
import com.baizhi.wangh.dao.BannerDao;
import com.baizhi.wangh.dao.UserDao;
import com.baizhi.wangh.entity.*;
import com.baizhi.wangh.service.UserService;
import com.baizhi.wangh.util.HttpUtil;
import com.baizhi.wangh.util.SmsUtil;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    BannerDao bannerDao;
    @Autowired
    AlbumDao albumDao;
    @Autowired
    ArticleDao articleDao;


    //导出
    @RequestMapping("imageUpload")
    public void imageUpload(){
        List<User> users = userDao.selectAll();
        String url="D:\\后期项目\\EasyExcel实例\\"+new Date().getTime()+".xls";
        EasyExcel.write(url, User.class)
                .sheet("用户")
                .doWrite(users);
    }

    //导入
    @RequestMapping("leadExcel")
    public void leadExcel(){
        String url = "D:\\后期项目\\EasyExcel实例\\1578214295585.xls";
        EasyExcel.read(url,User.class,new UserListener()).sheet().doRead();
    }



    //分页
    @RequestMapping("userPage")
    public Map queryPageUser(Integer page,Integer rows){
        Map map = userService.queryPage(page, rows);
        return map;
    }

    //增删改
    @RequestMapping("us")
    public Map us(User user, String oper,String[] id){
        HashMap hashMap = new HashMap();
        String userId = UUID.randomUUID().toString();
        if (oper.equals("add")){
            user.setId(userId);
            user.setRigestDate(new Date());
            user.setLastLogin(new Date());
            userService.save(user);
            hashMap.put("userId",userId);
        }
        if(oper.equals("del")){
            userService.delete(id);
        }
        if(oper.equals("edit")){
            user.setRigestDate(new Date());
            user.setLastLogin(new Date());
            userService.update(user);
            hashMap.put("userId",user.getId());
        }
        return hashMap;
    }

    //文件上传
    @RequestMapping("uploadUser")
    public Map uploadAlbum(MultipartFile photo, String userId, HttpSession session, HttpServletRequest request){
        HashMap hashMap = new HashMap();
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/user/");
        // 判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(photo, request, "/upload/user/");
        // 文件上传 工具类完成
        // 更新数据库信息
        User user = new User();
        user.setId(userId);
        user.setPhoto(http);
        userService.updatePK(user);
        hashMap.put("status", 200);
        return hashMap;
    }



    //折线趋势图
    @RequestMapping("showUserTime")
    public Map showUserTime(){
        HashMap hashMap = new HashMap();
        ArrayList manList = new ArrayList();
        manList.add(userDao.queryUserByTime("1",1));
        manList.add(userDao.queryUserByTime("1",7));
        manList.add(userDao.queryUserByTime("1",30));
        manList.add(userDao.queryUserByTime("1",365));
        ArrayList womenList = new ArrayList();
        womenList.add(userDao.queryUserByTime("2",1));
        womenList.add(userDao.queryUserByTime("2",7));
        womenList.add(userDao.queryUserByTime("2",30));
        womenList.add(userDao.queryUserByTime("2",365));
        hashMap.put("man",manList);
        hashMap.put("women",womenList);
        return hashMap;
    }

    @RequestMapping("showUserLocation")
    public Map showUserLocation(){
        HashMap hashMap = new HashMap();

        List<UserDto> manDto = userDao.selectByLocation("1");
        System.out.println(manDto);
        List<UserDto> womenDto = userDao.selectByLocation("2");
        System.out.println(womenDto);
        hashMap.put("man",manDto);
        hashMap.put("women",womenDto);

        return hashMap;
    }
    @RequestMapping("addUser")
    public void addUser(){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setSex("1");
        user.setLocation("北京");
        user.setRigestDate(new Date());
        userDao.insert(user);
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-098516e7efc047bf838f39cc4138a2d4");
        Map map = showUserTime();
        String s = JSONUtils.toJSONString(map);
        System.out.println(s);
        goEasy.publish("cmfz", s);
    }


    //登陆
    @RequestMapping("login")
    public Map login(String phone,String password){
        HashMap hashMap = new HashMap();
        User user = userDao.selectByPhone(phone);
        if(user==null){
            hashMap.put("status","-200");
            hashMap.put("message","用户不存在");
        }else{
            if(user.getPassword().equals(password)){
                hashMap.put("status","200");
                hashMap.put("user",user);
            }else {
                hashMap.put("status","-200");
                hashMap.put("message","密码错误");
            }
        }
        return hashMap;
    }


    //一级页面
    @RequestMapping("onePage")
    // type : all|wen|si
    public Map onePage(String uid,String type,String sub_type){
        HashMap hashMap = new HashMap();
        try {
            if (type.equals("all")){
                List<Banner> banners = bannerDao.queryBannersByTime();
                List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(0, 5));
                List<Article> articles = articleDao.selectAll();
                hashMap.put("status",200);
                hashMap.put("head",banners);
                hashMap.put("albums",albums);
                hashMap.put("articles",articles);
            }else if (type.equals("wen")){
                List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(0, 5));
                hashMap.put("status",200);
                hashMap.put("albums",albums);
            }else {
                if (sub_type.equals("ssyj")){
                    List<Article> articles = articleDao.selectAll();
                    hashMap.put("status",200);
                    hashMap.put("articles",articles);
                }else {
                    List<Article> articles = articleDao.selectAll();
                    hashMap.put("status",200);
                    hashMap.put("articles",articles);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }
        return hashMap;
    }

    //展示功课接口
    @RequestMapping("queryCourseByUser")
    public Map queryCourseByUser(String id){
        HashMap hashMap = new HashMap();
        List<Course> courses = userDao.queryCourseByUser(id);
        if(courses.size()==0){
            hashMap.put("status","-200");
            hashMap.put("message","该用户没有功课");
        }else {
            hashMap.put("status","200");
            hashMap.put("course",courses);
        }
        return hashMap;
    }

    //发送验证码
    @RequestMapping("sendCode")
    public Map sendCode(String phone){
        String s = UUID.randomUUID().toString();
        String code = s.substring(0, 4);
        SmsUtil.send(phone,code);
        Jedis jedis = new Jedis("192.168.71.15",6379);
        //5分钟有效
        jedis.setex(phone,300,code);
        HashMap hashMap = new HashMap();
        if(phone.length()!=11){
            hashMap.put("status","-200");
            hashMap.put("message","短信发送失败");
        }else{
            hashMap.put("status","200");
            hashMap.put("message","短信发送成功");
        }
        return hashMap;
    }

    //注册接口
    @RequestMapping("registerUser")
    public Map registerUser(String code,String phone){
        HashMap hashMap = new HashMap();
        Jedis jedis = new Jedis("192.168.71.15",6379);
        String s = jedis.get(phone);
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setPhone(phone);
        userDao.registerUser(user.getId(),user.getPhone());
        if(s.equals(code)){
            hashMap.put("status","200");
            hashMap.put("message","注册成功");
        }else{
            hashMap.put("status","-200");
            hashMap.put("message","注册失败");
        }


        return hashMap;
    }

    //修改用户个人信息
    @RequestMapping("updateUser")
    public Map updateUser(String id,String password,String location,String sex){
        HashMap hashMap = new HashMap();
        userDao.updateUser(id,sex,location,password);
        User user = userDao.queryById(id);
        hashMap.put("status","200");
        hashMap.put("user",user);
        return hashMap;
    }

    //金刚道友
    @RequestMapping("selectFiveUser")
    public Map selectFiveUser(String id){
        HashMap hashMap = new HashMap();
        List<User> users = userDao.queryFiveUser(id);
        hashMap.put("status","200");
        hashMap.put("user",users);
        return hashMap;
    }

    //补充个人信息
    @RequestMapping("replenishUser")
    public Map replenishUser(String id,String password,String name,String nick_name,String sex,String sign,String location){
        HashMap hashMap = new HashMap();
        userDao.replenishUser(id,password,name,nick_name,sex,sign,location);
        User user = userDao.queryById(id);
        if("".equals(user.getPassword())||"".equals(user.getName())||"".equals(user.getNickName())||"".equals(user.getSex())||"".equals(user.getSign())||"".equals(user.getLocation())){
            hashMap.put("status","-200");
            hashMap.put("message","补充失败");
        }else{
            hashMap.put("status","200");
            hashMap.put("user",user);
        }
        return hashMap;
    }
}
