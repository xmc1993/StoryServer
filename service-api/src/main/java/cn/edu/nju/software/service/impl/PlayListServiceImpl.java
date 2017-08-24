package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.PlayListDao;
import cn.edu.nju.software.entity.PlayList;
import cn.edu.nju.software.entity.Works;
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
    public List<PlayList> getAllPlayListByUserIdByPage(int userId, int page, int pageSize) {
        int offset = (page-1)*pageSize;
        int limit = pageSize;
        List<PlayList> list = playListDao.getAllPlayListByUserIdByPage(userId, offset, limit);
        //默认的我喜欢的文件夹每个用户都有
        PlayList playList = new PlayList();
        playList.setUserId(userId);
        playList.setId(0);
        playList.setName("我喜欢");
        list.add(playList);
        return list;
    }

    @Override
    public List<PlayList> getPlayListListByIdList(List<Integer> idList) {
        return playListDao.getPlayListListByIdList(idList);
    }

}
