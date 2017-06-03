package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.StoryDao;
import cn.edu.nju.software.dao.WorksDao;
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
    @Autowired
    private WorksDao worksDao;

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
        Story _story = storyDao.getStoryById(story.getId());
        boolean res = storyDao.updateStory(story);
        if (!res) {
            return null;
        }
        //如果故事的名字发生改变，那么更新冗余字段
        if (!_story.getTitle().equals(story.getTitle())) {
            worksDao.updateStoryTitle(story.getId(), story.getTitle());
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
        return storyDao.getStoryListByTitle(title, offset, limit);
    }

    @Override
    public boolean recommendStory(int id) {
        return storyDao.recommendStory(id);
    }

    @Override
    public boolean cancelRecommendStory(int id) {
        return storyDao.cancelRecommendStory(id);
    }

    @Override
    public List<Story> getRecommendedStoryListByPage(int offset, int limit) {
        return storyDao.getRecommendedStoryListByPage(offset, limit);
    }
}
