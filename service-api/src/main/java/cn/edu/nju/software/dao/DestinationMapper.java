package cn.edu.nju.software.dao;

import cn.edu.nju.software.dto.DestinationVo;
import cn.edu.nju.software.entity.Destination;
import cn.edu.nju.software.entity.DestinationExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationMapper {
    int countByExample(DestinationExample example);

    int deleteByExample(DestinationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Destination record);

    int insertSelective(Destination record);

    List<Destination> selectByExample(DestinationExample example);

    Destination selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Destination record, @Param("example") DestinationExample example);

    int updateByExample(@Param("record") Destination record, @Param("example") DestinationExample example);

    int updateByPrimaryKeySelective(Destination record);

    int updateByPrimaryKey(Destination record);

    List<DestinationVo> getAllDestinationDescription();
}