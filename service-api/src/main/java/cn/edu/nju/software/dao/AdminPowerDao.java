package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.AdminPower;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface AdminPowerDao {

    boolean saveAdminPower(AdminPower adminPower);

    boolean deleteAdminPower(int id);

    AdminPower getAdminPowerById(int id);

    List<AdminPower> getAdminPowerListAdminId(int id);

    boolean deleteAdminPowerWithPrimaryKey(int adminId, int code);
    
    List<AdminPower> getAdminPowerListByAdminId(int id,int offset, int limit);

}
