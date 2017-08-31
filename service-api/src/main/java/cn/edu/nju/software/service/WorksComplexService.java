package cn.edu.nju.software.service;

import cn.edu.nju.software.dto.WorksVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
public interface WorksComplexService {

    List<WorksVo> getWorksListByUserId(int userId, int offset, int limit);

    List<WorksVo> getWorksListByStoryId(int storyId, int offset, int limit);

    List<WorksVo> getWorksListByIdList(List<Integer> idList);

    List<WorksVo> getLatestWorksByPage(int page, int pageSize);

    List<WorksVo> getMostPopularByPage(int page, int pageSize);

	PageInfo<WorksVo> getWorksListByStoryIdListByPage(List<Integer> storyIdList, int page, int pageSize);

}
