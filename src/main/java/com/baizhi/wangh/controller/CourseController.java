package com.baizhi.wangh.controller;

import com.baizhi.wangh.dao.CourseDao;
import com.baizhi.wangh.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseDao courseDao;

    //添加功课接口
    @RequestMapping("insertCourse")
    public Map insertCourse(Course course){
        HashMap hashMap = new HashMap();
        course.setId(UUID.randomUUID().toString());
        course.setCreate_date(new Date());
        courseDao.insertCourse(course);
        List<Course> courses = courseDao.queryAll();
        hashMap.put("status","200");
        hashMap.put("option",courses);
        return hashMap;
    }

    //删除功课接口
    @RequestMapping("deleteCourse")
    public Map deleteCourse(String id){
        HashMap hashMap = new HashMap();
        courseDao.deleteCourse(id);
        List<Course> courses = courseDao.queryAll();
        hashMap.put("status","200");
        hashMap.put("option",courses);
        return hashMap;
    }
}
