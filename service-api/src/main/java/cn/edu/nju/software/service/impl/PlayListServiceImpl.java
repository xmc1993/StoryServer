package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.PlayListDao;
import cn.edu.nju.software.entity.PlayList;
import cn.edu.nju.software.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class PlayListServiceImpl implements PlayListService {

    @Autowired
    private PlayListDao playListDao;


    @Override
    public PlayList savePlayList(PlayList playList) {
        if (playListDao.savePlayList(playList)) {
            return playList;
        }
        return null;
    }

    @Override
    public boolean updatePlayList(PlayList playList) {
        return playListDao.updatePlayList(playList);
    }

    @Override
    public boolean deletePlayList(int id) {
        return playListDao.deletePlayList(id);
    }

    @Override
    public PlayList getPlayListById(int id) {
        return playListDao.getPlayListById(id);
    }


    @Override
    public List<PlayList> getAllPlayListByPage(int page, int pageSize) {
        int offset = page*pageSize;
        int limit = pageSize;
        return playListDao.getAllPlayListByPage(offset, limit);
    }

    @Override
    public List<PlayList> getPlayListListByIdList(List<Integer> idList) {
        return playListDao.getPlayListListByIdList(idList);
    }
}
