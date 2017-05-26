package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.SoundEffectTagDao;
import cn.edu.nju.software.entity.SoundEffectTag;
import cn.edu.nju.software.service.SoundEffectTagService;
import cn.edu.nju.software.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class SoundEffectTagServiceImpl implements SoundEffectTagService {

    @Autowired
    private SoundEffectTagDao soundEffectTagDao;

    @Override
    public int saveSoundEffectTag(SoundEffectTag soundEffectTag) {
        return soundEffectTagDao.saveSoundEffectTag(soundEffectTag);
    }

    @Override
    public SoundEffectTag updateSoundEffectTag(SoundEffectTag soundEffectTag) {
        soundEffectTag.setUpdateTime(new Date());
        boolean res = soundEffectTagDao.updateSoundEffectTag(soundEffectTag);
        return res ? soundEffectTagDao.getSoundEffectTagById(soundEffectTag.getId()) : null;
    }

    @Override
    public boolean deleteSoundEffectTag(int tagId) {
        return soundEffectTagDao.deleteSoundEffectTagById(tagId);
    }

    @Override
    public SoundEffectTag getSoundEffectTagById(int tagId) {
        return soundEffectTagDao.getSoundEffectTagById(tagId);
    }


    @Override
    public List<SoundEffectTag> getSoundEffectTagListByIdList(List<Integer> idList) {
        idList.add(-1);
        return soundEffectTagDao.getTagListByIdList(idList);
    }

    @Override
    public List<SoundEffectTag> getAllSoundEffectTags() {
        return soundEffectTagDao.getAllSoundEffectTags();
    }

    @Override
    public List<SoundEffectTag> getSoundEffectTagsByPage(int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return soundEffectTagDao.getSoundEffectTagListByPage(offset, limit);
    }

    @Override
    public SoundEffectTag getSoundEffectTagByIdHard(int tagId) {
        return soundEffectTagDao.getSoundEffectTagByIdHard(tagId);
    }

}
