package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.AdminDao;
import cn.edu.nju.software.entity.Admin;
import cn.edu.nju.software.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;


    @Override
    public Admin getAdminByUsername(String username) {
        return adminDao.getAdminByUsername(username);
    }

    @Override
    public Admin getAdminByAccessToken(String accessToken) {
        return adminDao.getAdminByAccessToken(accessToken);
    }
}
