package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.UserBadgeDao;
import cn.edu.nju.software.entity.UserBadge;
import cn.edu.nju.software.service.UserBadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuyu
 * @create 2017-08-28 下午7:13
 */
@Service
public class UserBadgeServiceImpl implements UserBadgeService {
    @Autowired
    UserBadgeDao userBadgeDao;

    @CacheEvict(value = "badge", key = "#userBadge.userId")
    @Override
    public Boolean saveUserBadge(UserBadge userBadge) {
        //如果徽章已经存在则不再重复获取 TODO 更高层判断
        if (getUserBadge(userBadge.getBadgeId(), userBadge.getUserId()) != null) {
            return false;
        }
        Boolean aBoolean = userBadgeDao.saveUserBadge(userBadge);
        return aBoolean;
    }

    @CacheEvict(value = "badge", key = "#userId")
    @Override
    public Boolean deleteUserBadgeByUserId(Integer userId) {
        return userBadgeDao.deleteUserBadgeByUserId(userId);
    }

    @Override
    public List<UserBadge> getUserBadgeByUserId(Integer userId) {
        return userBadgeDao.getUserBadgeByUserId(userId);
    }

    @Override
    public UserBadge getUserBadge(int badgeId, int userId) {
        return userBadgeDao.getUserBadge(badgeId, userId);
    }
}
