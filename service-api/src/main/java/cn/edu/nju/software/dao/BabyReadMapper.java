package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.BabyRead;
import cn.edu.nju.software.entity.BabyReadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BabyReadMapper {
    int countByExample(BabyReadExample example);

    int deleteByExample(BabyReadExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BabyRead record);

    int insertSelective(BabyRead record);

    List<BabyRead> selectByExample(BabyReadExample example);

    BabyRead selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BabyRead record, @Param("example") BabyReadExample example);

    int updateByExample(@Param("record") BabyRead record, @Param("example") BabyReadExample example);

    int updateByPrimaryKeySelective(BabyRead record);

    int updateByPrimaryKey(BabyRead record);
}