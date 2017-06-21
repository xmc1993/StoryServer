package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.UserRelationStory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRelationStoryDao {
    //boolean insert(@Param("storyId") Integer storyId, @Param("userId") Integer userId);
    boolean insert(UserRelationStory userRelationStory);
    int update(@Param("storyId") Integer storyId, @Param("userId") Integer userId,@Param("id") Integer id);

    boolean delete(@Param("storyId") Integer storyId, @Param("userId") Integer userId);

    List<Integer> getStoryIdListByUserId(@Param("userId") Integer userId);

    boolean delete(int storyId);

    List<Integer> getUserIdListByStoryId(int storyId);


    Integer storyIsLikedByUserId(@Param("storyId") Integer storyId, @Param("userId") Integer userId);
}
