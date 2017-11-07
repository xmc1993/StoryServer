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
    
    boolean deleteTagRelationsByStoryId(int storyId);

    List<Integer> getStoryIdListByTagId(int tagId);
    
    //多标签查询故事:下面的那个长得好像的，是取“或”，我是取“与”的。(size是代表想要获取的故事标签数要大于几)
    List<Integer> getStoryIdListByTagIds(List<Integer> tagIds,Integer size);

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
