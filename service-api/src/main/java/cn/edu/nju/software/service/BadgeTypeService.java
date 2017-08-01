package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.BadgeType;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface BadgeTypeService {

    BadgeType saveBadgeType(BadgeType badgeType);

    boolean updateBadgeType(BadgeType badgeType);

    boolean deleteBadgeType(int id);

    BadgeType getBadgeTypeById(int id);

    List<BadgeType> getAllBadgeTypeByPage(int page, int pageSize);

}
