package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.BackgroundMusicTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface BackgroundMusicTagDao {

    int saveBackgroundMusicTag(BackgroundMusicTag backgroundMusicTag);

    boolean updateBackgroundMusicTag(BackgroundMusicTag backgroundMusicTag);

    boolean deleteBackgroundMusicTagById(int tagId);

    BackgroundMusicTag getBackgroundMusicTagById(int tagId);

    BackgroundMusicTag getBackgroundMusicTagByIdHard(int tagId);

    List<BackgroundMusicTag> getBackgroundMusicTagListByPage(int offset, int limit);

    List<BackgroundMusicTag> getAllBackgroundMusicTags();

    List<BackgroundMusicTag> getGroundMusicTagListByIdList(@Param("idList") List<Integer> idList);

    boolean deleteHard(int id);

    List<BackgroundMusicTag> getAllSecondLevelTags();

}
