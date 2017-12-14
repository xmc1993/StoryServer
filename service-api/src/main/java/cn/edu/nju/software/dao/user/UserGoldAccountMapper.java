package cn.edu.nju.software.dao.user;

import cn.edu.nju.software.entity.UserGoldAccount;
import cn.edu.nju.software.entity.UserGoldAccountExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserGoldAccountMapper {
    long countByExample(UserGoldAccountExample example);

    int deleteByExample(UserGoldAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserGoldAccount record);

    int insertSelective(UserGoldAccount record);

    List<UserGoldAccount> selectByExample(UserGoldAccountExample example);

    UserGoldAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserGoldAccount record, @Param("example") UserGoldAccountExample example);

    int updateByExample(@Param("record") UserGoldAccount record, @Param("example") UserGoldAccountExample example);

    int updateByPrimaryKeySelective(UserGoldAccount record);

    int updateByPrimaryKey(UserGoldAccount record);
}