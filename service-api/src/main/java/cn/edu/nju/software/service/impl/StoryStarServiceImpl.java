package cn.edu.nju.software.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.nju.software.dao.StoryStarMapper;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryStar;
import cn.edu.nju.software.entity.StoryStarExample;
import cn.edu.nju.software.service.StoryStarService;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年10月24日 
*/
@Service
public class StoryStarServiceImpl implements StoryStarService {

	
	@Autowired
	StoryStarMapper storyStarMapper;
	
	@Override
	public Integer insertStoryStar(StoryStar storyStar) {
		return storyStarMapper.insert(storyStar);
	}

	@Override
	public Integer deleteStoryStar(Integer id) {
		return storyStarMapper.deleteByPrimaryKey(id);
	}

	@Override
	public ResponseData<List<StoryStar>> getAllStoryStar(Integer page,Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		StoryStarExample storyStarExample=new StoryStarExample();
		StoryStarExample.Criteria criteria = storyStarExample.createCriteria();
        criteria.andValidEqualTo(1);
        List<StoryStar> list = storyStarMapper.selectByExample(storyStarExample);
        PageInfo<StoryStar> pageInfo=new PageInfo<>(list);
        int count=(int)pageInfo.getTotal();
        ResponseData<List<StoryStar>> responseData=new ResponseData<>();
        responseData.setCount(count);
        responseData.jsonFill(1,null, list);
		return responseData;
	}

	@Override
	public List<StoryStar> getStoryStarByUserAndStory(Integer storyId,Integer userId) {
		StoryStarExample storyStarExample=new StoryStarExample();
		StoryStarExample.Criteria criteria = storyStarExample.createCriteria();
		criteria.andStoryidEqualTo(storyId);
		criteria.andUseridEqualTo(userId);
		List<StoryStar> list=storyStarMapper.selectByExample(storyStarExample);
		return list;
		
	}

	@Override
	public StoryStar getStoryStarById(Integer id) {
		return storyStarMapper.selectByPrimaryKey(id);
	}
}
