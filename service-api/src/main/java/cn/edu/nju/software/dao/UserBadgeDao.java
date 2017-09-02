package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.UserBadge;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBadgeDao {

    Boolean saveUserBadge(UserBadge userBadge);

    Boolean deleteUserBadgeByUserId(Integer userId);

    List<UserBadge> getUserBadgeByUserId(Integer userId);

    UserBadge getUserBadge(int badgeId, int userId);

}
