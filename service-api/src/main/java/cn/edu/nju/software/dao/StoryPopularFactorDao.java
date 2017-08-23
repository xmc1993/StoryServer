package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.StoryPopularFactor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface StoryPopularFactorDao {

    boolean saveStoryPopularFactor(StoryPopularFactor storyPopularFactor);

    boolean updateStoryPopularFactor(StoryPopularFactor storyPopularFactor);

    boolean deleteStoryPopularFactor(int id);

    StoryPopularFactor getStoryPopularFactorById(int id);

    List<StoryPopularFactor> getStoryPopularFactorListByTypeId(int typeId);

    List<StoryPopularFactor> getAllStoryPopularFactorByPage(int offset, int limit);
    
    List<StoryPopularFactor> getStoryPopularFactorOfUser(Integer userId);


}
