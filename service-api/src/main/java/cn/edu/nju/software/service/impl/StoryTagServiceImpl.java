package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.StoryTagDao;
import cn.edu.nju.software.entity.StoryTag;
import cn.edu.nju.software.service.StoryTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class StoryTagServiceImpl implements StoryTagService {

    @Autowired
    private StoryTagDao storyTagDao;

    @Override
    public boolean saveStoryTag(StoryTag storyTag) {
        return storyTagDao.saveStoryTag(storyTag);
    }

    @Override
    public boolean updateStoryTag(StoryTag storyTag) {
        return storyTagDao.updateStoryTag(storyTag);
    }

    @Override
    public boolean deleteStoryTag(int tagId) {
        return storyTagDao.deleteStoryTagById(tagId);
    }

    @Override
    public StoryTag getStoryTagById(int tagId) {
        return storyTagDao.getStoryTagById(tagId);
    }

    @Override
    public List<StoryTag> getStoryTagListByParentId(int parentId) {
        return storyTagDao.getStoryTagListByParentId(parentId);
    }

    @Override
    public List<StoryTag> getAllStoryTags() {
        return storyTagDao.getAllStoryTags();
    }
}
