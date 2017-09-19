package cn.edu.nju.software.service;

import java.util.List;

import cn.edu.nju.software.entity.StoryRoleAudio;

public interface StoryRoleAudioService {
	int saveRoleAudio(StoryRoleAudio storyRoleAudio);
	
	int updataRoleAudioById(StoryRoleAudio storyRoleAudio);
	
	int deleteById(Integer id);
	
	StoryRoleAudio selectById(Integer id);
	
	List<StoryRoleAudio> getStoryRoleAudioByUserId(Integer userId);
	
	List<StoryRoleAudio> getStoryRoleAudioByUserIdAndRoleId(Integer userId,Integer RoleId);
}