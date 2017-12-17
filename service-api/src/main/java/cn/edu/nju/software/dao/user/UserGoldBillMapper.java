package cn.edu.nju.software.dao.user;

import cn.edu.nju.software.entity.UserGoldBill;
import cn.edu.nju.software.entity.UserGoldBillExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserGoldBillMapper {
    long countByExample(UserGoldBillExample example);

    int deleteByExample(UserGoldBillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserGoldBill record);

    int insertSelective(UserGoldBill record);

    List<UserGoldBill> selectByExample(UserGoldBillExample example);

    UserGoldBill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserGoldBill record, @Param("example") UserGoldBillExample example);

    int updateByExample(@Param("record") UserGoldBill record, @Param("example") UserGoldBillExample example);

    int updateByPrimaryKeySelective(UserGoldBill record);

    int updateByPrimaryKey(UserGoldBill record);
}