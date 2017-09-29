package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.vo.StoryVo;
import com.github.pagehelper.PageInfo;

import java.io.File;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface StoryService {
    Story saveStory(Story story);

    boolean deleteStoryById(int id);

    Story getStoryById(int id);

    Story getStoryByIdIncludeDrafts(int id);

    Story getStoryByIdHard(int id);

    List<Story> getAllStories();

    Story updateStory(Story story);

    List<Story> getStoryListByPage(int offset, int limit, String sortByCreateTime);

    List<Story> getSetStoryListByPage(int offset, int limit, String sortByCreateTime);

    List<Story> getStoryListByPageIncludeDrafts(int offset, int limit);

    List<Story> getStoryListByIdList(List<Integer> idList, Integer offset, Integer limit);

    List<Story> getStoryListByTitle(String title, int offset, int limit);

    boolean recommendStory(int id);

    boolean cancelRecommendStory(int id);

    List<Story> getRecommendedStoryListByPage(int offset, int limit);

    List<Story> getSetRecommendedStoryListByPage(int offset, int limit);

    Integer getRecommendedStoryCount();

    Integer getSetRecommendedStoryCount();

    boolean newTell(int id);

    boolean deleteTell(int id);

    Integer getStoryCount();

    Integer getSetStoryCount();

    Integer getStoryCountIncludeDrafts();

    String getOriginSoundLength(File file);

    Integer getStoryCountByTitle(String query);

    Integer getStoryCountByTitleIncludeDrafts(String query);

    Integer getStoryCountByIdList(List<Integer> idList);

    Integer getSetStoryCountByIdList(List<Integer> idList);

    List<Story> getStoryByFuzzyQuery(String query, Integer offset, Integer limit);

    List<Story> getStoryByClassifyFuzzyQueryIncludeDrafts(String title, String author, String content, String press, Integer tag, Integer offset, Integer limit);

    Integer getStoryCountByClassifyFuzzyQueryIncludeDrafts(String title, String author, String content, String press, Integer tag);

    boolean setDraftComplete(Integer storyId);

    Integer getDraftCount();

    List<Story> getDraftList(Integer offset, Integer limit);

    Integer getStoryCountByFuzzyQuery(String query);

    Story getExactStoryByTitle(String title);

    List<StoryVo> getRecommendedStoryVoList(int offset, int limit);

    List<Story> getRecommendStoryListByPage(int offset, int limit);

    PageInfo<Story> getStoryListByIdListByPage(List<Integer> storyIdList, Integer page, Integer pageSize);

    PageInfo<Story> getSetStoryListByIdListByPage(List<Integer> storyIdList, Integer page, Integer pageSize);

    List<Story> getStoryListBySetId(int setId, int page, int pageSize);

    Integer getStoryCountBySetId(int setId);

    List<Story> getSetStoryListByIdList(List<Integer> idList, Integer offset, Integer limit);

    List<Story> getStoryListBySecondLevelTagId(
            Integer tagId, Integer offset, Integer limit);

    Integer getStoryCountBySecondLevelTagId(
            Integer tagId);

    List<Story> getPopularOriginalStoryListByPage(Integer page,  int pageSize);
    
    List<Story> getStoryListByReadLog(Integer storyId);
    
    List<Story> getMostPopularStoryByPage(int page, int pageSize);

}
