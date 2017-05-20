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

    List<Integer> getUserFollowerList(int userId);

    List<Integer> getUserFolloweeList(int userId);
}
