package cn.edu.nju.software.service;

import java.util.List;

import cn.edu.nju.software.entity.ReadingPlan;
import cn.edu.nju.software.entity.ResponseData;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月21日 
*/
public interface ReadPlanService {
	ResponseData<List<ReadingPlan>> getAllReadPlan(Integer page,Integer pageSize);
	int saveReadPlan(ReadingPlan readPlan);
	int deleteReadPlan(Integer id);
	int updateReadPlan(ReadingPlan readPlan);
	ReadingPlan selectReadPlanById(Integer id);
	List<ReadingPlan> getReadingPlanByTime(String days,String timePoint);
}
