package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.StorySet;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
public interface StorySetService {

    StorySet saveStorySet(StorySet storySet);

    boolean updateStorySet(StorySet storySet);

    boolean deleteStorySet(int id);

    StorySet getStorySetById(int id);

    List<StorySet> getAllStorySetByPage(int page, int pageSize);

    StorySet getStorySetByStoryId(int storyId);

    int getAllStorySetCount();

}
