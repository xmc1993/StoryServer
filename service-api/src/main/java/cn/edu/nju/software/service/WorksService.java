package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.TwoTuple;
import cn.edu.nju.software.entity.Works;
import com.github.pagehelper.PageInfo;

import java.io.File;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
public interface WorksService {

    Works saveWorks(Works works);

    boolean listenWorks(int worksId);

    boolean updateWorks(Works works);

    boolean deleteWorksById(int id);

    Works getWorksById(int id);

    boolean deleteWorks(int storyId, int userId);

    List<Works> getWorksListByUserId(int userId, int offset, int limit);

    List<Works> getWorksListByUserIdByPage(int userId, int page, int pageSize);

    List<Works> getWorksListByStoryId(int storyId, int offset, int limit);

    List<Works> getWorksListByStoryIdByPage(int storyId, int page, int pageSize);

    List<Works> getWorksListByIdList(List<Integer> idList);

    Integer getWorksCount();

    Integer getWorksCountByUserId(Integer userId);

    Integer getWorksCountByStoryId(Integer storyId);

    Integer getWorksCountByIdList(List<Integer> idList);

    List<Works> getLatestWorksByPage(int page, int pageSize);

    List<Works> getMostPopularByPage(int page, int pageSize);

	List<TwoTuple<Integer,String>> getFirstWorkByPlayIdList(List<Integer> playIdList);

	PageInfo<Works> getWorksListByStoryIdListByPage(List<Integer> storyIdList, int page, int pageSize);

    Integer getUserIdByWorkId(int worksId);

    List<Integer> getWorkIdListByUserId(int userId);

    Integer getWorksAfterSomeDate(Integer userId, String date);
    
    String getOriginSoundLength(File file);
    
    boolean getWorksByUserAndStory(Integer userId,Integer storyId);

    List<Works> getListByUserAndStory(Integer userId,Integer storyId);
    
    //此方法是为了在用户更新头像时，使work表中的HeadImgUrl字段也联动更新
    boolean updateHeadImg(int userId,String url);
}
