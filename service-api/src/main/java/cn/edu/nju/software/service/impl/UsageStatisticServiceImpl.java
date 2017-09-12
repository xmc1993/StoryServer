package cn.edu.nju.software.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.nju.software.dao.UsageStatisticDao;
import cn.edu.nju.software.entity.UsageStatistic;
import cn.edu.nju.software.service.UsageStatisticService;

/**
 * @author zs
 * @version 创建时间：2017年9月12日 上午11:52:57
 */
@Service
public class UsageStatisticServiceImpl implements UsageStatisticService {

	@Autowired
	UsageStatisticDao usageStatisticDao;

	@Override
	public void saveUsage(int userId) {
		UsageStatistic statistic = usageStatisticDao.getUsageStatisticByUserId(userId);
		// 如果没有记录过重新记录生成全新的记录
		if (statistic == null) {
			statistic = new UsageStatistic();
			statistic.setUpdateTime(new Date());
			statistic.setUserId(userId);
			statistic.setLastUsageTime(new Date());
			statistic.setCurCount(1);
			statistic.setCreateTime(new Date());
			usageStatisticDao.saveUsageStatistic(statistic);
		} else {
			// 否则更新记录
			Date now = new Date();
			int diff = differentDaysByMillisecond(now, statistic.getLastUsageTime());
			// 只有不是同一天的记录才需要更新
			if (diff > 0) {
				if (statistic.getCurCount() > statistic.getHistoryMaxCount()) {
					statistic.setHistoryMaxCount(statistic.getCurCount());
				}
				statistic.setCurCount(1);
				statistic.setLastUsageTime(now);
				statistic.setUpdateTime(now);
				usageStatisticDao.updateUsageStatistic(statistic);
			}
		}
	}

	@Override
	public int getCurCount(int userId) {
		UsageStatistic statistic = usageStatisticDao.getUsageStatisticByUserId(userId);
		if (statistic == null) {
			return 0;
		}
		// 否则更新记录
		Date now = new Date();
		int diff = differentDaysByMillisecond(now, statistic.getLastUsageTime());
		// 只有不是同一天的记录才需要更新
		if (diff > 0) {
			if (statistic.getCurCount() > statistic.getHistoryMaxCount()) {
				statistic.setHistoryMaxCount(statistic.getCurCount());
			}
			statistic.setCurCount(0);
			statistic.setLastUsageTime(now);
			statistic.setUpdateTime(now);
			usageStatisticDao.updateUsageStatistic(statistic);
		}
		return statistic.getCurCount();
	}

	@Override
	public int getHistoryMaxCount(int userId) {
		UsageStatistic statistic = usageStatisticDao.getUsageStatisticByUserId(userId);
		if (statistic == null) {
			return 0;
		} else {
			return statistic.getHistoryMaxCount() > statistic.getCurCount() ? statistic.getHistoryMaxCount()
					: statistic.getCurCount();
		}
	}

	private static int differentDaysByMillisecond(Date date1, Date date2) {
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
		return days;
	}

}
