package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Story;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface StoryService {
    boolean saveStory(Story story);

    boolean deleteStoryById(int id);

    Story getStoryById(int id);

    Story getStoryByIdHard(int id);

    List<Story> getAllStories();

    Story updateStory(Story story);

    List<Story> getStoryListByPage(int offset, int limit);

    boolean getStoryListByTitle(String title, int offset, int limit);
}
