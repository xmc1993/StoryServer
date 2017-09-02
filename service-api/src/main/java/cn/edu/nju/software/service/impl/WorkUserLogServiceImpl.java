package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.WorkUserLogDao;
import cn.edu.nju.software.dao.user.AppUserDao;
import cn.edu.nju.software.entity.WorkUserLog;
import cn.edu.nju.software.service.WorkUserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Kt on 2017/6/28.
 */
@Service
public class WorkUserLogServiceImpl implements WorkUserLogService {
    @Autowired
    private WorkUserLogDao workUserLogDao;
    @Autowired
    private AppUserDao appUserDao;
    @Override
    public boolean saveWorkUserLog(WorkUserLog workUserLog){
        //增加用户的收听数量
        appUserDao.newListen(workUserLog.getUserId());
        return workUserLogDao.insertWorkUserLog(workUserLog);
    }

    @Override
    public List<WorkUserLog> extractNewRecords(Date lastExtractTime) {
        return workUserLogDao.extractNewRecords(lastExtractTime);
    }

    @Override
    public Integer getLogAfterSomeDate(Integer userId, String date) {
        return workUserLogDao.getLogAfterSomeDate(userId, date);
    }
}
