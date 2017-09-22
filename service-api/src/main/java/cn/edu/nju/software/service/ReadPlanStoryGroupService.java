package cn.edu.nju.software.service;

import java.util.List;

import cn.edu.nju.software.entity.ReadingPlanStoryGroup;
import cn.edu.nju.software.entity.ResponseData;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月21日 
*/
public interface ReadPlanStoryGroupService {
	int saveReadPlanStory(ReadingPlanStoryGroup readingPlanStoryGroup);
	int deleteReadPlanStoryByReadPlanId(Integer readPlanId);
	ReadingPlanStoryGroup getReadPlanStoryGroupById(Integer id);
	ResponseData<List<ReadingPlanStoryGroup>> getReadPlanStoryGroupByPlanId(Integer readPlanId,Integer page,Integer pageSize);
	List<ReadingPlanStoryGroup> getReadPlanStoryGroupByPlanId(Integer readPlanId);
}
