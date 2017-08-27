package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.BadgeDao;
import cn.edu.nju.software.entity.Badge;
import cn.edu.nju.software.service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class BadgeServiceImpl implements BadgeService {

    @Autowired
    private BadgeDao badgeDao;


    @Override
    public Badge saveBadge(Badge badge) {
        if (badgeDao.saveBadge(badge)) {
            return badge;
        }
        return null;
    }

    @Override
    public boolean updateBadge(Badge badge) {
        return badgeDao.updateBadge(badge);
    }

    @Override
    public boolean deleteBadge(int id) {
        return badgeDao.deleteBadge(id);
    }

    @Override
    public Badge getBadgeById(int id) {
        return badgeDao.getBadgeById(id);
    }

    @Override
    public List<Badge> getBadgeListByTypeId(int typeId) {
        return badgeDao.getBadgeListByTypeId(typeId);
    }

    @Override
    public List<Badge> getAllBadgeByPage(int page, int pageSize) {
        int offset = page*pageSize;
        int limit = pageSize;
        return badgeDao.getAllBadgeByPage(offset, limit);
    }

	@Override
	public List<Badge> getBadgeOfUser(Integer userId) {
		return badgeDao.getBadgeOfUser(userId);
	}

	@Override
	public Badge getBadgeByMeasureAndType(int measure, int badgeTypeId) {
		return badgeDao.getBadgeByMeasureAndType(measure,badgeTypeId);
	}

}
