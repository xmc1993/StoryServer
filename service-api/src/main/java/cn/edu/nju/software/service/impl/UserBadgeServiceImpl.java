package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.UserBadgeDao;
import cn.edu.nju.software.entity.UserBadge;
import cn.edu.nju.software.service.UserBadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuyu
 * @create 2017-08-28 下午7:13
 */
@Service
public class UserBadgeServiceImpl implements UserBadgeService {
    @Autowired
    UserBadgeDao userBadgeDao;

    @Override
    public Boolean saveUserBadge(UserBadge userBadge) {
        return userBadgeDao.saveUserBadge(userBadge);
    }

    @Override
    public Boolean deleteUserBadgeByUserId(Integer userId) {
        return userBadgeDao.deleteUserBadgeByUserId(userId);
    }

    @Override
    public Boolean getUserBadgeByUserId(Integer userId) {
        return userBadgeDao.getUserBadgeByUserId(userId);
    }
}
