package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.StoryDao;
import cn.edu.nju.software.dao.UserDao;
import cn.edu.nju.software.dao.UserRelationStoryDao;
import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.UserRelationStory;
import cn.edu.nju.software.service.UserRelationStoryService;
import cn.edu.nju.software.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRelationStoryServiceImpl implements UserRelationStoryService {

    @Autowired
    private UserRelationStoryDao userRelationStoryDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private StoryDao storyDao;
    @Override
    public boolean addUserRelationStory(Integer storyId,Integer userId){
        if(storyDao.getStoryById(storyId)==null) return false;
        if(userRelationStoryDao.storyIsLikedByUserId(storyId,userId)!=null) return false;
        userDao.addLikeStoryCount(userId);
        storyDao.addStoryLikeCount(storyId);
        UserRelationStory userRelationStory=new UserRelationStory();
        userRelationStory.setStoryId(storyId);
        userRelationStory.setUserId(userId);
        return userRelationStoryDao.insert(userRelationStory);
    }
    @Override
    public boolean deleteUserRelationStory(Integer storyId,Integer userId){
        if(storyDao.getStoryById(storyId)==null) return false;
        if(userRelationStoryDao.storyIsLikedByUserId(storyId,userId)==null) return false;
        userDao.delLikeStoryCount(userId);
        storyDao.delStoryLikeCount(storyId);
        return userRelationStoryDao.delete(storyId, userId);
    }
    @Override
    public List<Story> getLikeStories(int userId, int offset, int limit){
        List<Integer> idList = userRelationStoryDao.getStoryIdListByUserId(userId);
        if(idList.size()==0) return new ArrayList<Story>();
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        List<Story> storyList=storyDao.getStoryListByIdList(idList,offset,limit);
        return storyList;
    }
    @Override
    public Integer getLikeStoriesCount(int userId){
        List<Integer> idList = userRelationStoryDao.getStoryIdListByUserId(userId);
        if(idList.size()==0) return 0;
        return storyDao.getStoryCountByIdList(idList);
    }

    @Override
    public boolean isLikedByUser(int userId, int storyId) {
        if(userRelationStoryDao.storyIsLikedByUserId(storyId,userId)==null) return false;
        return true;
    }
}
