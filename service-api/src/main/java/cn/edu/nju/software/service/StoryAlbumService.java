package cn.edu.nju.software.service;

import java.util.List;

public interface StoryAlbumService {
	boolean saveStoryAlbumRel(Integer storyId,List<Integer> albumIdList);

	boolean delStoryAlbumRel(int id);
}
