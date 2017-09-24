package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.StoryTopic;
import cn.edu.nju.software.entity.StoryTopicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryTopicMapper {
    int countByExample(StoryTopicExample example);

    int deleteByExample(StoryTopicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StoryTopic record);

    int insertSelective(StoryTopic record);

    List<StoryTopic> selectByExampleWithBLOBs(StoryTopicExample example);

    List<StoryTopic> selectByExample(StoryTopicExample example);

    StoryTopic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StoryTopic record, @Param("example") StoryTopicExample example);

    int updateByExampleWithBLOBs(@Param("record") StoryTopic record, @Param("example") StoryTopicExample example);

    int updateByExample(@Param("record") StoryTopic record, @Param("example") StoryTopicExample example);

    int updateByPrimaryKeySelective(StoryTopic record);

    int updateByPrimaryKeyWithBLOBs(StoryTopic record);

    int updateByPrimaryKey(StoryTopic record);
}