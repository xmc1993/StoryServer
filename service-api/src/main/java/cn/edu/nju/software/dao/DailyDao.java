package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Daily;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface DailyDao {

    boolean saveDaily(Daily daily);

    boolean updateDaily(Daily daily);

    boolean deleteDaily(int id);

    Daily getDailyById(int id);

    List<Daily> getDailyListByUserId(int userId, int visibility, int offset, int limit);

    int getDailyCountByUserId(int userId, int visibility);

    Daily getDraftDaily(Integer userId);

    boolean deleteDraftDaily(Integer userId);

    boolean newRead(int id);

}
