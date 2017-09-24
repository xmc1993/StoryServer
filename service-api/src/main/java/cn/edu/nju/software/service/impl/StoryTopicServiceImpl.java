package cn.edu.nju.software.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.nju.software.dao.StoryTopicMapper;
import cn.edu.nju.software.dao.StoryTopicRelationMapper;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryTopic;
import cn.edu.nju.software.entity.StoryTopicExample;
import cn.edu.nju.software.entity.StoryTopicRelation;
import cn.edu.nju.software.entity.StoryTopicRelationExample;
import cn.edu.nju.software.service.StoryTopicServcie;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月24日 
*/
@Service
public class StoryTopicServiceImpl implements StoryTopicServcie {

	@Autowired
	StoryTopicMapper storyTopicMapper;
	@Autowired
	StoryTopicRelationMapper storyTopicRelationMapper;
	@Override
	public int deleteStoryTopicById(Integer id) {
		return storyTopicMapper.deleteByPrimaryKey(id);
	}

	@Override
	public ResponseData<List<StoryTopic>> getAllStoryTopic(Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		StoryTopicExample storyTopicExample=new StoryTopicExample();
        //通过criteria构造查询条件
		StoryTopicExample.Criteria criteria = storyTopicExample.createCriteria();
        criteria.andValidEqualTo(1);
        //数据库中有大文本文件 查询使用这个方法，selectByExampleWithBLOBs
        List<StoryTopic> list = storyTopicMapper.selectByExampleWithBLOBs(storyTopicExample);
        PageInfo<StoryTopic> pageInfo=new PageInfo<>(list);
        int count=(int)pageInfo.getTotal();
        ResponseData<List<StoryTopic>> responseData=new ResponseData<>();
        responseData.setCount(count);
        responseData.jsonFill(1,null, list);
		return responseData;
	}

	@Override
	public List<StoryTopic> selectAllShowStoryTopic() {
		StoryTopicExample storyTopicExample=new StoryTopicExample();
        //通过criteria构造查询条件
		StoryTopicExample.Criteria criteria = storyTopicExample.createCriteria();
        criteria.andIsshowEqualTo(1);
        //数据库中有大文本文件 查询使用这个方法，selectByExampleWithBLOBs
        List<StoryTopic> list = storyTopicMapper.selectByExampleWithBLOBs(storyTopicExample);
		return list;
	}

	@Override
	public int saveStoryTopic(StoryTopic storyTopic) {
		return storyTopicMapper.insert(storyTopic);
	}

	@Override
	public List<StoryTopicRelation> getStoryListByTopicId(Integer storyTopicId) {
		StoryTopicRelationExample storyTopicRelationExample=new StoryTopicRelationExample();
		StoryTopicRelationExample.Criteria criteria = storyTopicRelationExample.createCriteria();
		criteria.andStorytopicidEqualTo(storyTopicId);
		List<StoryTopicRelation> list=storyTopicRelationMapper.selectByExample(storyTopicRelationExample);
		return list;
	}

	@Override
	public StoryTopic getStoryTopicById(Integer id) {
		return storyTopicMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateStoryTopic(StoryTopic storyTopic) {
		return storyTopicMapper.updateByPrimaryKeyWithBLOBs(storyTopic);
	}

	@Override
	public int deleteStoryTopicStorys(Integer storyTopicId) {
		StoryTopicRelationExample storyTopicRelationExample=new StoryTopicRelationExample();
		StoryTopicRelationExample.Criteria criteria = storyTopicRelationExample.createCriteria();
		criteria.andStorytopicidEqualTo(storyTopicId);
		return storyTopicRelationMapper.deleteByExample(storyTopicRelationExample);
	}

	@Override
	public int saveStoryTopicRelation(StoryTopicRelation storyTopicRelation) {
		return storyTopicRelationMapper.insert(storyTopicRelation);
	}

	@Override
	public int deleteStoryForStoryTopic(Integer storyTopicId, Integer storyId) {
		StoryTopicRelationExample storyTopicRelationExample=new StoryTopicRelationExample();
		StoryTopicRelationExample.Criteria criteria = storyTopicRelationExample.createCriteria();
		criteria.andStorytopicidEqualTo(storyTopicId);
		criteria.andStoryidEqualTo(storyId);
		return storyTopicRelationMapper.deleteByExample(storyTopicRelationExample);
	}


}
