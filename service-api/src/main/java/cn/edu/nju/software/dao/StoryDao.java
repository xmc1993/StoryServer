package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Story;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface StoryDao {

    boolean saveStory(Story story);

    /**
     * 软删除
     * @param id
     * @return
     */
    boolean deleteStoryById(int id);

    Story getStoryById(int id);

    List<Story> getAllStories();

    boolean updateStory(Story story);

    boolean deleteHard(int id);

}
