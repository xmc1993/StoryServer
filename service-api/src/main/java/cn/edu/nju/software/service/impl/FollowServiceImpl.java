package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.FollowRelationDao;
import cn.edu.nju.software.dao.user.AppUserDao;
import cn.edu.nju.software.entity.FollowRelation;
import cn.edu.nju.software.entity.UserBase;
import cn.edu.nju.software.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowRelationDao followRelationDao;
    @Autowired
    private AppUserDao appUserDao;


    @Override
    public boolean saveFollowRelation(FollowRelation followRelation) {
        boolean res1 = followRelationDao.saveFollowRelation(followRelation);
        if (!res1) return false;

        boolean res2 = appUserDao.newFollower(followRelation.getFolloweeId());//被关注的人粉丝+1
        boolean res3 = appUserDao.newFollowee(followRelation.getFollowerId());//关注者关注的人数+1
        return res2 && res3;

    }

    @Override
    public boolean deleteFollowRelation(int followerId, int followeeId) {
        boolean res1 = followRelationDao.deleteFollowRelation(followerId, followeeId);
        if (!res1) return false;
        boolean res2 = appUserDao.loseFollower(followeeId);//被取消关注的人粉丝-1
        boolean res3 = appUserDao.removeFollowee(followerId);//取消关注者关注的人数-1
        return res2 && res3;
    }

    @Override
    public boolean deleteFollowRelationById(int id) {
        return followRelationDao.deleteFollowRelationById(id);
    }

    @Override
    public List<Integer> getUserFollowerList(int userId) {
        List<Integer> followerIdList = followRelationDao.getFollowerIdListByUserId(userId);
        return followerIdList;
    }

    @Override
    public List<Integer> getUserFolloweeList(int userId) {
        List<Integer> followeeIdList = followRelationDao.getFolloweeIdListByUserId(userId);
        return followeeIdList;
    }
}
