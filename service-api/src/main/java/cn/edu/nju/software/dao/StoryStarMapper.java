package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.StoryStar;
import cn.edu.nju.software.entity.StoryStarExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryStarMapper {
    int countByExample(StoryStarExample example);

    int deleteByExample(StoryStarExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StoryStar record);

    int insertSelective(StoryStar record);

    List<StoryStar> selectByExample(StoryStarExample example);

    StoryStar selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StoryStar record, @Param("example") StoryStarExample example);

    int updateByExample(@Param("record") StoryStar record, @Param("example") StoryStarExample example);

    int updateByPrimaryKeySelective(StoryStar record);

    int updateByPrimaryKey(StoryStar record);
}