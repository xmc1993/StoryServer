package cn.edu.nju.software.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.nju.software.dao.StoryScriptMapper;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryScript;
import cn.edu.nju.software.entity.StoryScriptExample;
import cn.edu.nju.software.service.StoryScriptService;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月20日 
*/

@Service
public class StoryScriptServiceImpl implements StoryScriptService {
	@Autowired
	StoryScriptMapper storyScriptMapper;

	@Override
	public ResponseData<List<StoryScript>> selectAllStoryScript(int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		StoryScriptExample storyScriptExample=new StoryScriptExample();
        //通过criteria构造查询条件
		StoryScriptExample.Criteria criteria = storyScriptExample.createCriteria();
        criteria.andValidEqualTo(1);
        //数据库中有大文本文件 查询使用这个方法，selectByExampleWithBLOBs
        List<StoryScript> list = storyScriptMapper.selectByExampleWithBLOBs(storyScriptExample);
        PageInfo<StoryScript> pageInfo=new PageInfo<>(list);
        int count=(int)pageInfo.getTotal();
        ResponseData<List<StoryScript>> responseData=new ResponseData<>();
        responseData.setCount(count);
        responseData.jsonFill(1,null, list);
		return responseData;
	}

	@Override
	public int saveStoryScript(StoryScript storyScript) {
		return storyScriptMapper.insert(storyScript);
	}

	@Override
	public int deleteById(Integer id) {
		return storyScriptMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updataById(StoryScript storyScript) {
		return storyScriptMapper.updateByPrimaryKey(storyScript);
	}

	@Override
	public StoryScript getStoryScriptByStoryId(Integer storyId) {
		StoryScriptExample storyScriptExample=new StoryScriptExample();
		StoryScriptExample.Criteria criteria = storyScriptExample.createCriteria();
        criteria.andStoryidEqualTo(storyId);
        List<StoryScript> list = storyScriptMapper.selectByExampleWithBLOBs(storyScriptExample);
		return list.get(0);
	}

	@Override
	public StoryScript getStoryScriptById(Integer id) {
		return storyScriptMapper.selectByPrimaryKey(id);
	}



}
