package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.TwoTuple;
import cn.edu.nju.software.entity.Works;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
public interface WorksService {

    boolean saveWorks(Works works);

    boolean listenWorks(int worksId);

    boolean updateWorks(Works works);

    boolean deleteWorksById(int id);

    Works getWorksById(int id);

    boolean deleteWorks(int storyId, int userId);

    List<Works> getWorksListByUserId(int userId, int offset, int limit);

    List<Works> getWorksListByStoryId(int storyId, int offset, int limit);

    List<Works> getWorksListByIdList(List<Integer> idList);

    Integer getWorksCount();

    Integer getWorksCountByUserId(Integer userId);

    Integer getWorksCountByStoryId(Integer storyId);

    Integer getWorksCountByIdList(List<Integer> idList);

    List<Works> getLatestWorksByPage(int page, int pageSize);

    List<Works> getMostPopularByPage(int page, int pageSize);

	List<TwoTuple<Integer,String>> getFirstWorkByPlayIdList(List<Integer> playIdList);

	PageInfo<Works> getWorksListByStoryIdListByPage(List<Integer> storyIdList, int page, int pageSize);
}
