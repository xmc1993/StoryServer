package cn.edu.nju.software.service;

import java.util.List;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryAmbitus;

/**
 * @author zs1996 E-mail:806949096@qq.com
 * @version 创建时间：2017年9月19日
 */

public interface StoryAmbitusService {
	ResponseData<List<StoryAmbitus>> selectAllStoryAmbitus(int page,int pageSize);
	int saveStoryAmbitus(StoryAmbitus storyAmbitus);
	int deleteById(Integer id);
	int updataById(StoryAmbitus storyAmbitus);
	ResponseData <List<StoryAmbitus>> getStoryAmbitusByStoryId(Integer storyId,Integer page, Integer pageSize);
	StoryAmbitus getStoryAmbitusById(Integer id);
}
