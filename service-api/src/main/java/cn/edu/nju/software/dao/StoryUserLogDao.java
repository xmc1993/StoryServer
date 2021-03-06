package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.StoryUserLog;
import cn.edu.nju.software.vo.StoryUserLogVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Kt on 2017/6/25.
 */
@Repository
public interface StoryUserLogDao {

    boolean insertStoryUserLog(@Param("storyUserLog") StoryUserLog storyUserLog);

    Integer deleteStoryUserLogByIdList(@Param("idList") List<Integer> idList);

    StoryUserLog getStoryUserLogById(@Param("id") int id);

    List<Integer> getUserIdListByStoryIdTimeDesc(@Param("storyId") int storyId, @Param("offset") int offset, @Param("limit") int limit);

    List<Integer> getStoryIdListByUserIdTimeDesc(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    int getUserCountByStoryId(@Param("storyId") int storyId);

    int getStoryCountByUserId(@Param("userId") int userId);

    boolean deleteStoryUserLogById(@Param("id") int id);

    List<StoryUserLog> getStoryUserLogByPageTimeDesc(int offset, int limit);

    int getStoryUserLogCount();

    List<StoryUserLogVo> getStoryLogVoByPage(@Param("offset") int offset, @Param("limit") int limit);

    List<StoryUserLog> extractNewRecords(Date lastExtractTime);

}
