package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.StoryRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface StoryRoleDao {

    boolean saveStoryRole(StoryRole storyRole);

    boolean updateStoryRole(StoryRole storyRole);

    boolean deleteStoryRole(int id);

    StoryRole getStoryRoleById(int id);

    List<StoryRole> getStoryRoleListByStoryId(int storyId);

}
