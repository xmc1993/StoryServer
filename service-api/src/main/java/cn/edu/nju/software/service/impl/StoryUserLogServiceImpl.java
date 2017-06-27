package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.StoryDao;
import cn.edu.nju.software.dao.StoryUserLogDao;
import cn.edu.nju.software.dao.UserDao;
import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.StoryUserLog;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.StoryUserLogService;
import cn.edu.nju.software.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kt on 2017/6/27.
 */
@Service
public class StoryUserLogServiceImpl implements StoryUserLogService{
    @Autowired
    private StoryUserLogDao storyUserLogDao;
    @Autowired
    private StoryDao storyDao;
    @Autowired
    private UserDao userDao;
    @Override
    public boolean saveLog(StoryUserLog storyUserLog){
        return storyUserLogDao.insertStoryUserLog(storyUserLog);
    }
    @Override
    public boolean deleteLog(int id){
        return storyUserLogDao.deleteStoryUserLogById(id);
    }
    @Override
    public boolean deleteLogByIdList(int[] idArray){
        List<Integer> idList = new ArrayList(Arrays.asList(idArray));
        if(idArray.length==storyUserLogDao.deleteStoryUserLogByIdList(idList)) return true;
        else return false;
    }
    @Override
    public List<StoryUserLog> getStoryUserLogByPage(int offset, int limit){
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        List<StoryUserLog> logList  = storyUserLogDao.getStoryUserLogByPageTimeDesc(offset,limit);
        return logList;
    }
    @Override
    public int getStoryUserLogCount(){
        return storyUserLogDao.getStoryUserLogCount();
    }

    @Override
    public List<Story> getStoryListByUserIdTimeDesc(int userId, int offset, int limit){
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        List<Integer> idList = storyUserLogDao.getStoryIdListByUserIdTimeDesc(userId,offset,limit);
        List<Story> storyList = storyDao.getStoryListByIdList(idList,offset,limit);
        return storyList;
    }
    @Override
    public int getStoryCountByUserId(int userId){
        return storyUserLogDao.getStoryCountByUserId(userId);
    }
    @Override
    public List<User> getUserListByStoryIdTimeDesc(int storyId, int offset, int limit){
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        List<Integer> idList = storyUserLogDao.getUserIdListByStoryIdTimeDesc(storyId,offset,limit);
        List<User> userList = userDao.getUserListByIdList(idList,offset,limit);
        return userList;
    }
    @Override
    public int getUserCountByStoryID(int storyId){
        return storyUserLogDao.getUserCountByStoryId(storyId);
    }

}
