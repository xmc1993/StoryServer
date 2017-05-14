package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Works;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
public interface WorksService {

    boolean saveWorks(Works works);

    boolean updateWorks(Works works);

    boolean deleteWorksById(int id);

    boolean deleteWorks(int storyId, int userId);

    List<Works> getWorksListByUserId(int userId);

    List<Works> getWorksListByStoryId(int storyId);

}
