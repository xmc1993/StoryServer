package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.AgreeDao;
import cn.edu.nju.software.dao.PlayListDao;
import cn.edu.nju.software.dao.WorksDao;
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
    @Autowired
    private AgreeDao agreeDao;
    @Autowired
    private WorksDao worksDao;

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
        PlayList playList = playListDao.getPlayListById(id);
        setCover(playList);
        return playList;
    }

    @Override
    public List<PlayList> getAllPlayListByUserIdByPage(int userId, int page, int pageSize) {
        int offset = page*pageSize;
        int limit = pageSize;
        List<PlayList> list = playListDao.getAllPlayListByUserIdByPage(userId, offset, limit);
        //默认的我喜欢的文件夹每个用户都有
        PlayList playList = new PlayList();
        playList.setUserId(userId);
        playList.setId(0);
        playList.setName("我喜欢");
        list.add(playList);
        setCoverByPatch(list);
        return list;
    }

    @Override
    public List<PlayList> getPlayListListByIdList(List<Integer> idList) {
        List<PlayList> list = playListDao.getPlayListListByIdList(idList);
        setCoverByPatch(list);
        return list;
    }

    private void setCover(PlayList playList) {
        List<Integer> idList = agreeDao.getAgreeUserIdListByWorksId(playList.getId());
        List<Works> worksList = worksDao.getWorksListByIdList(idList);
        if (worksList.size() > 0) {
            playList.setCover(worksList.get(0).getCoverUrl());
        }
    }

    private void setCoverByPatch(List<PlayList> playLists) {
        for (PlayList playList : playLists) {
            setCover(playList);
        }
    }

}
