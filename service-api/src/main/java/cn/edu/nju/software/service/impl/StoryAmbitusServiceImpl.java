package cn.edu.nju.software.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.nju.software.dao.StoryAmbitusMapper;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryAmbitus;
import cn.edu.nju.software.entity.StoryAmbitusExample;
import cn.edu.nju.software.service.StoryAmbitusService;

/**
 * @author zs1996 E-mail:806949096@qq.com
 * @version 创建时间：2017年9月19日
 */

@Service
public class StoryAmbitusServiceImpl implements StoryAmbitusService {
	
	@Autowired
	StoryAmbitusMapper storyAmbitusMapper;
	

	@Override
	public ResponseData <List<StoryAmbitus>> selectAllStoryAmbitus(int page, int pageSize) {
		//pageHelper是默认从1开始分页的，但是前端需要从0开始分页，所以这里加上1
		PageHelper.startPage(page+1, pageSize);
		StoryAmbitusExample storyAmbitusExample=new StoryAmbitusExample();
        //通过criteria构造查询条件
		StoryAmbitusExample.Criteria criteria = storyAmbitusExample.createCriteria();
        criteria.andValidEqualTo(1);
        //数据库中有大文本文件 查询使用这个方法，selectByExampleWithBLOBs
        List<StoryAmbitus> list = storyAmbitusMapper.selectByExampleWithBLOBs(storyAmbitusExample);     
        PageInfo<StoryAmbitus> pageInfo=new PageInfo<>(list);
        int count=(int)pageInfo.getTotal();
        ResponseData<List<StoryAmbitus>> responseData=new ResponseData<>();
        responseData.setCount(count);
        responseData.jsonFill(1,null, list);
		return responseData;
	}

	@Override
	public int saveStoryAmbitus(StoryAmbitus storyAmbitus) {
		return storyAmbitusMapper.insert(storyAmbitus);
	}

	@Override
	public int deleteById(Integer id) {
		return storyAmbitusMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updataById(StoryAmbitus storyAmbitus) {
		return storyAmbitusMapper.updateByPrimaryKeyWithBLOBs(storyAmbitus);
	}

	@Override
	public ResponseData <List<StoryAmbitus>> getStoryAmbitusByStoryId(Integer storyId,Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		StoryAmbitusExample storyAmbitusExample=new StoryAmbitusExample();
        //通过criteria构造查询条件
		StoryAmbitusExample.Criteria criteria = storyAmbitusExample.createCriteria();
        criteria.andStoryidEqualTo(storyId);
        //数据库中有大文本文件 查询使用这个方法，selectByExampleWithBLOBs
        List<StoryAmbitus> list = storyAmbitusMapper.selectByExampleWithBLOBs(storyAmbitusExample);     
        PageInfo<StoryAmbitus> pageInfo=new PageInfo<>(list);
        int count=(int)pageInfo.getTotal();
        ResponseData<List<StoryAmbitus>> responseData=new ResponseData<>();
        responseData.setCount(count);
        responseData.jsonFill(1,null, list);
		return responseData;
	}

	@Override
	public StoryAmbitus getStoryAmbitusById(Integer id) {	
		return storyAmbitusMapper.selectByPrimaryKey(id);
	}
	

}
