package cn.edu.nju.software.service.impl.user;

import cn.edu.nju.software.dao.user.LoginStatusStatisticsMapper;
import cn.edu.nju.software.entity.LoginStatusStatistics;
import cn.edu.nju.software.service.user.LoginStatusStatisticsService;
import cn.edu.nju.software.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

/**
 * @author zj
 */
@Service
public class LoginStatusStatisticsServiceImpl implements LoginStatusStatisticsService {
    @Autowired
    private LoginStatusStatisticsMapper loginStatusStatisticsMapper;

    @Override
    public void saveLoginStatusStatistics(Integer userId) {
        LoginStatusStatistics loginStatusStatistics=loginStatusStatisticsMapper.getLoginStatusStatisticsByUserId(userId);
        // 如果没有登录状态的记录，则新建一条来
        if (loginStatusStatistics==null){
            loginStatusStatistics=new LoginStatusStatistics();
            loginStatusStatistics.setUserId(userId);
            loginStatusStatistics.setContinuousLoginDays(1);
            loginStatusStatistics.setLastLoginTime(new Date());
            loginStatusStatistics.setCreateTime(new Date());
            loginStatusStatistics.setUpdateTime(new Date());
            loginStatusStatisticsMapper.insert(loginStatusStatistics);
        } else{// 否则更新记录
            Date now=new Date();
            Date lastLoginTime=loginStatusStatistics.getLastLoginTime();
            int days = DateUtil.differentDays(lastLoginTime, now);
            int continuousLoginDays=loginStatusStatistics.getContinuousLoginDays();
            // 只有相差一天以上的记录才需要更新
            if (days == 1) {
                loginStatusStatistics.setContinuousLoginDays(++continuousLoginDays);
            } else if (days >=2) {// 相差2天以上把连续登录天数改为1
                loginStatusStatistics.setContinuousLoginDays(1);
            }
            loginStatusStatistics.setLastLoginTime(now);
            loginStatusStatistics.setUpdateTime(now);
            loginStatusStatisticsMapper.updateByPrimaryKeySelective(loginStatusStatistics);
        }
    }

    @Override
    public Integer getContinuousLoginDays(Integer userId) {
        return loginStatusStatisticsMapper.getLoginStatusStatisticsByUserId(userId).getContinuousLoginDays();
    }
}
