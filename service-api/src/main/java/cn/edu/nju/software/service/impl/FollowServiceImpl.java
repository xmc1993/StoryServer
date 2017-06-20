package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.FollowRelationDao;
import cn.edu.nju.software.dao.user.AppUserDao;
import cn.edu.nju.software.entity.FollowRelation;
import cn.edu.nju.software.service.FollowService;
import cn.edu.nju.software.util.Const;
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
    public List<Integer> getUserFollowerList(int userId, int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        List<Integer> followerIdList = followRelationDao.getFollowerIdListByUserId(userId, offset, limit);
        return followerIdList;
    }

    @Override
    public Integer getUserFollowerCountByUserId(int userId){
        return followRelationDao.getFollowerCountByUserId(userId);
    }

    @Override
    public List<Integer> getUserFolloweeList(int userId, int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        List<Integer> followeeIdList = followRelationDao.getFolloweeIdListByUserId(userId, offset, limit);
        return followeeIdList;
    }

    @Override
    public Integer getUserFolloweeCountByUserId(int userId){
        return followRelationDao.getFolloweeCountByUserId(userId);
    }

    @Override
    public int getStatusBetween(int curUserId, int userId) {
        boolean res1 = followRelationDao.getFollowRelation(curUserId, userId) != null;
        boolean res2 = followRelationDao.getFollowRelation(userId, curUserId) != null;
        if (res1 && res2) {
            return 3;//互相关注
        }
        if (res2) {
            return 2;//单方面被关注
        }
        if (res1) {
            return 1;//单方面关注
        }
        return 0;//无关联
    }
}
