package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Agree;
import cn.edu.nju.software.entity.Works;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
public interface AgreeService {

    boolean addAgree(Agree agree);

    boolean deleteAgree(int worksId, int userId);

    boolean deleteAgreeById(int id);

    List<Integer> getAgreeUserIdListByWorksId(int worksId, int offset, int limit);

    List<Integer> getAgreeWorksIdListByUserId(int userId, int offset, int limit);

    Agree getAgree(int userId, int worksId);

    List<Agree> extractNewRecords(Date lastExtractTime);

    //特殊排序
    List<Works> getWorksListByUserIdByPage(int userId,int page,int pageSize);

    boolean updateOrderTimeByStorySetId(Integer storySetId, Date orderTime,Integer userId);

}
