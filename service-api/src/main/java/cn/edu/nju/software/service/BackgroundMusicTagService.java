package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.BackgroundMusicTag;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface BackgroundMusicTagService {

    int saveBackgroundMusicTag(BackgroundMusicTag backgroundMusicTag);

    BackgroundMusicTag updateBackgroundMusicTag(BackgroundMusicTag backgroundMusicTag);

    boolean deleteBackgroundMusicTag(int tagId);

    BackgroundMusicTag getBackgroundMusicTagById(int tagId);

    List<BackgroundMusicTag> getBackgroundMusicTagListByIdList(List<Integer> idList);

    List<BackgroundMusicTag> getAllBackgroundMusicTags();

    List<BackgroundMusicTag> getBackgroundMusicTagsByPage(int offset, int limit);

    BackgroundMusicTag getBackgroundMusicTagByIdHard(int tagId);

}
