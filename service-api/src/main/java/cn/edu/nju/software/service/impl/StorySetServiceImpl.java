package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.StorySetDao;
import cn.edu.nju.software.entity.StorySet;
import cn.edu.nju.software.service.StorySetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class StorySetServiceImpl implements StorySetService {

    @Autowired
    private StorySetDao storySetDao;

    @Override
    public StorySet saveStorySet(StorySet storySet) {
        if (storySetDao.saveStorySet(storySet)) {
            return storySet;
        }
        return storySet;
    }

    @Override
    public boolean updateStorySet(StorySet storySet) {
        return storySetDao.updateStorySet(storySet);
    }

    @Override
    public boolean deleteStorySet(int id) {
        return storySetDao.deleteStorySet(id);
    }

    @Override
    public StorySet getStorySetById(int id) {
        return storySetDao.getStorySetById(id);
    }

    @Override
    public List<StorySet> getAllStorySetByPage(int page, int pageSize) {
        int offset = page*pageSize;
        int limit = pageSize;
        return storySetDao.getAllStorySetByPage(offset, limit);
    }

    @Override
    public StorySet getStorySetByStoryId(int storyId) {
        int id = storySetDao.getStorySetIdByStoryId(storyId);
        return id == 0 ? null : storySetDao.getStorySetById(id);
    }
}
