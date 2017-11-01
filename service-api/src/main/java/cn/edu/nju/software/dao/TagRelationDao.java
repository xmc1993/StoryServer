package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.TagRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface TagRelationDao {

    boolean saveTagRelation(TagRelation tagRelation);

    boolean deleteTagRelationById(int id);

    boolean deleteTagRelationByStoryIdAndTagId(int storyId, int tagId);

    List<Integer> getStoryIdListByTagId(int tagId);

    List<Integer> getTagIdListByStoryId(int storyId);

    //这个是取"或"的
    List<Integer> getStoryIdListByTagIdList(@Param("idList") List<Integer> idList);
    
    //这个是取"与"的
    List<Integer> getStoryIdListByTagIds(@Param("idList") List<Integer> idList,@Param("size") Integer size);

    boolean deleteTagRelationsByStoryId(Integer storyId);
}
