package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.BackgroundMusic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
@Repository
public interface BackgroundMusicDao {

    boolean saveBackgroundMusic(BackgroundMusic backgroundMusic);

    List<BackgroundMusic> getAllBackgroundMusics();

    boolean updateBackgroundMusic(BackgroundMusic backgroundMusic);

    boolean deleteBackgroundMusicById(int id);

    BackgroundMusic getBackgroundMusicById(int id);

    BackgroundMusic getBackgroundMusicByIdHard(int id);

    boolean deleteHard(int id);

    List<BackgroundMusic> getBackgroundMusicListByPage(int offset, int limit);

    List<BackgroundMusic> getBackgroundMusicListByIdList(@Param("idList") List<Integer> idList);

    Integer getBackgroundMusicCount();
}
