package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.entity.Agree;
import cn.edu.nju.software.entity.StoryUserLog;
import cn.edu.nju.software.entity.WorkUserLog;
import cn.edu.nju.software.service.AgreeService;
import cn.edu.nju.software.service.StatisticService;
import cn.edu.nju.software.service.StoryUserLogService;
import cn.edu.nju.software.service.WorkUserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/8/23.
 */

@Service
public class StatisticServiceImpl implements StatisticService{
    @Autowired
    private StoryUserLogService storyUserLogService;
    @Autowired
    private WorkUserLogService workUserLogService;
    @Autowired
    private AgreeService agreeService;


    @Override
    public void refreshAgreeStatistic() {
        List<Agree> agrees = agreeService.extractNewRecords(new Date());
    }

    @Override
    public void refreshWorksUserLog() {
        List<WorkUserLog> workUserLogs = workUserLogService.extractNewRecords(new Date());
    }

    @Override
    public void refreshStoryUserLog() {
        List<StoryUserLog> storyUserLogs = storyUserLogService.extractNewRecords(new Date());
    }
}
