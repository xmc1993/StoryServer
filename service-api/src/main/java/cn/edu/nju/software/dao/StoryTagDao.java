package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.StoryTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface StoryTagDao {

    int saveStoryTag(StoryTag storyTag);

    boolean updateStoryTag(StoryTag storyTag);

    boolean deleteStoryTagById(int tagId);

    StoryTag getStoryTagById(int tagId);

    StoryTag getStoryTagByIdHard(int tagId);

    /**
     * 根据父标签id返回所有的子标签
     * @param parentId
     * @return
     */
    List<StoryTag> getStoryTagListByParentId(int parentId);

    List<StoryTag> getStoryTagListByPage(int offset, int limit);

    List<StoryTag> getAllStoryTags();

    List<StoryTag> getTagListByIdList(@Param("idList") List<Integer> idList);

    boolean deleteHard(int id);

    List<StoryTag> getAllSecondLevelTags();

    Integer getStoryTagCount();

}
