package cn.edu.nju.software.service;

import java.util.List;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryScript;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月20日 
*/
public interface StoryScriptService {
	ResponseData<List<StoryScript>> selectAllStoryScript(int page,int pageSize);
	int saveStoryScript(StoryScript storyScript);
	int deleteById(Integer id);
	int updataById(StoryScript storyScript);
	StoryScript getStoryScriptByStoryId(Integer storyId);
	StoryScript getStoryScriptById(Integer id);
}
