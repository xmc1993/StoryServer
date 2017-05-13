package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.FollowRelation;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface FollowService {

    boolean saveFollowRelation(FollowRelation followRelation);

    boolean deleteFollowRelation(int followerId, int followeeId);

    boolean deleteFollowRelationById(int id);
}
