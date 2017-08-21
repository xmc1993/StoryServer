package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.AdminPowerDao;
import cn.edu.nju.software.entity.AdminPower;
import cn.edu.nju.software.service.AdminPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<AdminPower> getAdminPowerListByAdminId(int id) {
        return adminPowerDao.getAdminPowerListAdminId(id);
    }

    @Override
    public List<Integer> getAdminPowerCodeListByAdminId(int id) {
        List<AdminPower> list = getAdminPowerListByAdminId(id);
        List<Integer> res = new ArrayList<Integer>();
        for (AdminPower adminPower : list) {
            res.add(adminPower.getId());
        }
        return res;
    }
}
