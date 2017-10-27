package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.RecordStatisticDao;
import cn.edu.nju.software.entity.RecordStatistic;
import cn.edu.nju.software.service.RecordStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
			// 否则更新记录
			Date now = new Date();
			int days = differentDaysByMillisecond(statistic.getLastRecordTime(), now);
			// 只有相差一天以上的记录才需要更新
			if (days == 1) {
				statistic.setCurCount(statistic.getCurCount() + 1);
				statistic.setLastRecordTime(now);
				statistic.setUpdateTime(now);
				if (statistic.getCurCount() > statistic.getHistoryMaxCount()) {
					statistic.setHistoryMaxCount(statistic.getCurCount());
				}
				recordStatisticDao.updateRecordStatistic(statistic);
				// 相差2天以上才把连续录制时间改为1
			} else if (days > 2 || days == 2) {
				statistic.setCurCount(1);
				statistic.setLastRecordTime(now);
				statistic.setUpdateTime(now);
				recordStatisticDao.updateRecordStatistic(statistic);
				// 考虑到有curCount为0的情况
			} else if (days == 0) {
				if (statistic.getCurCount() == 0) {
					statistic.setCurCount(1);
					statistic.setUpdateTime(now);
					statistic.setLastRecordTime(now);
					recordStatisticDao.updateRecordStatistic(statistic);
				}
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
		int days = differentDaysByMillisecond(statistic.getLastRecordTime(), now);
		// 如果超过2天没有录制过故事，就更新一下记录
		if (days == 2) {
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
			return statistic.getHistoryMaxCount() > statistic.getCurCount() ? statistic.getHistoryMaxCount()
					: statistic.getCurCount();
		}
	}

	/*
	 * 这里我觉得是有问题的，应该是判断现在和lastRecordTime的时间差，如果是24小时内，返回0(即：不管)，如果是48小时内，返回1(即：
	 * 连续录制天数加一)， 如果超过48个小时，返回2(即：把curTime赋值为0)
	 */
	/*
	 * private static boolean isSameDate(Date date1, Date date2) { Calendar cal1
	 * = Calendar.getInstance(); cal1.setTime(date1);
	 * 
	 * Calendar cal2 = Calendar.getInstance(); cal2.setTime(date2);
	 * 
	 * boolean isSameYear = cal1.get(Calendar.YEAR) == cal2 .get(Calendar.YEAR);
	 * boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) ==
	 * cal2.get(Calendar.MONTH); boolean isSameDate = isSameMonth &&
	 * cal1.get(Calendar.DAY_OF_MONTH) == cal2 .get(Calendar.DAY_OF_MONTH);
	 * 
	 * return isSameDate; }
	 */

	private static int differentDaysByMillisecond(Date date1, Date date2) {
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
		return days;
	}
}
