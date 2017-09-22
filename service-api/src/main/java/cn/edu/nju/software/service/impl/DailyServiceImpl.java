package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.DailyDao;
import cn.edu.nju.software.entity.Daily;
import cn.edu.nju.software.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class DailyServiceImpl implements DailyService {

    @Autowired
    private DailyDao dailyDao;


    @Override
    public Daily saveDaily(Daily daily) {
        if (dailyDao.saveDaily(daily)) {
            return daily;
        }
        return null;
    }

    @Override
    public boolean updateDaily(Daily daily) {
        return dailyDao.updateDaily(daily);
    }

    @Override
    public boolean deleteDaily(int id) {
        return dailyDao.deleteDaily(id);
    }

    @Override
    public Daily getDailyById(int id) {
        return dailyDao.getDailyById(id);
    }

    @Override
    public List<Daily> getDailyListByUserId(int userId, int visibility, int page, int pageSize) {
        int offset = page*pageSize;
        int limit = pageSize;
        return dailyDao.getDailyListByUserId(userId, visibility, offset, limit);
    }

    @Override
    public Integer getDailyCountByUserId(int userId, int visibility) {
        return dailyDao.getDailyCountByUserId(userId, visibility);
    }

    @Override
    public Daily getDraftDaily(Integer userId) {
        return dailyDao.getDraftDaily(userId);
    }

    @Override
    public boolean updateDraftDaily(Daily daily) {
        return updateDaily(daily);
    }

    @Override
    public boolean deleteDraftDaily(Integer userId) {
        return dailyDao.deleteDraftDaily(userId);
    }

    @Override
    public Daily saveDraftDaily(Daily daily) {
        //设置为草稿
        if (getDraftDaily(daily.getUserId()) != null) {
            return null;
        }
        daily.setDraft(1);
        return saveDaily(daily);
    }


}
