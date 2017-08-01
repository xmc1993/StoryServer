package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.AlbumRelation;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface AlbumRelationService {

    boolean saveAlbumRelation(AlbumRelation albumRelation);

    boolean deleteAlbumRelationById(int id);

    boolean deleteAlbumRelationByStoryIdAndAlbumId(int storyId, int albumId);

    List<Integer> getStoryIdListByAlbumId(int albumId);

    List<Integer> getAlbumIdListByStoryId(int storyId);

    List<Integer> getStoryIdListByAlbumIdList(List<Integer> albumIds);


}
