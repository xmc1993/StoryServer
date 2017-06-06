package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.BackgroundMusicTagRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface BackgroundMusicTagRelationDao {

    boolean saveTagRelation(BackgroundMusicTagRelation tagRelation);

    boolean deleteTagRelationById(int id);

    boolean deleteTagRelationByBackgroundMusicIdAndTagId(int backgroundMusicId, int tagId);

    List<Integer> getBackgroundMusicIdListByTagId(int tagId);

    List<Integer> getTagIdListByBackgroundMusicId(int backgroundMusicId);

    List<Integer> getBackgroundMusicIdListByTagIdList(@Param("idList") List<Integer> idList);

}
