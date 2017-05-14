package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.StoryTag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface StoryTagDao {

    boolean saveStoryTag(StoryTag storyTag);

    boolean updateStoryTag(StoryTag storyTag);

    boolean deleteStoryTagById(int tagId);

    StoryTag getStoryTagById(int tagId);

    /**
     * 根据父标签id返回所有的子标签
     * @param parentId
     * @return
     */
    List<StoryTag> getStoryTagListByParentId(int parentId);

    List<StoryTag> getAllStoryTags();

    boolean deleteHard(int id);

}
