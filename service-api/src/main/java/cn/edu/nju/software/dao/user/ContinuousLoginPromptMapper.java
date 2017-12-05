package cn.edu.nju.software.dao.user;

import cn.edu.nju.software.entity.ContinuousLoginPrompt;
import cn.edu.nju.software.entity.ContinuousLoginPromptExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ContinuousLoginPromptMapper {
    int countByExample(ContinuousLoginPromptExample example);

    int deleteByExample(ContinuousLoginPromptExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ContinuousLoginPrompt record);

    int insertSelective(ContinuousLoginPrompt record);

    List<ContinuousLoginPrompt> selectByExample(ContinuousLoginPromptExample example);

    ContinuousLoginPrompt selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ContinuousLoginPrompt record, @Param("example") ContinuousLoginPromptExample example);

    int updateByExample(@Param("record") ContinuousLoginPrompt record, @Param("example") ContinuousLoginPromptExample example);

    int updateByPrimaryKeySelective(ContinuousLoginPrompt record);

    int updateByPrimaryKey(ContinuousLoginPrompt record);
}