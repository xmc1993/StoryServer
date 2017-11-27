package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.FollowRelation;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface FollowService {

    boolean saveFollowRelation(FollowRelation followRelation);

    boolean deleteFollowRelation(int followerId, int followeeId);

    boolean deleteFollowRelationById(int id);

    List<Integer> getUserFollowerList(int userId, int offset, int limit);

    Integer getUserFollowerCountByUserId(int userId);

    List<Integer> getUserFolloweeList(int userId, int offset, int limit);

    Integer getUserFolloweeCountByUserId(int userId);

    int getStatusBetween(int curUserId, int userId);

    int getRelation(Integer visitedUserId, Integer visitorId);

    Boolean dummyFollowRelation(int userId);
}

