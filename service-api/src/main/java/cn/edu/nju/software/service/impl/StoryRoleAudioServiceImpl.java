package cn.edu.nju.software.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.nju.software.dao.StoryRoleAudioMapper;
import cn.edu.nju.software.entity.ResponseData;
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

	//这里可使用多条件查询，不需要增强for遍历，但我又暂时不想改，留着 。write by zs
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

	@Override
	public ResponseData<List<StoryRoleAudio>> getStoryRoleAuioByStoryId(Integer storyId, Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		StoryRoleAudioExample storyRoleAudioExample=new StoryRoleAudioExample();
		StoryRoleAudioExample.Criteria criteria = storyRoleAudioExample.createCriteria();
        criteria.andStoryidEqualTo(storyId);
        List<StoryRoleAudio> list = storyRoleAudioMapper.selectByExample(storyRoleAudioExample);
        ResponseData<List<StoryRoleAudio>> responseData=new ResponseData<>();
        if(list==null){
        	responseData.jsonFill(2, "没有此音频", null);
        	return responseData;
        }
        PageInfo<StoryRoleAudio> pageInfo=new PageInfo<>(list);
        int count=(int)pageInfo.getTotal();
        responseData.setCount(count);
        responseData.jsonFill(1,null, list);
		return responseData;
	}

	@Override
	public List<StoryRoleAudio> getStoryRoleAuioByUserAndStory(Integer userId, Integer storyId) {
		StoryRoleAudioExample storyRoleAudioExample=new StoryRoleAudioExample();
		StoryRoleAudioExample.Criteria criteria = storyRoleAudioExample.createCriteria();
        criteria.andUseridEqualTo(userId);
        criteria.andStoryidEqualTo(storyId);
        List<StoryRoleAudio> list = storyRoleAudioMapper.selectByExample(storyRoleAudioExample);
		return list;
	}

	@Override
	public int deleteByStoryId(Integer storyId) {
		StoryRoleAudioExample storyRoleAudioExample=new StoryRoleAudioExample();
		StoryRoleAudioExample.Criteria criteria = storyRoleAudioExample.createCriteria();
        criteria.andStoryidEqualTo(storyId);
        return storyRoleAudioMapper.deleteByExample(storyRoleAudioExample);
	}

}
