package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.SoundEffectTagRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface SoundEffectTagRelationDao {

    boolean saveTagRelation(SoundEffectTagRelation tagRelation);

    boolean deleteTagRelationById(int id);

    boolean deleteTagRelationBySoundEffectIdAndTagId(int soundEffectId, int tagId);

    List<Integer> getSoundEffectIdListByTagId(int tagId);

    List<Integer> getTagIdListBySoundEffectId(int soundEffectId);

    List<Integer> getSoundEffectIdListByTagIdList(@Param("idList") List<Integer> idList);

    boolean updateSoundEffectTagRelation(@Param("soundEffectId") int soundEffectId, @Param("tagId") Integer tagId, @Param("updateTime") Date updateTime);
}
