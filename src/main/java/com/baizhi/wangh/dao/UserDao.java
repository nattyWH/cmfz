package com.baizhi.wangh.dao;

import com.baizhi.wangh.entity.Course;
import com.baizhi.wangh.entity.User;
import com.baizhi.wangh.entity.UserDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User>, DeleteByIdListMapper<User,String> {

    Integer queryUserByTime(@Param("sex") String sex, @Param("day") Integer day);

    List<UserDto> selectByLocation(String sex);

    //根据phone和密码查询
    User selectByPhone(String phone);

    //查看一个用户下的功课
    List<Course> queryCourseByUser(String id);

    //注册接口
    void registerUser(@Param("id") String id,@Param("phone") String phone);

    //修改个人信息
    void updateUser(@Param("id") String id,@Param("sex") String sex,@Param("location") String location,@Param("password") String password);
    //通过id查用户
    User queryById(String id);

    //查询5个用户不包含自己
    List<User> queryFiveUser(String id);

    //补充个人信息
    void replenishUser(@Param("id") String id,@Param("password")String password,@Param("name")String name,@Param("nick_name")String nick_name,@Param("sex")String sex,@Param("sign")String sign,@Param("location")String location);
}
