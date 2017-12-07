package cn.edu.nju.software.dao.user;

import cn.edu.nju.software.entity.WorkTagRelation;
import cn.edu.nju.software.entity.WorkTagRelationExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WorkTagRelationMapper {
    int countByExample(WorkTagRelationExample example);

    int deleteByExample(WorkTagRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WorkTagRelation record);

    int insertSelective(WorkTagRelation record);

    List<WorkTagRelation> selectByExample(WorkTagRelationExample example);

    WorkTagRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WorkTagRelation record, @Param("example") WorkTagRelationExample example);

    int updateByExample(@Param("record") WorkTagRelation record, @Param("example") WorkTagRelationExample example);

    int updateByPrimaryKeySelective(WorkTagRelation record);

    int updateByPrimaryKey(WorkTagRelation record);
}