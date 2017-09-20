package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.StoryScript;
import cn.edu.nju.software.entity.StoryScriptExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StoryScriptMapper {
    int countByExample(StoryScriptExample example);

    int deleteByExample(StoryScriptExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StoryScript record);

    int insertSelective(StoryScript record);

    List<StoryScript> selectByExampleWithBLOBs(StoryScriptExample example);

    List<StoryScript> selectByExample(StoryScriptExample example);

    StoryScript selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StoryScript record, @Param("example") StoryScriptExample example);

    int updateByExampleWithBLOBs(@Param("record") StoryScript record, @Param("example") StoryScriptExample example);

    int updateByExample(@Param("record") StoryScript record, @Param("example") StoryScriptExample example);

    int updateByPrimaryKeySelective(StoryScript record);

    int updateByPrimaryKeyWithBLOBs(StoryScript record);

    int updateByPrimaryKey(StoryScript record);
}