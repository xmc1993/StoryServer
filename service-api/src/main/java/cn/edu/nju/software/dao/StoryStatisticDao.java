package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.StoryStatistic;
import org.springframework.stereotype.Repository;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface StoryStatisticDao {

    boolean saveStoryStatistic(StoryStatistic storyStatistic);

    boolean updateStoryStatistic(StoryStatistic storyStatistic);

    StoryStatistic getStoryStatisticByStoryId(int storyId);

    boolean newLike(int storyId);

    boolean removeLike(int storyId);

    boolean newTell(int storyId);

    boolean removeTell(int storyId);

    boolean newView(int storyId);

    boolean removeView(int storyId);

    boolean newComment(int storyId);

    boolean removeComment(int storyId);
}
