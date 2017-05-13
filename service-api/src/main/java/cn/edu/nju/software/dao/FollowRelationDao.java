package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.FollowRelation;
import org.springframework.stereotype.Repository;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Repository
public interface FollowRelationDao {

    boolean saveFollowRelation(FollowRelation followRelation);

    boolean deleteFollowRelation(int followerId, int followeeId);

    boolean deleteFollowRelationById(int id);

}
