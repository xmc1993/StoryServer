package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.SoundEffectTagRelation;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface SoundEffectTagRelationService {

    boolean saveTagRelation(SoundEffectTagRelation tagRelation);

    boolean deleteTagRelationById(int id);

    boolean deleteTagRelationBySoundEffectIdAndTagId(int soundEffectId, int tagId);

    List<Integer> getSoundEffectIdListByTagId(int tagId);

    List<Integer> getTagIdListBySoundEffectId(int soundEffectId);

    List<Integer> getSoundEffectIdListByTagIdList(List<Integer> tagIds);

}
