package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.BackgroundMusicDao;
import cn.edu.nju.software.entity.BackgroundMusic;
import cn.edu.nju.software.service.BackgroundMusicService;
import cn.edu.nju.software.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class BackgroundMusicServiceImpl implements BackgroundMusicService {
    @Autowired
    private BackgroundMusicDao backgroundMusicDao;

    @Override
    public BackgroundMusic saveBackgroundMusic(BackgroundMusic backgroundMusic) {
        boolean res = backgroundMusicDao.saveBackgroundMusic(backgroundMusic);
        if (res) {
            return backgroundMusic;
        }else {
            return null;
        }
    }

    @Override
    public boolean deleteBackgroundMusic(int id) {
        return backgroundMusicDao.deleteBackgroundMusicById(id);
    }

    @Override
    public BackgroundMusic updateBackgroundMusic(BackgroundMusic backgroundMusic) {
        backgroundMusic.setUpdateTime(new Date());
        boolean res = backgroundMusicDao.updateBackgroundMusic(backgroundMusic);
        return res ? backgroundMusicDao.getBackgroundMusicById(backgroundMusic.getId()) : null;
    }

    @Override
    public List<BackgroundMusic> getAllBackgroundMusic() {
        return backgroundMusicDao.getAllBackgroundMusics();
    }

    @Override
    public List<BackgroundMusic> getBackgroundMusicListByPage(int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return backgroundMusicDao.getBackgroundMusicListByPage(offset, limit);
    }

    @Override
    public BackgroundMusic getBackgroundMusicById(int id) {
        return backgroundMusicDao.getBackgroundMusicById(id);
    }

    @Override
    public List<BackgroundMusic> getBackgroundMusicListByIdList(List<Integer> idList) {
        idList.add(-1);
        return backgroundMusicDao.getBackgroundMusicListByIdList(idList);
    }

    @Override
    public Integer getBackgroundMusicCount(){
        return backgroundMusicDao.getBackgroundMusicCount();
    }

}
