package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.StoryCorrelation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface StoryCorrelationDao {

    boolean saveStoryCorrelation(StoryCorrelation storyCorrelation);

    boolean deleteStoryCorrelationById(int id);

    StoryCorrelation getByPrimaryKey(int storyIdA, int storyIdB);

    List<StoryCorrelation> getByStoryId(int storyId);

    boolean updateStoryCorrelation(StoryCorrelation storyCorrelation);

}
