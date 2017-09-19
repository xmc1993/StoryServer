package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.StoryAmbitus;
import cn.edu.nju.software.entity.StoryAmbitusExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryAmbitusMapper {
    int countByExample(StoryAmbitusExample example);

    int deleteByExample(StoryAmbitusExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StoryAmbitus record);

    int insertSelective(StoryAmbitus record);

    List<StoryAmbitus> selectByExampleWithBLOBs(StoryAmbitusExample example);

    List<StoryAmbitus> selectByExample(StoryAmbitusExample example);

    StoryAmbitus selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StoryAmbitus record, @Param("example") StoryAmbitusExample example);

    int updateByExampleWithBLOBs(@Param("record") StoryAmbitus record, @Param("example") StoryAmbitusExample example);

    int updateByExample(@Param("record") StoryAmbitus record, @Param("example") StoryAmbitusExample example);

    int updateByPrimaryKeySelective(StoryAmbitus record);

    int updateByPrimaryKeyWithBLOBs(StoryAmbitus record);

    int updateByPrimaryKey(StoryAmbitus record);
}