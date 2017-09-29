package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.UserStoryDao;
import cn.edu.nju.software.entity.UserStory;
import cn.edu.nju.software.service.UserStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class UserStoryServiceImpl implements UserStoryService {

    @Autowired
    private UserStoryDao userStoryDao;

    @Override
    public UserStory saveUserStory(UserStory userStory) {
        if (userStoryDao.saveUserStory(userStory)) {
            return userStory;
        }
        return userStory;
    }

    @Override
    public boolean updateUserStory(UserStory userStory) {
        return userStoryDao.updateUserStory(userStory);
    }

    @Override
    public boolean deleteUserStory(int id) {
        return userStoryDao.deleteUserStory(id);
    }

    @Override
    public UserStory getUserStoryById(int id) {
        return userStoryDao.getUserStoryById(id);
    }

    @Override
    public List<UserStory> getAllUserStoryByUserIdByPage(int userId, int visibility, int page, int pageSize) {
        int offset = page * pageSize;
        int limit = pageSize;
        return userStoryDao.getAllUserStoryByUserIdByPage(userId, visibility, offset, limit);
    }

    @Override
    public int getUserStoryCountByUserId(int userId, int visibility) {
        return userStoryDao.getUserStoryCountByUserId(userId, visibility);
    }

}
