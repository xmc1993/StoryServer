package cn.edu.nju.software.service.user;

/**
 * @author zj
 */

public interface LoginStatusStatisticsService {

    void saveLoginStatusStatistics(Integer userId);

    Integer getContinuousLoginDays(Integer userId);
}
