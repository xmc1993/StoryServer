package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.BabyReadInfo;
import cn.edu.nju.software.entity.BabyReadInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BabyReadInfoMapper {
    int countByExample(BabyReadInfoExample example);

    int deleteByExample(BabyReadInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BabyReadInfo record);

    int insertSelective(BabyReadInfo record);

    List<BabyReadInfo> selectByExampleWithBLOBs(BabyReadInfoExample example);

    List<BabyReadInfo> selectByExample(BabyReadInfoExample example);

    BabyReadInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BabyReadInfo record, @Param("example") BabyReadInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") BabyReadInfo record, @Param("example") BabyReadInfoExample example);

    int updateByExample(@Param("record") BabyReadInfo record, @Param("example") BabyReadInfoExample example);

    int updateByPrimaryKeySelective(BabyReadInfo record);

    int updateByPrimaryKeyWithBLOBs(BabyReadInfo record);

    int updateByPrimaryKey(BabyReadInfo record);
}