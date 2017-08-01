package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.BadgeType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface BadgeTypeDao {

    boolean saveBadgeType(BadgeType badgeType);

    boolean updateBadgeType(BadgeType badgeType);

    boolean deleteBadgeType(int id);

    BadgeType getBadgeTypeById(int id);

    List<BadgeType> getAllBadgeTypeByPage(int offset, int limit);

}
