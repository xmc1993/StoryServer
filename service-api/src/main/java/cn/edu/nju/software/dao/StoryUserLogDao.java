package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.StoryUserLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kt on 2017/6/25.
 */
@Repository
public interface StoryUserLogDao {
    public boolean insertStoryUserLog(@Param("storyUserLog") StoryUserLog storyUserLog);
    public Integer deleteStoryUserLogByIdList(@Param("idList") List<Integer> idList);
    public List<Integer> getUserIdListByStoryId(@Param("storyId") int storyId, @Param("offset") int offset, @Param("limit") int limit);
    public List<Integer> getStoryIdListByUserId(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);
}
