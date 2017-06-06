package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.BackgroundMusicTagDao;
import cn.edu.nju.software.dao.BackgroundMusicTagRelationDao;
import cn.edu.nju.software.entity.BackgroundMusicTagRelation;
import cn.edu.nju.software.service.BackgroundMusicTagRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class BackgroundMusicTagRelationServiceImpl implements BackgroundMusicTagRelationService {

    @Autowired
    private BackgroundMusicTagRelationDao backgroundMusicTagRelationDao;
    @Autowired
    private BackgroundMusicTagDao backgroundMusicTagDao;

    @Override
    public boolean saveTagRelation(BackgroundMusicTagRelation tagRelation) {
        return backgroundMusicTagRelationDao.saveTagRelation(tagRelation);
    }

    @Override
    public boolean deleteTagRelationById(int id) {
        return backgroundMusicTagRelationDao.deleteTagRelationById(id);
    }

    @Override
    public boolean deleteTagRelationByBackgroundMusicIdAndTagId(int backgroundMusicId, int tagId) {
        return backgroundMusicTagRelationDao.deleteTagRelationByBackgroundMusicIdAndTagId(backgroundMusicId, tagId);
    }

    @Override
    public List<Integer> getBackgroundMusicIdListByTagId(int tagId) {
        return backgroundMusicTagRelationDao.getBackgroundMusicIdListByTagId(tagId);
    }

    @Override
    public List<Integer> getTagIdListByBackgroundMusicId(int backgroundMusicId) {
        return backgroundMusicTagRelationDao.getTagIdListByBackgroundMusicId(backgroundMusicId);
    }

    @Override
    public List<Integer> getBackgroundMusicIdListByTagIdList(List<Integer> tagIds) {
        return backgroundMusicTagRelationDao.getBackgroundMusicIdListByTagIdList(tagIds);
    }



}
