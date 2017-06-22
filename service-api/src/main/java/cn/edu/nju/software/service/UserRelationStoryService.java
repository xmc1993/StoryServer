package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.UserRelationStory;

import java.util.List;

/**
 * Created by Kt on 2017/6/21.
 */
public interface UserRelationStoryService {

    boolean addUserRelationStory(Integer storyId, Integer userId);

    boolean deleteUserRelationStory(Integer storyId, Integer userId);

    List<Story> getLikeStories(int userId, int offset, int limit);

    Integer getLikeStoriesCount(int userId);

    boolean isLikedByUser(int userId, int storyId);
}
