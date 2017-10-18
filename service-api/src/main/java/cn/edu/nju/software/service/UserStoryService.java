package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.UserStory;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
public interface UserStoryService {

    UserStory saveUserStory(UserStory userStory);

    boolean updateUserStory(UserStory userStory);

    boolean deleteUserStory(int id);

    UserStory getUserStoryById(int id);

    List<UserStory> getAllUserStoryByUserIdByPage(int userId, int visibility, int page, int pageSize);

    int getUserStoryCountByUserId(int userId, int visibility);
    
    boolean roseUserStoryTellCount(Integer id);

}
