package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.PlayListRelation;
import cn.edu.nju.software.entity.Works;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface PlayListRelationDao {

    boolean savePlayListRelation(PlayListRelation playListRelation);

    boolean deletePlayListRelationByPrimaryKey(int worksId, int playListId, int userId);

    boolean deletePlayListRelationById(int id);

    List<Integer> getWorksIdListByPlayListIdAndUserIdByPage(int playListId, int userId, int limit, int offset);

    boolean updateOrderTimeByStorySetId(int storySetId, Date orderTime);

    List<Works> getWorksListByPlayListIdByPage(int playListId, int userId, int limit, int offset);

}
