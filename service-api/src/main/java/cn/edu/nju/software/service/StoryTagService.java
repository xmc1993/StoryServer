package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.StoryTag;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface StoryTagService {

    int saveStoryTag(StoryTag storyTag);

    StoryTag updateStoryTag(StoryTag storyTag);

    boolean deleteStoryTag(int tagId);

    StoryTag getStoryTagById(int tagId);

    /**
     * 根据父标签id返回所有的子标签
     *
     * @param parentId
     * @return
     */
    List<StoryTag> getStoryTagListByParentId(int parentId);

    List<StoryTag> getStoryTagListByIdList(List<Integer> idList);

    List<StoryTag> getAllStoryTags();

    List<StoryTag> getStoryTagsByPage(int offset, int limit);

    StoryTag getStoryTagByIdHard(int tagId);

}
