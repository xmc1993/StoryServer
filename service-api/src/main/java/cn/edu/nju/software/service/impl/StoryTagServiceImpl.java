package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.StoryTagDao;
import cn.edu.nju.software.entity.StoryTag;
import cn.edu.nju.software.service.StoryTagService;
import cn.edu.nju.software.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class StoryTagServiceImpl implements StoryTagService {

    @Autowired
    private StoryTagDao storyTagDao;

    @Override
    public int saveStoryTag(StoryTag storyTag) {
        return storyTagDao.saveStoryTag(storyTag);
    }

    @Override
    public StoryTag updateStoryTag(StoryTag storyTag) {
        storyTag.setUpdateTime(new Date());
        boolean res = storyTagDao.updateStoryTag(storyTag);
        return res ? storyTagDao.getStoryTagById(storyTag.getId()) : null;
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
    public List<StoryTag> getStoryTagListByIdList(List<Integer> idList) {
        idList.add(-1);
        storyTagDao
    }

    @Override
    public List<StoryTag> getAllStoryTags() {
        return storyTagDao.getAllStoryTags();
    }

    @Override
    public List<StoryTag> getStoryTagsByPage(int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return storyTagDao.getStoryTagListByPage(offset, limit);
    }

    @Override
    public StoryTag getStoryTagByIdHard(int tagId) {
        return storyTagDao.getStoryTagByIdHard(tagId);
    }
}
