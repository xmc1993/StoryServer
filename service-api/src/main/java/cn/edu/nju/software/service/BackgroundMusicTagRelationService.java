package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.BackgroundMusicTagRelation;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface BackgroundMusicTagRelationService {

    boolean saveTagRelation(BackgroundMusicTagRelation tagRelation);

    boolean deleteTagRelationById(int id);

    boolean deleteTagRelationByBackgroundMusicIdAndTagId(int backgroundMusicId, int tagId);

    List<Integer> getBackgroundMusicIdListByTagId(int tagId);

    List<Integer> getTagIdListByBackgroundMusicId(int backgroundMusicId);

    List<Integer> getBackgroundMusicIdListByTagIdList(List<Integer> tagIds);

}
