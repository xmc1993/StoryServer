package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.BadgeTypeDao;
import cn.edu.nju.software.entity.BadgeType;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.BadgeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class BadgeTypeServiceImpl implements BadgeTypeService {

    @Autowired
    private BadgeTypeDao badgeTypeDao;


    @Override
    public BadgeType saveBadgeType(BadgeType badgeType) {
        if (badgeTypeDao.saveBadgeType(badgeType)) {
            return badgeType;
        }
        return null;
    }

    @Override
    public boolean updateBadgeType(BadgeType badgeType) {
        return badgeTypeDao.updateBadgeType(badgeType);
    }

    @Override
    public boolean deleteBadgeType(int id) {
        return badgeTypeDao.deleteBadgeType(id);
    }

    @Override
    public BadgeType getBadgeTypeById(int id) {
        return badgeTypeDao.getBadgeTypeById(id);
    }

    @Override
    public List<BadgeType> getAllBadgeTypeByPage(int page, int pageSize) {
        int offset = page*pageSize;
        int limit = pageSize;
        return badgeTypeDao.getAllBadgeTypeByPage(offset, limit);
    }
    
	@Override
	public BadgeType getBadgeTypeByBadgeId(Integer badgeId) {
		return badgeTypeDao.getBadgeTypeByBadgeId(badgeId);
	}

	@Override
	public PageInfo<BadgeType> getBadgeTypeListByPage(Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		List<BadgeType> bageTypeList = badgeTypeDao.getBadgeTypeList();
		PageInfo<BadgeType> pageInfo= new PageInfo<BadgeType>(bageTypeList);
		return pageInfo;
	}
}
