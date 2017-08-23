package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.WorkUserLogDao;
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
    @Override
    public boolean saveWorkUserLog(WorkUserLog workUserLog){
        return workUserLogDao.insertWorkUserLog(workUserLog);
    }

    @Override
    public List<WorkUserLog> extractNewRecords(Date lastExtractTime) {
        return workUserLogDao.extractNewRecords(lastExtractTime);
    }
}
