package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Admin;
import org.springframework.stereotype.Repository;

/**
 * 所有的传参顺序第一个都是businessId
 */
@Repository
public interface AdminDao {

    Admin getAdminByUsername(String username);

    Admin getAdminByAccessToken(String accessToken);

}
