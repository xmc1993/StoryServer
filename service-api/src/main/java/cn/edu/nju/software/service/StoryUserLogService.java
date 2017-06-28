package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.StoryUserLog;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.vo.StoryUserLogVo;

import java.util.List;

/**
 * Created by Kt on 2017/6/27.
 */
public interface StoryUserLogService {
    boolean saveLog(StoryUserLog storyUserLog);

    boolean deleteLog(int id);

    boolean deleteLogByIdList(int[] idArray);

    List<StoryUserLog> getStoryUserLogByPage(int offset, int limit);

    int getStoryUserLogCount();

    List<Story> getStoryListByUserIdTimeDesc(int userId, int offset, int limit);

    int getStoryCountByUserId(int userId);

    List<User> getUserListByStoryIdTimeDesc(int storyId, int offset, int limit);

    int getUserCountByStoryID(int storyId);

    List<StoryUserLogVo> getStoryUserLogVoByPageTimeDesc(int offset, int limit);
}
