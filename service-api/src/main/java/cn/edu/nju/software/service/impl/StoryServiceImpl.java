package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.StoryDao;
import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.service.StoryService;
import cn.edu.nju.software.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public Story getStoryByIdHard(int id) {
        return storyDao.getStoryByIdHard(id);
    }

    @Override
    public List<Story> getAllStories() {
        return storyDao.getAllStories();
    }

    @Override
    public Story updateStory(Story story) {
        story.setUpdateTime(new Date());
        boolean res = storyDao.updateStory(story);
        if (!res) {
            return null;
        }
        return storyDao.getStoryById(story.getId());

    }

    @Override
    public List<Story> getStoryListByPage(int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return storyDao.getStoryListByPage(offset, limit);
    }

    @Override
    public List<Story> getStoryListByIdList(List<Integer> idList) {
        idList.add(-1);//防止mybatis查询出错
        return storyDao.getStoryListByIdList(idList);
    }

    @Override
    public List<Story> getStoryListByTitle(String title, int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        //TODO 交给sql做
        title = "%" + title + "%";
        return storyDao.getStoryListByTitle(title, offset, limit);
    }
}
