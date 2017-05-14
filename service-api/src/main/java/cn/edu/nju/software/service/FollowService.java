package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.FollowRelation;
import cn.edu.nju.software.entity.UserBase;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface FollowService {

    boolean saveFollowRelation(FollowRelation followRelation);

    boolean deleteFollowRelation(int followerId, int followeeId);

    boolean deleteFollowRelationById(int id);

    List<UserBase> getUserFollowerList(int userId);

    List<UserBase> getUserFolloweeList(int userId);
}
