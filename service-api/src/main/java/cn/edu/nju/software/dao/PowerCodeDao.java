package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.PowerCode;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface PowerCodeDao {

    boolean savePowerCode(PowerCode powerCode);

    boolean updatePowerCode(PowerCode powerCode);

    boolean deletePowerCode(int id);

    PowerCode getPowerCodeById(int id);

    List<PowerCode> getPowerCodeListByPage(int offset, int limit);

}
