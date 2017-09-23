package cn.edu.nju.software.service;

import java.util.List;

import cn.edu.nju.software.entity.BabyRead;
import cn.edu.nju.software.entity.BabyReadInfo;
import cn.edu.nju.software.entity.ResponseData;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月23日 
*/

public interface BabyReadService {
	ResponseData<List<BabyReadInfo>> readInfoList(Integer page,Integer pageSize);
	int saveBabyReadInfo(BabyReadInfo readInfo );
	int deleteBabyReadInfo(Integer id);
	int updateBabyReadInfo(BabyReadInfo readInfo);
	BabyReadInfo selectReadInfoById(Integer id);
	List<BabyReadInfo> selectReadInfoByStoryId(Integer storyId);
	int saveBabyRead(BabyRead babyRead);
}
