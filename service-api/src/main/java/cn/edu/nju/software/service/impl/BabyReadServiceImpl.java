package cn.edu.nju.software.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.nju.software.dao.BabyReadInfoMapper;
import cn.edu.nju.software.dao.BabyReadMapper;
import cn.edu.nju.software.entity.BabyRead;
import cn.edu.nju.software.entity.BabyReadInfo;
import cn.edu.nju.software.entity.BabyReadInfoExample;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.BabyReadService;

/**
 * @author zs1996 E-mail:806949096@qq.com
 * @version 创建时间：2017年9月23日
 */
@Service
public class BabyReadServiceImpl implements BabyReadService {

	@Autowired
	BabyReadInfoMapper babyReadInfoMapper;
	@Autowired
	BabyReadMapper babyReadMapper;

	@Override
	public int saveBabyReadInfo(BabyReadInfo readInfo) {
		return babyReadInfoMapper.insert(readInfo);
	}

	@Override
	public int deleteBabyReadInfo(Integer id) {
		return babyReadInfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateBabyReadInfo(BabyReadInfo readInfo) {
		return babyReadInfoMapper.updateByPrimaryKeyWithBLOBs(readInfo);
	}

	@Override
	public BabyReadInfo selectReadInfoById(Integer id) {
		return babyReadInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<BabyReadInfo> selectReadInfoByStoryId(Integer storyId) {
		BabyReadInfoExample babyReadInfoExample = new BabyReadInfoExample();
		// 通过criteria构造查询条件
		BabyReadInfoExample.Criteria criteria = babyReadInfoExample.createCriteria();
		criteria.andStoryidEqualTo(storyId);
		// 数据库中有大文本文件 查询使用这个方法，selectByExampleWithBLOBs
		List<BabyReadInfo> list = babyReadInfoMapper.selectByExampleWithBLOBs(babyReadInfoExample);
		return list;
	}

	@Override
	public ResponseData<List<BabyReadInfo>> readInfoList(Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		BabyReadInfoExample babyReadInfoExample = new BabyReadInfoExample();
		// 通过criteria构造查询条件
		BabyReadInfoExample.Criteria criteria = babyReadInfoExample.createCriteria();
		criteria.andValidEqualTo(1);
		// 数据库中有大文本文件 查询使用这个方法，selectByExampleWithBLOBs
		List<BabyReadInfo> list = babyReadInfoMapper.selectByExampleWithBLOBs(babyReadInfoExample);
		PageInfo<BabyReadInfo> pageInfo = new PageInfo<>(list);
		int count = (int) pageInfo.getTotal();
		ResponseData<List<BabyReadInfo>> responseData = new ResponseData<>();
		responseData.setCount(count);
		responseData.jsonFill(1, null, list);
		return responseData;
	}

	@Override
	public BabyRead saveBabyRead(BabyRead babyRead) {
		boolean res=babyReadMapper.insert(babyRead);
		return babyRead;
	}

	@Override
	public BabyRead selectBabyReadById(Integer id) {
		return babyReadMapper.selectByPrimaryKey(id);
	}

}
