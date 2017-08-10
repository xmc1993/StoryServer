package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.PlayListRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface PlayListRelationDao {

    boolean savePlayListRelation(PlayListRelation playListRelation);

    boolean deletePlayListRelationById(int id);

    boolean deletePlayListRelationByStoryIdAndAlbumId(int storyId, int albumId);

    List<Integer> getStoryIdListByAlbumId(int albumId);

    List<Integer> getAlbumIdListByStoryId(int storyId);

    List<Integer> getStoryIdListByAlbumIdList(@Param("idList") List<Integer> idList);

}
