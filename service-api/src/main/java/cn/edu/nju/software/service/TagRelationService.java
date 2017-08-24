package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.TagRelation;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface TagRelationService {

    boolean saveTagRelation(TagRelation tagRelation);

    boolean deleteTagRelationById(int id);

    boolean deleteTagRelationByStoryIdAndTagId(int storyId, int tagId);

    List<Integer> getStoryIdListByTagId(int tagId);

    List<Integer> getTagIdListByStoryId(int storyId);

    List<Integer> getStoryIdListByTagIdList(List<Integer> tagIds);

    /**
     * 通过一级标签查询故事列表
     * @param tagId
     * @return
     */
    List<Integer> getStoryIdListByFirstLevelTagId(int tagId);


    /**
     * 通过二级标签查询故事列表
     * @param tagId
     * @return
     */
    List<Integer> getStoryIdListBySecondLevelTagId(int tagId);


}
