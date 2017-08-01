package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.StoryDao;
import cn.edu.nju.software.dao.UserDao;
import cn.edu.nju.software.dao.UserRelationStoryDao;
import cn.edu.nju.software.dao.WorksDao;
import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.service.StoryService;
import cn.edu.nju.software.util.Const;
import cn.edu.nju.software.vo.StoryVo;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
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
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRelationStoryDao userRelationStoryDao;

    @Override
    public Story saveStory(Story story) {
        boolean res = storyDao.saveStory(story);
        if (res) {
            return story;
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteStoryById(int id) {
        List<Integer> userIdList = userRelationStoryDao.getUserIdListByStoryId(id);
        for (Integer userId : userIdList) {
            userDao.delLikeStoryCount(userId);
            userRelationStoryDao.delete(id, userId);
        }
        return storyDao.deleteStoryById(id);
    }

    @Override
    public Story getStoryById(int id) {
        return storyDao.getStoryById(id);
    }

    @Override
    public Story getStoryByIdIncludeDrafts(int id) {
        return storyDao.getStoryByIdIncludeDrafts(id);
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
        if (_story.getTitle() != null) {
            if (!_story.getTitle().equals(story.getTitle())) {
                worksDao.updateStoryTitle(story.getId(), story.getTitle());
            }
        }
        if (_story.getCoverUrl() != null) {
            if (!_story.getCoverUrl().equals(story.getCoverUrl())) {
                worksDao.updateCoverUrl(story.getId(), story.getCoverUrl());
            }
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
    public List<Story> getStoryListByPageIncludeDrafts(int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return storyDao.getStoryListByPageIncludeDrafts(offset, limit);
    }

    @Override
    public List<Story> getStoryListByIdList(List<Integer> idList, Integer offset, Integer limit) {
        idList.add(-1);//防止mybatis查询出错
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return storyDao.getStoryListByIdList(idList, offset, limit);
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

    @Override
    public Integer getRecommendedStoryCount() {
        return storyDao.getRecommendedStoryCount();
    }

    @Override
    public boolean newTell(int id) {
        return storyDao.newTell(id);
    }

    @Override
    public boolean deleteTell(int id) {
        return storyDao.deleteTell(id);
    }

    @Override
    public Integer getStoryCount() {
        return storyDao.getStoryCount();
    }

    @Override
    public Integer getStoryCountIncludeDrafts() {
        return storyDao.getStoryCountIncludeDrafts();
    }

    @Override
    public String getOriginSoundLength(File file) {
        Encoder encoder = new Encoder();
        MultimediaInfo m = null;
        try {
            m = encoder.getInfo(file);
            long length = m.getDuration() / 1000;
            int hours = (int) (length / 3600);
            int minutes = (int) ((length % 3600) / 60);
            int seconds = (int) (length % 60);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(hours == 0 ? "0" : String.valueOf(hours));
            stringBuilder.append(":");
            stringBuilder.append(minutes == 0 ? "0" : String.valueOf(minutes));
            stringBuilder.append(":");
            stringBuilder.append(seconds == 0 ? "0" : String.valueOf(seconds));
            return stringBuilder.toString();
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getStoryCountByTitle(String query) {
        return storyDao.getStoryCountByTitle(query);
    }

    @Override
    public Integer getStoryCountByTitleIncludeDrafts(String query) {
        return storyDao.getStoryCountByTitleIncludeDrafts(query);
    }

    @Override
    public Integer getStoryCountByIdList(List<Integer> idList) {
        idList.add(-1);//防止mybatis查询出错
        return storyDao.getStoryCountByIdList(idList);
    }

    @Override
    public List<Story> getStoryByFuzzyQuery(String query, Integer offset, Integer limit) {
        if (query != null && query.trim() == "") return null;
        String[] queries = query.split(" ");
        List<String> queryList = new ArrayList<String>();
        for (String temp : queries) {
            if (!temp.trim().equals("")) queryList.add(temp.trim());
        }
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return storyDao.getStoryByFuzzyQuery(queryList, offset, limit);
    }

    @Override
    public List<Story> getStoryByClassifyFuzzyQueryIncludeDrafts(String title, String author, String content, String press, String tag, Integer offset, Integer limit) {
        if (title != null && title.trim().equals("")) title = null;
        else if (title != null) title = title.trim();
        if (author != null && author.trim().equals("")) author = null;
        else if (author != null) author = author.trim();
        if (content != null && content.trim().equals("")) content = null;
        else if (content != null) content = content.trim();
        if (press != null && press.trim().equals("")) press = null;
        else if (press != null) press = press.trim();
        if (tag != null && tag.trim().equals("")) tag = null;
        else if (tag != null) tag = tag.trim();
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return storyDao.getStoryListByClassifyFuzzyQueryIncludeDrafts(
                title, author, content, press, tag, offset, limit);
    }

    @Override
    public Integer getStoryCountByClassifyFuzzyQueryIncludeDrafts(String title, String author, String content, String press, String tag) {
        if (title != null && title.trim().equals("")) title = null;
        else if (title != null) title = title.trim();
        if (author != null && author.trim().equals("")) author = null;
        else if (author != null) author = author.trim();
        if (content != null && content.trim().equals("")) content = null;
        else if (content != null) content = content.trim();
        if (press != null && press.trim().equals("")) press = null;
        else if (press != null) press = press.trim();
        if (tag != null && tag.trim().equals("")) tag = null;
        else if (tag != null) tag = tag.trim();
        return storyDao.getStoryCountByClassifyFuzzyQueryIncludeDrafts(title, author, content, press, tag);
    }

    @Override
    public boolean setDraftComplete(Integer storyId) {
        if (storyDao.getStoryById(storyId).getDraft() == 0) return false;
        return storyDao.setDraftCompleteByStoryId(storyId);
    }

    @Override
    public Integer getDraftCount() {
        return storyDao.getDraftCount();
    }

    @Override
    public List<Story> getDraftList(Integer offset, Integer limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return storyDao.getDraftList(offset, limit);
    }

    @Override
    public Integer getStoryCountByFuzzyQuery(String query) {
        if (query != null && query.trim().equals("")) return null;
        String[] queries = query.split(" ");
        List<String> queryList = new ArrayList<String>();
        for (String temp : queries) {
            if (!temp.trim().equals("")) queryList.add(temp.trim());
        }
        return storyDao.getStoryCountByFuzzyQuery(queryList);
    }

    @Override
    public Story getExactStoryByTitle(String title) {
        return storyDao.getExactStoryByTitle(title);
    }

    @Override
    public List<StoryVo> getRecommendedStoryVoList(int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        List<Integer> idList = storyDao.getRecommendedStoryIdListByPage(offset, limit);
        if (idList == null) return null;
        List<StoryVo> storyVoList = storyDao.getStoryVoByIdList(idList);
        return storyVoList;
    }

    @Override
    public List<Story> getRecommendStoryListByPage(int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        List<Story> storyList = storyDao.getRecommendedStoryListByPage(offset, limit);
        return storyList;
    }
}
