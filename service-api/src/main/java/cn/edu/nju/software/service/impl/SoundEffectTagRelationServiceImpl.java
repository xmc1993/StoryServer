package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.SoundEffectTagDao;
import cn.edu.nju.software.dao.SoundEffectTagRelationDao;
import cn.edu.nju.software.entity.SoundEffectTag;
import cn.edu.nju.software.entity.SoundEffectTagRelation;
import cn.edu.nju.software.entity.TagRelation;
import cn.edu.nju.software.service.SoundEffectTagRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class SoundEffectTagRelationServiceImpl implements SoundEffectTagRelationService {

    @Autowired
    private SoundEffectTagRelationDao soundEffectTagRelationDao;
    @Autowired
    private SoundEffectTagDao soundEffectTagDao;

    @Override
    public boolean saveTagRelation(SoundEffectTagRelation tagRelation) {
        return soundEffectTagRelationDao.saveTagRelation(tagRelation);
    }

    @Override
    public boolean deleteTagRelationById(int id) {
        return soundEffectTagRelationDao.deleteTagRelationById(id);
    }

    @Override
    public boolean deleteTagRelationBySoundEffectIdAndTagId(int soundEffectId, int tagId) {
        return soundEffectTagRelationDao.deleteTagRelationBySoundEffectIdAndTagId(soundEffectId, tagId);
    }

    @Override
    public List<Integer> getSoundEffectIdListByTagId(int tagId) {
        return soundEffectTagRelationDao.getSoundEffectIdListByTagId(tagId);
    }

    @Override
    public List<Integer> getTagIdListBySoundEffectId(int soundEffectId) {
        return soundEffectTagRelationDao.getTagIdListBySoundEffectId(soundEffectId);
    }

    @Override
    public List<Integer> getSoundEffectIdListByTagIdList(List<Integer> tagIds) {
        return soundEffectTagRelationDao.getSoundEffectIdListByTagIdList(tagIds);
    }



}
