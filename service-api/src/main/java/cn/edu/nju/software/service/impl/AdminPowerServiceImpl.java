package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.AdminPowerDao;
import cn.edu.nju.software.entity.AdminPower;
import cn.edu.nju.software.service.AdminPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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


    @CacheEvict(value = "powerCode", key = "#adminPower.adminId")
    @Override
    public AdminPower saveAdminPower(AdminPower adminPower) {
        if (adminPowerDao.saveAdminPower(adminPower)) {
            return adminPower;
        }
        return null;
    }

    //TODO 为了清除缓存强行传了一个adminId进来不知道有没有办法优化一下
    @CacheEvict(value = "powerCode", key = "#adminId")
    @Override
    public boolean deleteAdminPower(int id, int adminId) {
        return adminPowerDao.deleteAdminPower(id);
    }

    @CacheEvict(value = "powerCode", key = "#adminId")
    @Override
    public boolean deleteAdminPowerWithPrimaryKey(int adminId, int code) {
        return adminPowerDao.deleteAdminPowerWithPrimaryKey(adminId, code);
    }

    @Override
    public AdminPower getAdminPowerById(int id) {
        return adminPowerDao.getAdminPowerById(id);
    }

    @Override
    public List<AdminPower> getAdminPowerListByAdminId(int id) {
        return adminPowerDao.getAdminPowerListAdminId(id);
    }


    @Cacheable(value = "powerCode", key = "#id")
    @Override
    public List<Integer> getAdminPowerCodeListByAdminId(int id) {
        List<AdminPower> list = getAdminPowerListByAdminId(id);
        List<Integer> res = new ArrayList<>();
        for (AdminPower adminPower : list) {
            res.add(adminPower.getCodeId());
        }
        return res;
    }

    @Override
    public List<AdminPower> getAdminPowerListByAdminId(int id,int page,int pageSize) {
        int offset = page*pageSize;
        int limit = pageSize;
        return adminPowerDao.getAdminPowerListByAdminId(id,offset, limit);
    }
}
