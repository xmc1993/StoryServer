package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Works;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
@Repository
public interface WorksDao {

    boolean saveWorks(Works works);

    List<Works> getWorksListByUserId(int userId);

    List<Works> getWorksListByStoryId(int storyId);

    Works getWorksById(int id);

    boolean updateWorks(Works works);

    boolean deleteWorksById(int id);

}
