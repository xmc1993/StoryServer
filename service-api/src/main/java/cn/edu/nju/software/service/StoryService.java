package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.StoryTag;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface StoryService {
    boolean saveStory(Story story);

    boolean deleteStoryById(int id);

    Story getStoryById(int id);

    List<Story> getAllStories();

    boolean updateStory(Story story);
}
