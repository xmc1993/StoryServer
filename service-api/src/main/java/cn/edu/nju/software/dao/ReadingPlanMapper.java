package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.ReadingPlan;
import cn.edu.nju.software.entity.ReadingPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingPlanMapper {
    int countByExample(ReadingPlanExample example);

    int deleteByExample(ReadingPlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ReadingPlan record);

    int insertSelective(ReadingPlan record);

    List<ReadingPlan> selectByExampleWithBLOBs(ReadingPlanExample example);

    List<ReadingPlan> selectByExample(ReadingPlanExample example);

    ReadingPlan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ReadingPlan record, @Param("example") ReadingPlanExample example);

    int updateByExampleWithBLOBs(@Param("record") ReadingPlan record, @Param("example") ReadingPlanExample example);

    int updateByExample(@Param("record") ReadingPlan record, @Param("example") ReadingPlanExample example);

    int updateByPrimaryKeySelective(ReadingPlan record);

    int updateByPrimaryKeyWithBLOBs(ReadingPlan record);

    int updateByPrimaryKey(ReadingPlan record);
}