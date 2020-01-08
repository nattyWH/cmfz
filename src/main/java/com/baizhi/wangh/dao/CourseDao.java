package com.baizhi.wangh.dao;

import com.baizhi.wangh.entity.Course;

import java.util.List;

public interface CourseDao {

    //添加功课
    void insertCourse(Course course);

    //查所有功课
    List<Course> queryAll();

    //删除功课
    void deleteCourse(String id);
}
