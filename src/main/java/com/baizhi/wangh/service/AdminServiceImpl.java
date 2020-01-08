package com.baizhi.wangh.service;

import com.baizhi.wangh.dao.AdminDao;
import com.baizhi.wangh.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;

    @Override
    public Admin login(Admin admin) {
        return adminDao.selectOne(admin);
    }
}
