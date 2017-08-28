package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.UserBadge;

public interface UserBadgeService {
    Boolean saveUserBadge(UserBadge userBadge);
    Boolean deleteUserBadgeByUserId(Integer userId);
    Boolean getUserBadgeByUserId(Integer userId);
}
