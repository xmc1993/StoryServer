package cn.edu.nju.software.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.nju.software.dao.user.AppUserBrowseDao;
import cn.edu.nju.software.entity.UserBrowse;
import cn.edu.nju.software.service.user.AppUserBrowseService;

/**
 * Created by fenggang on 9/29/16.
 */
@Service
public class AppUserBrowseServiceImpl implements AppUserBrowseService{

//	@Autowired
	private AppUserBrowseDao userBrowseDao;

	public void save(UserBrowse entity){
		userBrowseDao.save(entity);
	}

}
