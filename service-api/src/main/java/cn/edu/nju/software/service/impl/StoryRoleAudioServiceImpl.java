package cn.edu.nju.software.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.nju.software.dao.StoryRoleAudioMapper;
import cn.edu.nju.software.entity.StoryRoleAudio;
import cn.edu.nju.software.entity.StoryRoleAudioExample;
import cn.edu.nju.software.service.StoryRoleAudioService;

@Service
public class StoryRoleAudioServiceImpl implements StoryRoleAudioService{

	@Autowired
	StoryRoleAudioMapper storyRoleAudioMapper;
	
	@Override
	public int saveRoleAudio(StoryRoleAudio storyRoleAudio) {
		return storyRoleAudioMapper.insert(storyRoleAudio);
	}

	@Override
	public int updataRoleAudioById(StoryRoleAudio storyRoleAudio) {
		return storyRoleAudioMapper.updateByPrimaryKey(storyRoleAudio);
	}

	@Override
	public int deleteById(Integer id) {
		return storyRoleAudioMapper.deleteByPrimaryKey(id);
	}

	@Override
	public StoryRoleAudio selectById(Integer id) {
		return storyRoleAudioMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<StoryRoleAudio> getStoryRoleAudioByUserId(Integer userId) {
		StoryRoleAudioExample storyRoleAudioExample=new StoryRoleAudioExample();
        //通过criteria构造查询条件
		StoryRoleAudioExample.Criteria criteria = storyRoleAudioExample.createCriteria();
        criteria.andUseridEqualTo(userId);
        //可能返回多条记录
        List<StoryRoleAudio> list = storyRoleAudioMapper.selectByExample(storyRoleAudioExample);
		return list;
	}

	@Override
	public List<StoryRoleAudio> getStoryRoleAudioByUserIdAndRoleId(Integer userId, Integer RoleId) {
		List<StoryRoleAudio> list=getStoryRoleAudioByUserId(userId);
		List<StoryRoleAudio> list2=new ArrayList<>();
		for (StoryRoleAudio storyRoleAudio : list) {
			if(storyRoleAudio.getRoleid().equals(RoleId)){
				list2.add(storyRoleAudio);
			}
		}
		return list2;
	}

}
