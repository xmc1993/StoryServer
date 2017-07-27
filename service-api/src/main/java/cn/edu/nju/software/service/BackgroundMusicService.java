package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.BackgroundMusic;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
public interface BackgroundMusicService {

    BackgroundMusic saveBackgroundMusic(BackgroundMusic backgroundMusic);

    boolean deleteBackgroundMusic(int id);

    BackgroundMusic updateBackgroundMusic(BackgroundMusic backgroundMusic);

    List<BackgroundMusic> getAllBackgroundMusic();

    List<BackgroundMusic> getBackgroundMusicListByPage(int offset, int limit);

    BackgroundMusic getBackgroundMusicById(int id);

    List<BackgroundMusic> getBackgroundMusicListByIdList(List<Integer> idList);

    Integer getBackgroundMusicCount();
}
