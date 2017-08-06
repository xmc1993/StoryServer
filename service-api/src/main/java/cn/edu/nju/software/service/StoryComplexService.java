package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.vo.StoryNewVo;

import java.util.List;

/**
 * Created by xmc1993 on 2017/8/6.
 */


public interface StoryComplexService {
    List<StoryNewVo> getStoryNewVoListByIdList(List<Integer> idList, Integer userId);

    List<StoryNewVo> getStoryNewVoListByStoryList(List<Story> storyList, Integer userId);
}
