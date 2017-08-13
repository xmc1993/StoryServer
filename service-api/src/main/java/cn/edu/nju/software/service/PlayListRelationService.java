package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.PlayListRelation;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface PlayListRelationService {

    boolean savePlayListRelation(PlayListRelation playListRelation);

    boolean deletePlayListRelationByPrimaryKey(int worksId, int playListId, int userId);

    boolean deletePlayListRelationById(int id);

    List<Integer> getWorksIdListByPlayListIdAndUserIdByPage(int playListId, int userId, int page, int pageSize);

}
