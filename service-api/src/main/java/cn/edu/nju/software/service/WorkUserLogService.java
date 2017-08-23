package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.WorkUserLog;

import java.util.Date;
import java.util.List;

/**
 * Created by Kt on 2017/6/28.
 */
public interface WorkUserLogService {

    boolean saveWorkUserLog(WorkUserLog workUserLog);

    List<WorkUserLog> extractNewRecords(Date lastExtractTime);
}
