package cn.edu.nju.software.dao.user;

import cn.edu.nju.software.entity.LoginStatusStatistics;
import cn.edu.nju.software.entity.LoginStatusStatisticsExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LoginStatusStatisticsMapper {
    int countByExample(LoginStatusStatisticsExample example);

    int deleteByExample(LoginStatusStatisticsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LoginStatusStatistics record);

    int insertSelective(LoginStatusStatistics record);

    List<LoginStatusStatistics> selectByExample(LoginStatusStatisticsExample example);

    LoginStatusStatistics selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LoginStatusStatistics record, @Param("example") LoginStatusStatisticsExample example);

    int updateByExample(@Param("record") LoginStatusStatistics record, @Param("example") LoginStatusStatisticsExample example);

    int updateByPrimaryKeySelective(LoginStatusStatistics record);

    int updateByPrimaryKey(LoginStatusStatistics record);

    LoginStatusStatistics getLoginStatusStatisticsByUserId(Integer userId);
}