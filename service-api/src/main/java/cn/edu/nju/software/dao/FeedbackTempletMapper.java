package cn.edu.nju.software.dao;

import cn.edu.nju.software.dto.DestinationVo;
import cn.edu.nju.software.entity.FeedbackTemplet;
import cn.edu.nju.software.entity.FeedbackTempletExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FeedbackTempletMapper {
    int countByExample(FeedbackTempletExample example);

    int deleteByExample(FeedbackTempletExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FeedbackTemplet record);

    int insertSelective(FeedbackTemplet record);

    List<FeedbackTemplet> selectByExample(FeedbackTempletExample example);

    FeedbackTemplet selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FeedbackTemplet record, @Param("example") FeedbackTempletExample example);

    int updateByExample(@Param("record") FeedbackTemplet record, @Param("example") FeedbackTempletExample example);

    int updateByPrimaryKeySelective(FeedbackTemplet record);

    int updateByPrimaryKey(FeedbackTemplet record);

    List<DestinationVo> getAllFeedbackDescription();
}