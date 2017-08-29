package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.UserBadge;

import java.util.List;

public interface UserBadgeService {
    Boolean saveUserBadge(UserBadge userBadge);
    Boolean deleteUserBadgeByUserId(Integer userId);
    List<UserBadge> getUserBadgeByUserId(Integer userId);
}
