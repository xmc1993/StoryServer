package cn.edu.nju.software.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.edu.nju.software.entity.Baby;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.nju.software.dao.ReadingPlanMapper;
import cn.edu.nju.software.entity.ReadingPlan;
import cn.edu.nju.software.entity.ReadingPlanExample;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.ReadPlanService;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月21日 
*/
@Service
public class ReadPlanServiceImpl implements ReadPlanService {

	@Autowired
	ReadingPlanMapper readingPlanMapper;
	
	//返回自增主键
	@Override
	public int saveReadPlan(ReadingPlan readPlan) {
		return readingPlanMapper.insert(readPlan);
	}

	@Override
	public int deleteReadPlan(Integer id) {
		return readingPlanMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateReadPlan(ReadingPlan readPlan) {
		return readingPlanMapper.updateByPrimaryKeyWithBLOBs(readPlan);
	}

	@Override
	public ReadingPlan selectReadPlanById(Integer id) {
		return readingPlanMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ReadingPlan> getReadingPlanByTime(String ageGroup, String timePoint) {
		ReadingPlanExample readingPlanExample=new ReadingPlanExample();
		//通过criteria构造查询条件
		ReadingPlanExample.Criteria criteria = readingPlanExample.createCriteria();
		criteria.andAgegroupEqualTo(ageGroup);
		criteria.andTimepointEqualTo(timePoint);
		//数据库中有大文本文件 查询使用这个方法，selectByExampleWithBLOBs
		List<ReadingPlan> list = readingPlanMapper.selectByExampleWithBLOBs(readingPlanExample);
		return list;
	}

	@Override
	public ResponseData<List<ReadingPlan>> getAllReadPlan(Integer page,Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		ReadingPlanExample readingPlanExample=new ReadingPlanExample();
        //通过criteria构造查询条件
		ReadingPlanExample.Criteria criteria = readingPlanExample.createCriteria();
        criteria.andValidEqualTo(1);
        //数据库中有大文本文件 查询使用这个方法，selectByExampleWithBLOBs
        List<ReadingPlan> list = readingPlanMapper.selectByExampleWithBLOBs(readingPlanExample);
        PageInfo<ReadingPlan> pageInfo=new PageInfo<>(list);
        int count=(int)pageInfo.getTotal();
        ResponseData<List<ReadingPlan>> responseData=new ResponseData<>();
        responseData.setCount(count);
        responseData.jsonFill(1,null, list);
		return responseData;
	}






}
