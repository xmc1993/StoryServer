package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.RecordStatisticDao;
import cn.edu.nju.software.entity.RecordStatistic;
import cn.edu.nju.software.service.RecordStatisticService;
import cn.edu.nju.software.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		// 如果没有录制记录，则新建一条来录制记录
		if (statistic == null) {
			statistic = new RecordStatistic();
			statistic.setUpdateTime(new Date());
			statistic.setUserId(userId);
			statistic.setLastRecordTime(new Date());
			statistic.setCurCount(1);
			statistic.setCreateTime(new Date());
			recordStatisticDao.saveRecordStatistic(statistic);
		} else {// 否则更新记录
			Date now = new Date();
			int days = DateUtil.differentDays(statistic.getLastRecordTime(), now);
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
			} else if (days >=2) {
				statistic.setCurCount(1);
				statistic.setLastRecordTime(now);
				statistic.setUpdateTime(now);
				recordStatisticDao.updateRecordStatistic(statistic);
			} else if (days == 0) {//如果是同一天
				statistic.setUpdateTime(now);
				statistic.setLastRecordTime(now);
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
		int days = DateUtil.differentDays(statistic.getLastRecordTime(), now);
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

}
