package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.StoryTag;
import cn.edu.nju.software.service.*;
import cn.edu.nju.software.vo.StoryNewVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmc1993 on 2017/8/6.
 */
@Service
public class StoryComplexServiceImpl implements StoryComplexService {
    @Autowired
    private StoryService storyService;
    @Autowired
    private TagRelationService tagRelationService;
    @Autowired
    private StoryTagService storyTagService;
    @Autowired
    private UserRelationStoryService userRelationStoryService;

    @Override
    public List<StoryNewVo> getStoryNewVoListByIdList(List<Integer> idList, Integer userId) {
        List<Story> storyList = storyService.getStoryListByIdList(idList, 0, Integer.MAX_VALUE);
        return getStoryNewVoListByStoryList(storyList, userId);
    }

    @Override
    public List<StoryNewVo> getStoryNewVoListByStoryList(List<Story> storyList, Integer userId) {
        return storyList2VoList(storyList, userId);
    }

    private List<StoryNewVo> storyList2VoList(List<Story> list, int userId){
        List<StoryNewVo> storyVoList = new ArrayList<>();
        for (Story story : list) {
            storyVoList.add(story2Vo(story, userId));
        }
        return storyVoList;
    }

    private StoryNewVo story2Vo(Story story, int userId){
        if (story == null) {
            return null;
        }
        StoryNewVo storyVo = new StoryNewVo();
        List<Integer> idList = tagRelationService.getTagIdListByStoryId(story.getId());
        List<StoryTag> storyTagList = storyTagService.getStoryTagListByIdList(idList);
        storyVo.setTagList(storyTagList);
        BeanUtils.copyProperties(story, storyVo);
        if (userId > 0) {
            boolean isLiked = userRelationStoryService.isLikedByUser(userId, story.getId());
            storyVo.setLike(isLiked);
        }
        return storyVo;
    }
}
