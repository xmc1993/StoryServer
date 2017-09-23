package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Daily;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface DailyService {

    Daily saveDaily(Daily daily);

    boolean updateDaily(Daily daily);

    boolean deleteDaily(int id);

    Daily getDailyById(int id);

    List<Daily> getDailyListByUserId(int userId, int visibility, int page, int pageSize);

    Integer getDailyCountByUserId(int userId, int visibility);

    Daily getDraftDaily(Integer userId);

    boolean updateDraftDaily(Daily daily);

    boolean deleteDraftDaily(Integer userId);

    Daily saveDraftDaily(Daily daily);

}
