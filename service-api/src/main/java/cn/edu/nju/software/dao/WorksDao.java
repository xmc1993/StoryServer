package cn.edu.nju.software.dao;

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

}
