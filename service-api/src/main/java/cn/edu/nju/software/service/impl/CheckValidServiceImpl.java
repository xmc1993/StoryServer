package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.*;
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
    @Autowired
    private BackgroundMusicDao backgroundMusicDao;
    @Autowired
    private WorksDao worksDao;
    @Autowired
    private SoundEffectTagDao soundEffectTagDao;
    @Autowired
    private BackgroundMusicTagDao backgroundMusicTagDao;

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
    public boolean isBackgroundMusicExist(int id) {
        return backgroundMusicDao.getBackgroundMusicById(id) != null;
    }

    @Override
    public boolean isSoundEffectExistHard(int soundEffectId) {
        return soundEffectDao.getSoundEffectByIdHard(soundEffectId) != null;
    }

    @Override
    public boolean isBackgroundMusicExistHard(int id) {
        return backgroundMusicDao.getBackgroundMusicByIdHard(id) != null;
    }

    @Override
    public boolean isWorksExist(int worksId) {
        return worksDao.getWorksById(worksId) != null;
    }

    @Override
    public boolean isWorksExistHard(int worksId) {
        return worksDao.getWorksByIdHard(worksId) != null;
    }

    @Override
    public boolean isSoundEffectTagExist(int tagId) {
        return soundEffectTagDao.getSoundEffectTagById(tagId) != null;
    }

    @Override
    public boolean isBackgroundMusicTagExist(int tagId) {
        return backgroundMusicTagDao.getBackgroundMusicTagById(tagId) != null;
    }

    @Override
    public boolean isSoundEffectTagExistHard(int tagId) {
        return soundEffectTagDao.getSoundEffectTagByIdHard(tagId) != null;
    }

    @Override
    public boolean isBackgroundMusicTagExistHard(int tagId) {
        return backgroundMusicTagDao.getBackgroundMusicTagByIdHard(tagId) != null;
    }
}
