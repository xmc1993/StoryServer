package cn.edu.nju.software.service;

import java.util.List;


import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryTopic;
import cn.edu.nju.software.entity.StoryTopicRelation;

/**
 * @author zs1996 E-mail:806949096@qq.com
 * @version 创建时间：2017年9月24日
 */
public interface StoryTopicServcie { 
	int deleteStoryTopicById(Integer id);
	
	int deleteStoryTopicStorys(Integer storyTopicId);

	ResponseData<List<StoryTopic>> getAllStoryTopic(Integer page,Integer pageSize);
	
	List<StoryTopic> selectAllShowStoryTopic();
	
	int saveStoryTopic(StoryTopic storyTopic);
	
	List<StoryTopicRelation> getStoryListByTopicId(Integer storyTopicId);
	
	StoryTopic getStoryTopicById(Integer id);
	
	int updateStoryTopic(StoryTopic storyTopic);
	
	int saveStoryTopicRelation(StoryTopicRelation storyTopicRelation);
	
	int deleteStoryForStoryTopic(Integer storyTopicId,Integer storyId);
}
