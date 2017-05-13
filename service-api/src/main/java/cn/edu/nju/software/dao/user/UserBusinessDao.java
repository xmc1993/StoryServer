package cn.edu.nju.software.dao.user;

import cn.edu.nju.software.entity.Business;
import org.springframework.stereotype.Repository;

/**
 * @author dalec, 16/01/28
 */
@Repository
public interface UserBusinessDao {

	public boolean saveBusiness(Business business);

	public Business getBusinessById(int id);

	public Business getBusinessByAppId(String appId);

	public Business getBusinessByMobile(String mobile);

}
