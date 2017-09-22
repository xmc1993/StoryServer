package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.UserStory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface UserStoryDao {

    boolean saveUserStory(UserStory userStory);

    boolean updateUserStory(UserStory userStory);

    boolean deleteUserStory(int id);

    UserStory getUserStoryById(int id);

    List<UserStory> getAllUserStoryByUserIdByPage(int userId, int offset, int limit);

    int getUserStoryCountByUserId(int userId);
}
