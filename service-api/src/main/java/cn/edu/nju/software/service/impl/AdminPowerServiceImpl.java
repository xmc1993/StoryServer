package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.AdminPowerDao;
import cn.edu.nju.software.entity.AdminPower;
import cn.edu.nju.software.service.AdminPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class AdminPowerServiceImpl implements AdminPowerService {

    @Autowired
    private AdminPowerDao adminPowerDao;

    @Override
    public AdminPower saveAdminPower(AdminPower adminPower) {
        if (adminPowerDao.saveAdminPower(adminPower)) {
            return adminPower;
        }
        return null;
    }

    @Override
    public boolean deleteAdminPower(int id) {
        return adminPowerDao.deleteAdminPower(id);
    }

    @Override
    public AdminPower getAdminPowerById(int id) {
        return adminPowerDao.getAdminPowerById(id);
    }

    @Override
    public List<AdminPower> getAdminPowerListAdminId(int id) {
        return adminPowerDao.getAdminPowerListAdminId(id);
    }
}
