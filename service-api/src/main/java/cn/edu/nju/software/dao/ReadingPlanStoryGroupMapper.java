package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.ReadingPlanStoryGroup;
import cn.edu.nju.software.entity.ReadingPlanStoryGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface ReadingPlanStoryGroupMapper {
    int countByExample(ReadingPlanStoryGroupExample example);

    int deleteByExample(ReadingPlanStoryGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ReadingPlanStoryGroup record);

    int insertSelective(ReadingPlanStoryGroup record);

    List<ReadingPlanStoryGroup> selectByExample(ReadingPlanStoryGroupExample example);

    ReadingPlanStoryGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ReadingPlanStoryGroup record, @Param("example") ReadingPlanStoryGroupExample example);

    int updateByExample(@Param("record") ReadingPlanStoryGroup record, @Param("example") ReadingPlanStoryGroupExample example);

    int updateByPrimaryKeySelective(ReadingPlanStoryGroup record);

    int updateByPrimaryKey(ReadingPlanStoryGroup record);

    List<Integer> getStoryIdListInReadPlanByPlanId(Integer planId);
}