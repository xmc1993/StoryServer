package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Admin;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/16.
 */
public interface AdminService {

    Admin getAdminByUsername(String username);

    Admin getAdminByAccessToken(String accessToken);

    boolean updateAccessToken(Admin admin);

    boolean saveAdmin(Admin admin);

    boolean deleteAdmin(int id);

    List<Admin> getAdminListByPage(int page, int pageSize);
    
    int getAdminCount();

}
