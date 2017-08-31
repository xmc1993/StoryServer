package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dto.WorksVo;
import cn.edu.nju.software.entity.Works;
import cn.edu.nju.software.service.StoryTagService;
import cn.edu.nju.software.service.WorksComplexService;
import cn.edu.nju.software.service.WorksService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/8/31.
 */
@Service
public class WorksComplexServiceImpl implements WorksComplexService {
    @Autowired
    private WorksService worksService;
    @Autowired
    private StoryTagService storyTagService;

    @Override
    public List<WorksVo> getWorksListByUserId(int userId, int offset, int limit) {
        List<Works> worksList = worksService.getWorksListByUserId(userId, offset, limit);

        return null;
    }

    @Override
    public List<WorksVo> getWorksListByStoryId(int storyId, int offset, int limit) {
        return null;
    }

    @Override
    public List<WorksVo> getWorksListByIdList(List<Integer> idList) {
        return null;
    }

    @Override
    public List<WorksVo> getLatestWorksByPage(int page, int pageSize) {
        return null;
    }

    @Override
    public List<WorksVo> getMostPopularByPage(int page, int pageSize) {
        return null;
    }

    @Override
    public PageInfo<WorksVo> getWorksListByStoryIdListByPage(List<Integer> storyIdList, int page, int pageSize) {
        return null;
    }

    private void setTagList() {

    }
}
