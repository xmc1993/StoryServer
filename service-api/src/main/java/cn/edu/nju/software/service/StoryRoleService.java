package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.StoryRole;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface StoryRoleService {

    StoryRole saveStoryRole(StoryRole storyRole);

    boolean updateStoryRole(StoryRole storyRole);

    boolean deleteStoryRole(int id);

    StoryRole getStoryRoleById(int id);

    List<StoryRole> getStoryRoleListByStoryId(int typeId);

}
