package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.StoryTag;
import cn.edu.nju.software.entity.TagRelation;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface TagRelationService {

    boolean saveTagRelation(TagRelation tagRelation);

    boolean deleteTagRelationById(int id);

    boolean deleteTagRelationByStoryIdAndTagId(int storyId, int tagId);

    List<Integer> getStoryIdsByTagId(int tagId);

    List<Integer> getTagIdsByStoryId(int storyId);

    List<Integer> getStoryIdListByTagIdList(List<Integer> tagIds);

    /**
     * 通过一级标签查询故事列表
     * @param tagId
     * @return
     */
    List<Integer> getStoryIdListByOneLevelTagId(int tagId);


    /**
     * 通过二级标签查询故事列表
     * @param tagId
     * @return
     */
    List<Integer> getStoryIdListByTwoLevelTagId(int tagId);

}
