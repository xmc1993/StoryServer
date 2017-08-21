package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.PowerCode;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface PowerCodeService {

    PowerCode savePowerCode(PowerCode powerCode);

    boolean updatePowerCode(PowerCode powerCode);

    boolean deletePowerCode(int id);

    PowerCode getPowerCodeById(int id);

    List<PowerCode> getPowerCodeListByPage(int page, int pageSize);

}
