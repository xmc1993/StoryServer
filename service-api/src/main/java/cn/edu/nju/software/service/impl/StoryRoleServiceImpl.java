package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.StoryRoleDao;
import cn.edu.nju.software.entity.StoryRole;
import cn.edu.nju.software.service.StoryRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class StoryRoleServiceImpl implements StoryRoleService {

    @Autowired
    private StoryRoleDao storyRoleDao;


    @Override
    public StoryRole saveStoryRole(StoryRole storyRole) {
        if (storyRoleDao.saveStoryRole(storyRole)) {
            return storyRole;
        }
        return null;
    }

    @Override
    public boolean updateStoryRole(StoryRole storyRole) {
        return storyRoleDao.updateStoryRole(storyRole);
    }

    @Override
    public boolean deleteStoryRole(int id) {
        return storyRoleDao.deleteStoryRole(id);
    }

    @Override
    public StoryRole getStoryRoleById(int id) {
        return storyRoleDao.getStoryRoleById(id);
    }

    @Override
    public List<StoryRole> getStoryRoleListByStoryId(int storyId) {
        return storyRoleDao.getStoryRoleListByStoryId(storyId);
    }


}
