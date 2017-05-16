package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Admin;

/**
 * Created by xmc1993 on 2017/5/16.
 */
public interface AdminService {

    Admin getAdminByUsername(String username);

    Admin getAdminByAccessToken(String accessToken);

    boolean updateAccessToken(Admin admin);
}
