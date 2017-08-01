package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.AlbumDao;
import cn.edu.nju.software.dao.AlbumRelationDao;
import cn.edu.nju.software.entity.Album;
import cn.edu.nju.software.entity.AlbumRelation;
import cn.edu.nju.software.service.AlbumRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class AlbumRelationServiceImpl implements AlbumRelationService {

    @Autowired
    private AlbumRelationDao albumRelationDao;
    @Autowired
    private AlbumDao storyAlbumDao;

    @Override
    public boolean saveAlbumRelation(AlbumRelation albumRelation) {
        return albumRelationDao.saveAlbumRelation(albumRelation);
    }

    @Override
    public boolean deleteAlbumRelationById(int id) {
        return albumRelationDao.deleteAlbumRelationById(id);
    }

    @Override
    public boolean deleteAlbumRelationByStoryIdAndAlbumId(int storyId, int albumId) {
        return albumRelationDao.deleteAlbumRelationByStoryIdAndAlbumId(storyId, albumId);
    }

    @Override
    public List<Integer> getStoryIdListByAlbumId(int albumId) {
        return albumRelationDao.getStoryIdListByAlbumId(albumId);
    }

    @Override
    public List<Integer> getAlbumIdListByStoryId(int storyId) {
        return albumRelationDao.getAlbumIdListByStoryId(storyId);
    }

    @Override
    public List<Integer> getStoryIdListByAlbumIdList(List<Integer> albumIds) {
        return albumRelationDao.getStoryIdListByAlbumIdList(albumIds);
    }


}
