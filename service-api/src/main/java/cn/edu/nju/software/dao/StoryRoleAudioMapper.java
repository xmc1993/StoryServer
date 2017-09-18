package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.StoryRoleAudio;
import cn.edu.nju.software.entity.StoryRoleAudioExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRoleAudioMapper {
    int countByExample(StoryRoleAudioExample example);

    int deleteByExample(StoryRoleAudioExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StoryRoleAudio record);

    int insertSelective(StoryRoleAudio record);

    List<StoryRoleAudio> selectByExample(StoryRoleAudioExample example);

    StoryRoleAudio selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StoryRoleAudio record, @Param("example") StoryRoleAudioExample example);

    int updateByExample(@Param("record") StoryRoleAudio record, @Param("example") StoryRoleAudioExample example);

    int updateByPrimaryKeySelective(StoryRoleAudio record);

    int updateByPrimaryKey(StoryRoleAudio record);
}