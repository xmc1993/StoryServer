package cn.edu.nju.software.service;

import java.util.List;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryStar;

/**
 * @author zs1996 E-mail:806949096@qq.com
 * @version 创建时间：2017年10月24日
 */
public interface StoryStarService {
	public Integer insertStoryStar(StoryStar storyStar);

	public Integer deleteStoryStar(Integer id);

	public ResponseData<List<StoryStar>> getAllStoryStar(Integer page,Integer pageSize);

	public List<StoryStar> getStoryStarByUserAndStory(Integer storyId,Integer userId);

	public StoryStar getStoryStarById(Integer id);
}
