package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.StoryDao;
import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class StoryServiceImpl implements StoryService {

    @Autowired
    private StoryDao storyDao;

    @Override
    public boolean saveStory(Story story) {
        return storyDao.saveStory(story);
    }

    @Override
    public boolean deleteStoryById(int id) {
        return storyDao.deleteStoryById(id);
    }

    @Override
    public Story getStoryById(int id) {
        return storyDao.getStoryById(id);
    }

    @Override
    public List<Story> getAllStories() {
        return storyDao.getAllStories();
    }

    @Override
    public boolean updateStory(Story story) {
        return storyDao.updateStory(story);
    }
}
