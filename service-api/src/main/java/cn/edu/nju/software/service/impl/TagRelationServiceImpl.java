package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.StoryTagDao;
import cn.edu.nju.software.dao.TagRelationDao;
import cn.edu.nju.software.entity.StoryTag;
import cn.edu.nju.software.entity.TagRelation;
import cn.edu.nju.software.service.TagRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class TagRelationServiceImpl implements TagRelationService {

    @Autowired
    private TagRelationDao tagRelationDao;
    @Autowired
    private StoryTagDao storyTagDao;

    @Override
    public boolean saveTagRelation(TagRelation tagRelation) {
        return tagRelationDao.saveTagRelation(tagRelation);
    }

    @Override
    public boolean deleteTagRelationById(int id) {
        return tagRelationDao.deleteTagRelationById(id);
    }

    @Override
    public boolean deleteTagRelationByStoryIdAndTagId(int storyId, int tagId) {
        return tagRelationDao.deleteTagRelationByStoryIdAndTagId(storyId, tagId);
    }

    @Override
    public List<Integer> getStoryIdListByTagId(int tagId) {
        return tagRelationDao.getStoryIdListByTagId(tagId);
    }

    @Override
    public List<Integer> getTagIdListByStoryId(int storyId) {
        return tagRelationDao.getTagIdListByStoryId(storyId);
    }

    @Override
    public List<Integer> getStoryIdListByTagIdList(List<Integer> tagIds) {
        tagIds.add(-1);
        return tagRelationDao.getStoryIdListByTagIdList(tagIds);
    }

    @Override
    public List<Integer> getStoryIdListByFirstLevelTagId(int tagId) {
        List<StoryTag> childTags = storyTagDao.getStoryTagListByParentId(tagId);
        ArrayList<Integer> tagIdList = new ArrayList<>();
        tagIdList.add(tagId);
        for (StoryTag childTag : childTags) {
            tagIdList.add(childTag.getId());
        }
        return tagRelationDao.getStoryIdListByTagIdList(tagIdList);
    }

    @Override
    public List<Integer> getStoryIdListBySecondLevelTagId(int tagId) {
        return tagRelationDao.getStoryIdListByTagId(tagId);
    }

	@Override
	public boolean deleteTagRelationsByStoryId(int storyId) {
		return tagRelationDao.deleteTagRelationsByStoryId(storyId);
	}

	@Override
	public List<Integer> getStoryIdListByTagIds(List<Integer> tagIds, Integer size) {
		//由于sql语句使用的是大于号这里减一
		return tagRelationDao.getStoryIdListByTagIds(tagIds, size-1);
	}

}
