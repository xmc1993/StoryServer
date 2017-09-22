package cn.edu.nju.software.service;

import java.util.List;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryRoleAudio;

public interface StoryRoleAudioService {
	int saveRoleAudio(StoryRoleAudio storyRoleAudio);
	
	int updataRoleAudioById(StoryRoleAudio storyRoleAudio);
	
	int deleteById(Integer id);
	
	int deleteByStoryId(Integer storyId);
	
	StoryRoleAudio selectById(Integer id);
	
	List<StoryRoleAudio> getStoryRoleAudioByUserId(Integer userId);
	
	List<StoryRoleAudio> getStoryRoleAudioByUserIdAndRoleId(Integer userId,Integer RoleId);
	
	ResponseData<List<StoryRoleAudio>> getStoryRoleAuioByStoryId(Integer storyId,Integer page,Integer pageSize);
	
	List<StoryRoleAudio> getStoryRoleAuioByUserAndStory(Integer userId,Integer storyId);
}
