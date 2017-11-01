package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.StorySet;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface StorySetDao {

    boolean saveStorySet(StorySet storySet);

    boolean updateStorySet(StorySet storySet);

    boolean deleteStorySet(int id);

    StorySet getStorySetById(int id);

    List<StorySet> getAllStorySetByPage(int offset, int limit);

    Integer getStorySetIdByStoryId(int storyId);

    int getAllStorySetCount();
    
    List<StorySet> getStorySetByFuzzyQuery(String query);
}
