package cn.edu.nju.software.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.nju.software.dao.ReadingPlanStoryGroupMapper;
import cn.edu.nju.software.entity.ReadingPlanStoryGroup;
import cn.edu.nju.software.entity.ReadingPlanStoryGroupExample;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.ReadPlanStoryGroupService;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月21日 
*/
@Service
public class ReadPlanStoryGroupServiceImpl implements ReadPlanStoryGroupService {
	@Autowired
	ReadingPlanStoryGroupMapper readingPlanStoryGroupMapper;

	@Override
	public int saveReadPlanStory(ReadingPlanStoryGroup readingPlanStoryGroup) {
		return readingPlanStoryGroupMapper.insert(readingPlanStoryGroup);
	}

	@Override
	public int deleteReadPlanStoryByReadPlanId(Integer readPlanId) {
		ReadingPlanStoryGroupExample readingPlanStoryGroupExample=new ReadingPlanStoryGroupExample();
        //通过criteria构造查询条件
		ReadingPlanStoryGroupExample.Criteria criteria = readingPlanStoryGroupExample.createCriteria();
        criteria.andReadingplanidEqualTo(readPlanId);
        return readingPlanStoryGroupMapper.deleteByExample(readingPlanStoryGroupExample);
	}

	@Override
	public ReadingPlanStoryGroup getReadPlanStoryGroupById(Integer id) {
		return readingPlanStoryGroupMapper.selectByPrimaryKey(id);
	}

	@Override
	public ResponseData<List<ReadingPlanStoryGroup>> getReadPlanStoryGroupByPlanId(Integer readPlanId, Integer page,
			Integer pageSize) {
		ResponseData<List<ReadingPlanStoryGroup>> responseData=new ResponseData<>();
		PageHelper.startPage(page, pageSize);
		ReadingPlanStoryGroupExample readingPlanStoryGroupExample=new ReadingPlanStoryGroupExample();
        //通过criteria构造查询条件
		ReadingPlanStoryGroupExample.Criteria criteria = readingPlanStoryGroupExample.createCriteria();
        criteria.andReadingplanidEqualTo(readPlanId);
        List<ReadingPlanStoryGroup> list=readingPlanStoryGroupMapper.selectByExample(readingPlanStoryGroupExample);
        PageInfo<ReadingPlanStoryGroup> pageInfo=new PageInfo<>(list);
        int count=(int)pageInfo.getTotal();
        responseData.setCount(count);
        responseData.jsonFill(1,null, list);
		return responseData;
	}

	@Override
	public List<ReadingPlanStoryGroup> getReadPlanStoryGroupByPlanId(Integer readPlanId) {
		ReadingPlanStoryGroupExample readingPlanStoryGroupExample=new ReadingPlanStoryGroupExample();
        //通过criteria构造查询条件
		ReadingPlanStoryGroupExample.Criteria criteria = readingPlanStoryGroupExample.createCriteria();
        criteria.andReadingplanidEqualTo(readPlanId);
        List<ReadingPlanStoryGroup> list=readingPlanStoryGroupMapper.selectByExample(readingPlanStoryGroupExample);
		return list;
	}

	@Override
	public ReadingPlanStoryGroup getReadPlanStoryByIdAndMyOrder(Integer readPlanId, Integer myOrder) {
		ReadingPlanStoryGroupExample readingPlanStoryGroupExample=new ReadingPlanStoryGroupExample();
		//通过criteria构造查询条件
		ReadingPlanStoryGroupExample.Criteria criteria = readingPlanStoryGroupExample.createCriteria();
		criteria.andReadingplanidEqualTo(readPlanId);
		criteria.andMyorderEqualTo(myOrder);
		List<ReadingPlanStoryGroup> list=readingPlanStoryGroupMapper.selectByExample(readingPlanStoryGroupExample);
		if (list==null||list.isEmpty()){
			return  null;
		}
		return list.get(0);
	}

	@Override
	public ReadingPlanStoryGroup getReadPlanStoryByIdAndStory(Integer readPlanId, Integer storyId) {
		ReadingPlanStoryGroupExample readingPlanStoryGroupExample=new ReadingPlanStoryGroupExample();
		//通过criteria构造查询条件
		ReadingPlanStoryGroupExample.Criteria criteria = readingPlanStoryGroupExample.createCriteria();
		criteria.andReadingplanidEqualTo(readPlanId);
		criteria.andStoryidEqualTo(storyId);
		List<ReadingPlanStoryGroup> list=readingPlanStoryGroupMapper.selectByExample(readingPlanStoryGroupExample);
		if (list==null||list.isEmpty()){
			return  null;
		}
		return list.get(0);
	}

	@Override
	public List<Integer> getStoryIdListInReadPlanByPlanId(Integer planId) {
		return readingPlanStoryGroupMapper.getStoryIdListInReadPlanByPlanId(planId);
	}

}
