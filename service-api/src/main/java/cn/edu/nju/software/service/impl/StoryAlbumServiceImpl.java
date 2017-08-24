package cn.edu.nju.software.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.nju.software.dao.StoryAlbumDao;
import cn.edu.nju.software.service.StoryAlbumService;

/**
 * 
 * @author liuyu
 *
 */
@Service
public class StoryAlbumServiceImpl implements StoryAlbumService {
	@Autowired
	private StoryAlbumDao storyAlbumDao;
	
	@Override
	public boolean saveStoryAlbumRel(Integer storyId, List<Integer> albumIdList) {
		HashMap<String,Object> map = new HashMap<>();
		map.put("storyId", storyId);
		map.put("albumIdList", albumIdList);
		return storyAlbumDao.saveStoryAlbumRel(map);
	}

	@Override
	public boolean delStoryAlbumRel(int id) {
		return storyAlbumDao.delStoryAlbumRel(id);
	}

}
