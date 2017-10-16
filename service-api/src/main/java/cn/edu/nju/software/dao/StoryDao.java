package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.vo.StoryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface StoryDao {

    boolean saveStory(Story story);

    /**
     * 软删除
     * @param id
     * @return
     */
    boolean deleteStoryById(int id);

    Story getStoryById(int id);

    Story getStoryByIdHard(int id);

    List<Story> getAllStories();

    List<Story> getAllStoriesIncludeDrafts();

    boolean updateStory(Story story);

    boolean deleteHard(int id);

    List<Story> getStoryListByTitle(String title, int offset, int limit);

    List<Story> getStoryListByPage(int offset, int limit,@Param("sortByCreateTime") String sortByCreateTime);

    List<Story> getSetStoryListByPage(int offset, int limit,@Param("sortByCreateTime") String sortByCreateTime);

    List<Story> getStoryListByIdList(@Param("idList") List<Integer> idList, @Param("offset")Integer offset,@Param("limit") Integer limit);

    boolean recommendStory(int id);

    boolean cancelRecommendStory(int id);

    List<Story> getRecommendedStoryListByPage(int offset, int limit);

    List<Story> getSetRecommendedStoryListByPage(int offset, int limit);

    boolean newTell(int id);

    boolean deleteTell(int id);

    Integer getStoryCount();

    Integer getSetStoryCount();

    Integer getStoryCountByTitle(String title);

    Integer getStoryIdCountByTagIdList(@Param("idList")List<Integer> idList);

    Integer getRecommendedStoryCount();

    Integer getSetRecommendedStoryCount();

    Integer getStoryCountByIdList(@Param("idList") List<Integer> idList);

    Integer getSetStoryCountByIdList(@Param("idList") List<Integer> idList);

    List<Story> getStoryByFuzzyQuery(@Param("queryList") List<String> queryList, @Param("offset")Integer offset, @Param("limit")Integer limit);

    Integer getStoryCountByFuzzyQuery(@Param("queryList") List<String> queryList);

    int addStoryLikeCount(int storyId);

    int delStoryLikeCount(int storyId);


    List<Story> getStoryListByClassifyFuzzyQueryIncludeDrafts(@Param("title") String title, @Param("author") String author, @Param("content") String content,
                                                              @Param("press") String press, @Param("tag") Integer tag, @Param("offset") Integer offset,
                                                              @Param("limit") Integer limit);

    Integer getStoryCountByClassifyFuzzyQueryIncludeDrafts(@Param("title") String title, @Param("author") String author, @Param("content") String content,
                                                           @Param("press") String press, @Param("tag") Integer tag);

    List<Story> getDraftList(Integer offset,Integer limit);

    Integer getDraftCount();

    boolean setDraftCompleteByStoryId(@Param("storyId") Integer storyId);

    List<Story> getStoryListByPageIncludeDrafts(int offset, int limit);

    Integer getStoryCountIncludeDrafts();

    Story getStoryByIdIncludeDrafts(int id);

    Integer getStoryCountByTitleIncludeDrafts(String query);

    Story getExactStoryByTitle(@Param("title") String title);

    List<StoryVo> getStoryVoByIdList(@Param("idList") List<Integer> idList);


    List<Integer> getRecommendedStoryIdListByPage(@Param("offset") int offset, @Param("limit") int limit);

	List<Story> getStoryListByIdListByPage(@Param("storyIdList")List<Integer> storyIdList);
	
	String getStoryNameById(int id);

	List<Story> getSetStoryListByIdListByPage(@Param("storyIdList")List<Integer> storyIdList);

    List<Story> getStoryListBySetId(int setId, int offset, int limit);

    Integer getStoryCountBySetId(int setId);


    List<Story> getSetStoryListByIdList(@Param("idList") List<Integer> idList,@Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Story> getStoryListBySecondLevelTagId(@Param("tagId")Integer tagId, @Param("offset")Integer offset, @Param("limit")int limit);

    List<Story> getPopularOriginalStoryListByPage(@Param("offset")Integer offset, @Param("limit")int limit);

    Integer getStoryCountBySecondLevelTagId(
            @Param("tagId")Integer tagId);
    
    List<Story> getStoryListByReadLog();

    List<Story> getMostPopularStoryByPage(int offset, int limit);
}
