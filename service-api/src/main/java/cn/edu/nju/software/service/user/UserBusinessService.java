package cn.edu.nju.software.service.user;

import cn.edu.nju.software.entity.Business;
import cn.edu.nju.software.exception.BusinessException;

/**
 * @author dalec, 16/01/28
 */
public interface UserBusinessService {

	public Business getBusinessById(int id);

	public Business getBusinessByAppId(String appId);

	public Business getBusinessByMobile(String mobile);

	Business addBusiness(Business business) throws BusinessException;


}
