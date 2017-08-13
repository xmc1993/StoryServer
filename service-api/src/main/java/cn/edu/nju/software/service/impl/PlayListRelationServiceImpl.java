package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.PlayListRelationDao;
import cn.edu.nju.software.entity.PlayListRelation;
import cn.edu.nju.software.service.PlayListRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class PlayListRelationServiceImpl implements PlayListRelationService {
    @Autowired
    private PlayListRelationDao playListRelationDao;

    @Override
    public boolean savePlayListRelation(PlayListRelation playListRelation) {
        return playListRelationDao.savePlayListRelation(playListRelation);
    }

    @Override
    public boolean deletePlayListRelationByPrimaryKey(int worksId, int playListId, int userId) {
        return playListRelationDao.deletePlayListRelationByPrimaryKey(worksId, playListId, userId);
    }

    @Override
    public boolean deletePlayListRelationById(int id) {
        return playListRelationDao.deletePlayListRelationById(id);
    }

    @Override
    public List<Integer> getWorksIdListByPlayListIdAndUserIdByPage(int playListId, int userId, int page, int pageSize) {
        int offset = page * pageSize;
        int limit = pageSize;
        return playListRelationDao.getWorksIdListByPlayListIdAndUserIdByPage(playListId, userId, offset, limit);
    }
}
