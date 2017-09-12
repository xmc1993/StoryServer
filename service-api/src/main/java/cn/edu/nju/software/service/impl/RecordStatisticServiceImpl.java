package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.RecordStatisticDao;
import cn.edu.nju.software.entity.RecordStatistic;
import cn.edu.nju.software.service.RecordStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class RecordStatisticServiceImpl implements RecordStatisticService {

    @Autowired
    private RecordStatisticDao recordStatisticDao;

    @Override
    public void saveRecord(int userId) {
        RecordStatistic statistic = recordStatisticDao.getRecordStatisticByUserId(userId);
        // 如果没有记录过重新记录生成全新的记录
        if (statistic == null) {
            statistic = new RecordStatistic();
            statistic.setUpdateTime(new Date());
            statistic.setUserId(userId);
            statistic.setLastRecordTime(new Date());
            statistic.setCurCount(1);
            statistic.setCreateTime(new Date());
            recordStatisticDao.saveRecordStatistic(statistic);
        } else {
            //否则更新记录
            Date now = new Date();
            boolean same = isSameDate(now, statistic.getLastRecordTime());
            //只有不是同一天的记录才需要更新
            if (!same) {
                if (statistic.getCurCount() > statistic.getHistoryMaxCount()) {
                    statistic.setHistoryMaxCount(statistic.getCurCount());
                }
                statistic.setCurCount(1);
                statistic.setLastRecordTime(now);
                statistic.setUpdateTime(now);
                recordStatisticDao.updateRecordStatistic(statistic);
            }
        }
    }

    @Override
    public int getCurCount(int userId) {
        RecordStatistic statistic = recordStatisticDao.getRecordStatisticByUserId(userId);
        if (statistic == null) {
            return 0;
        }

        Date now = new Date();
        boolean same = isSameDate(now, statistic.getLastRecordTime());
        //如果今天没有录制过故事，就更新一下记录
        if (!same) {
            if (statistic.getCurCount() > statistic.getHistoryMaxCount()) {
                statistic.setHistoryMaxCount(statistic.getCurCount());
            }
            statistic.setCurCount(0);
            statistic.setLastRecordTime(now);
            statistic.setUpdateTime(now);
            recordStatisticDao.updateRecordStatistic(statistic);
        }
        return statistic.getCurCount();

    }

    @Override
    public int getHistoryMaxCount(int userId) {
        RecordStatistic statistic = recordStatisticDao.getRecordStatisticByUserId(userId);
        if (statistic == null) {
            return 0;
        } else {
            return statistic.getHistoryMaxCount() > statistic.getCurCount() ? statistic.getHistoryMaxCount() : statistic.getCurCount();
        }
    }

    private static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    private static int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }
}
