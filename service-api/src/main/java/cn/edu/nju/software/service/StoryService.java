package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Story;

import java.io.File;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface StoryService {
    boolean saveStory(Story story);

    boolean deleteStoryById(int id);

    Story getStoryById(int id);

    Story getStoryByIdIncludeDrafts(int id);

    Story getStoryByIdHard(int id);

    List<Story> getAllStories();

    Story updateStory(Story story);

    List<Story> getStoryListByPage(int offset, int limit);

    List<Story> getStoryListByPageIncludeDrafts(int offset, int limit);

    List<Story> getStoryListByIdList(List<Integer> idList, Integer offset, Integer limit);

    List<Story> getStoryListByTitle(String title, int offset, int limit);

    boolean recommendStory(int id);

    boolean cancelRecommendStory(int id);

    List<Story> getRecommendedStoryListByPage(int offset, int limit);

    Integer getRecommendedStoryCount();

    boolean newTell(int id);

    boolean deleteTell(int id);

    Integer getStoryCount();

    Integer getStoryCountIncludeDrafts();

    String getOriginSoundLength(File file);

    Integer getStoryCountByTitle(String query);

    Integer getStoryCountByTitleIncludeDrafts(String query);

    Integer getStoryCountByIdList(List<Integer> idList);

    List <Story>  getStoryByFuzzyQuery(String query, Integer offset, Integer limit);

    List<Story> getStoryByClassifyFuzzyQueryInludeDrafts(String title, String author, String content, String press, String tag, Integer offset, Integer limit);

    Integer getStoryCountByClassifyFuzzyQueryIncludeDrafts(String title, String author, String content, String press, String tag);

    boolean setDraftComplete(Integer storyId);

    Integer getDraftCount();

    List<Story> getDraftList(Integer offset, Integer limit);
}
