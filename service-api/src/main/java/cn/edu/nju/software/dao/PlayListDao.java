package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.PlayList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface PlayListDao {

    boolean savePlayList(PlayList playList);

    boolean updatePlayList(PlayList playList);

    boolean deletePlayList(int id);

    PlayList getPlayListById(int id);

    List<PlayList> getAllPlayListByPage(int offset, int limit);

    List<PlayList> getPlayListListByIdList(@Param("idList") List<Integer> idList);

}
