package cn.edu.nju.software.service.impl.user;

import cn.edu.nju.software.dao.user.UserBusinessDao;
import cn.edu.nju.software.entity.Business;
import cn.edu.nju.software.service.user.UserBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dalec, 16/01/28
 */
@Service
public class UserBusinessServiceImpl implements UserBusinessService {

	@Autowired
	private UserBusinessDao businessDao;

	@Override
	public Business getBusinessById(int id) {
		return businessDao.getBusinessById(id);
	}

	@Override
	public Business getBusinessByAppId(String appId) {
		return businessDao.getBusinessByAppId(appId);
	}

	@Override
	public Business getBusinessByMobile(String mobile) {
		return businessDao.getBusinessByMobile(mobile);
	}

}
