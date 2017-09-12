package cn.edu.nju.software.dao;

import org.springframework.stereotype.Repository;

import cn.edu.nju.software.entity.UsageStatistic;

/**
* @author zs
* @version 创建时间：2017年9月12日 上午11:55:12
*/
@Repository
public interface UsageStatisticDao {
    boolean saveUsageStatistic(UsageStatistic usageStatistic);

    boolean updateUsageStatistic(UsageStatistic usageStatistic);

    UsageStatistic getUsageStatisticByUserId(int userId);
}
