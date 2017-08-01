package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Badge;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface BadgeService {

    Badge saveBadge(Badge badge);

    boolean updateBadge(Badge badge);

    boolean deleteBadge(int id);

    Badge getBadgeById(int id);

    List<Badge> getBadgeListByTypeId(int typeId);

    List<Badge> getAllBadgeByPage(int page, int pageSize);

}
