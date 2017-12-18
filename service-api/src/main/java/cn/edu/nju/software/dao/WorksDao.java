package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.TwoTuple;
import cn.edu.nju.software.entity.Works;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
@Repository
public interface WorksDao {

    List<Works> getLatestWorksByPage(int offset, int limit);

    List<Works> getMostPopularByPage(int offset, int limit);

    boolean saveWorks(Works works);

    List<Works> getWorksListByUserId(int userId, int offset, int limit);

    List<Works> getWorksListByStoryId(int storyId, int offset, int limit);

    Works getWorksById(int id);

    Works getWorksByIdHard(int id);
    
    List<Works> getWorksByUserAndStory(@Param("userId") Integer userId,@Param("storyId") Integer storyId);

    List<Works> getWorksListByIdList(@Param("idList")List<Integer> idList);

    boolean updateWorks(Works works);

    boolean deleteWorksById(int id);

    boolean updateUserName(int userId, String userName);

    boolean updateStoryTitle(int storyId, String storyTitle);

    boolean updateHeadImg(int userId, String headImgUrl);

    boolean updateCoverUrl(int storyId, String coverUrl);

    Integer getWorksCount();

    Integer getWorksCountByUserId(Integer userId);

    Integer getWorksCountByStoryId(Integer storyId);

    Integer getWorksCountByIdList(@Param("idList")List<Integer> idList);

    boolean addReviewCount(@Param("workId") Integer workId);

    boolean delReviewCount(@Param("workId") Integer workId);

    boolean addListenCount(@Param("workId") Integer workId);

    boolean setDelReviewCount(@Param("workId") Integer workId, @Param("delReviewCount") int delReviewCount);

	List<TwoTuple<Integer,String>> getFirstWorkByPlayIdList(@Param("playIdList") List<Integer> playIdList);

	List<Works> getWorksListByStoryIdList(@Param("storyIdList") List<Integer> storyIdList);

    Integer getUserIdByWorkId(int worksId);

    Integer getWorksAfterSomeDate(Integer userId, String date);
    
    List<Integer> getUserIdListByStoryId(Integer storyId);

    List<Integer> getWorkIdListByUserId(Integer userId);
    
    List<Integer> getStoryIdListByUserId(Integer userId);

    boolean newLike(int id);

    boolean cancelLike(int id);

    List<Works> getWorksListByOrderRule(int offset, int limit,@Param("fieldName") String fieldName,@Param("orderRule") String orderRule);
}
