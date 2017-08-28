package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.UserBadge;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBadgeDao {

    Boolean saveUserBadge(UserBadge userBadge);

    Boolean deleteUserBadgeByUserId(Integer userId);

    Boolean getUserBadgeByUserId(Integer userId);
}
