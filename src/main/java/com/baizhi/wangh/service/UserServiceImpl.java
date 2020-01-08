package com.baizhi.wangh.service;

import com.baizhi.wangh.dao.UserDao;
import com.baizhi.wangh.entity.User;
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
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    //Log(name = "用户分页查询")
    public Map queryPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        Integer records = userDao.selectCount(null);
        Integer total = records%rows==0?records/rows:records/rows+1;
        List<User> users = userDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records",records);
        hashMap.put("page",page);
        hashMap.put("total",total);
        hashMap.put("rows",users);
        return hashMap;
    }

    @Override
    //Log(name = "用户添加")
    public void save(User user) {
        userDao.insert(user);
    }

    @Override
    //Log(name = "用户删除")
    public void delete(String[] id) {
        userDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    //Log(name = "用户修改")
    public void update(User user) {
        userDao.updateByPrimaryKey(user);
    }

    @Override
    //Log(name = "用户修改")
    public void updatePK(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }
}
