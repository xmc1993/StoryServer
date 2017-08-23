package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.StoryPopularFactor;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface StoryPopularFactorService {

    StoryPopularFactor saveStoryPopularFactor(StoryPopularFactor storyPopularFactor);

    boolean updateStoryPopularFactor(StoryPopularFactor storyPopularFactor);

    boolean deleteStoryPopularFactor(int id);

    StoryPopularFactor getStoryPopularFactorById(int id);

    List<StoryPopularFactor> getStoryPopularFactorListByTypeId(int typeId);

    List<StoryPopularFactor> getAllStoryPopularFactorByPage(int page, int pageSize);
    
    List<StoryPopularFactor> getStoryPopularFactorOfUser(Integer userId);
    
}
