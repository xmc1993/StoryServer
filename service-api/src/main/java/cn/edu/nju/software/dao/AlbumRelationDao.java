package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.AlbumRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface AlbumRelationDao {

    boolean saveAlbumRelation(AlbumRelation albumRelation);

    boolean deleteAlbumRelationById(int id);

    boolean deleteAlbumRelationByStoryIdAndAlbumId(int storyId, int albumId);

    List<Integer> getStoryIdListByAlbumId(int albumId);

    List<Integer> getAlbumIdListByStoryId(int storyId);

    List<Integer> getStoryIdListByAlbumIdList(@Param("idList") List<Integer> idList);

}
