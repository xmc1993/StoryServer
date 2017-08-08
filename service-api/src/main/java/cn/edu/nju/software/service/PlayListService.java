package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.PlayList;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface PlayListService {

    PlayList savePlayList(PlayList playList);

    boolean updatePlayList(PlayList playList);

    boolean deletePlayList(int id);

    PlayList getPlayListById(int id);

    List<PlayList> getAllPlayListByPage(int page, int pageSize);

    List<PlayList> getPlayListListByIdList(List<Integer> idList);

}
