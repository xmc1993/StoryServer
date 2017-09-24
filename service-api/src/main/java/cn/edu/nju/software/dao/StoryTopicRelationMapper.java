package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.StoryTopicRelation;
import cn.edu.nju.software.entity.StoryTopicRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryTopicRelationMapper {
    int countByExample(StoryTopicRelationExample example);

    int deleteByExample(StoryTopicRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StoryTopicRelation record);

    int insertSelective(StoryTopicRelation record);

    List<StoryTopicRelation> selectByExample(StoryTopicRelationExample example);

    StoryTopicRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StoryTopicRelation record, @Param("example") StoryTopicRelationExample example);

    int updateByExample(@Param("record") StoryTopicRelation record, @Param("example") StoryTopicRelationExample example);

    int updateByPrimaryKeySelective(StoryTopicRelation record);

    int updateByPrimaryKey(StoryTopicRelation record);
}