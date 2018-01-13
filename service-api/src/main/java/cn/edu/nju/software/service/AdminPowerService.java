package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.AdminPower;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface AdminPowerService {

    AdminPower saveAdminPower(AdminPower adminPower);

    boolean deleteAdminPower(int id, int adminId);

    boolean deleteAdminPowerWithPrimaryKey(int adminId, int code);

    AdminPower getAdminPowerById(int id);

    List<AdminPower> getAdminPowerListByAdminId(int id);

    List<Integer> getAdminPowerCodeListByAdminId(int id);
    
    List<AdminPower> getAdminPowerListByAdminId(int id,int page,int pageSize);

}
