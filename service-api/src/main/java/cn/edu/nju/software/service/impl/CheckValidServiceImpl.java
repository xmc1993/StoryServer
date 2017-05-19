package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.SoundEffectDao;
import cn.edu.nju.software.dao.StoryDao;
import cn.edu.nju.software.dao.StoryTagDao;
import cn.edu.nju.software.service.CheckValidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class CheckValidServiceImpl implements CheckValidService {
    @Autowired
    private StoryTagDao storyTagDao;
    @Autowired
    private StoryDao storyDao;
    @Autowired
    private SoundEffectDao soundEffectDao;

    @Override
         public boolean isTagExist(int tagId) {
        return storyTagDao.getStoryTagById(tagId) != null;
    }

    @Override
    public boolean isTagExistHard(int tagId) {
        return storyTagDao.getStoryTagByIdHard(tagId) != null;
    }

    @Override
    public boolean isStoryExist(int storyId) {
        return storyDao.getStoryById(storyId) != null;
    }

    @Override
    public boolean isStoryExistHard(int storyId) {
        return storyDao.getStoryByIdHard(storyId) != null;
    }

    @Override
    public boolean isUserExist(int userId) {
        return false;
    }

    @Override
    public boolean isSoundEffectExist(int soundEffectId) {
        return soundEffectDao.getSoundEffectById(soundEffectId) != null;
    }

    @Override
    public boolean isSoundEffectExistHard(int soundEffectId) {
        return soundEffectDao.getSoundEffectByIdHard(soundEffectId) != null;
    }
}
