package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.StoryCorrelation;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface StoryCorrelationService {

    boolean saveOrUpdateStoryCorrelation(StoryCorrelation storyCorrelation);

    List<StoryCorrelation> getCorrelationListByStoryId(int storyId);

    boolean deleteStoryCorrelationById(int id);



}
