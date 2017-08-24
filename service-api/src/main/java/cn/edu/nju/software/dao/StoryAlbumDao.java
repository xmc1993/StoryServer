package cn.edu.nju.software.dao;

import java.util.Map;


public interface StoryAlbumDao {
	boolean saveStoryAlbumRel(Map<String,Object> map);

	boolean delStoryAlbumRel(int id);
}
