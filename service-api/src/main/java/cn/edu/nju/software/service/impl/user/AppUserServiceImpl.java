package cn.edu.nju.software.service.impl.user;

import cn.edu.nju.software.dao.user.AppUserDao;
import cn.edu.nju.software.entity.TwoTuple;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.entity.UserBase;
import cn.edu.nju.software.service.user.AppUserService;
import cn.edu.nju.software.util.DateUtil;
import cn.edu.nju.software.util.JedisUtil;
import cn.edu.nju.software.util.MyDateUtil;
import cn.edu.nju.software.util.PhoneFormatCheckUtils;
import cn.edu.nju.software.util.Util;
import redis.clients.jedis.Jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dalec, 16/01/28
 */
@Service
public class AppUserServiceImpl implements AppUserService {
    @Autowired
    private AppUserDao userDao;

    private static final Logger logger = LoggerFactory.getLogger(AppUserServiceImpl.class);


    @Override
    public boolean updateUser(User user, String appId) {
        return userDao.updateUser(user);
    }
    @Override
    public boolean updateUser(User user){
        return userDao.updateUser(user);
    }
    @Override
    public User getUserByMobileOrId(String unionId) {
        //做兼容 mobile既可以传mobile又或者是传id都可以Service层根据实际值进行不同的Dao层调用
        if (PhoneFormatCheckUtils.isPhoneLegal(unionId) || unionId.length() == 11) {
            return userDao.getUserByMobile(unionId);
        } else {
            try {
                Integer.valueOf(unionId);
            } catch (Exception e) {
                return userDao.getUserByMobile(unionId);
            }
            return userDao.getUserById(Integer.valueOf(unionId));
        }
    }

    @Override
    public User getUserByMobile(String mobile) {
        return userDao.getUserByMobile(mobile);
    }


    @Override
    public void clearOldAvatar(File dir, String mobile, String curAvatar) {
        String[] avatars = dir.list();
        if (avatars != null && avatars.length != 0) {
            for (int i = 0; i < avatars.length; i++) {
                if (avatars[i] != null && avatars[i].startsWith(mobile) && !avatars[i].equals(curAvatar)) {
                    File oldAvatar = new File(dir, avatars[i]);
                    if (oldAvatar.exists()) {
                        oldAvatar.delete();
                    }
                }
            }
        }
    }


    @Override
    public User loginByUnionId(String unionId) {
        User user = userDao.getUserByUnionId(unionId);

        if (null != user) {
            boolean accessToken = Util.setNewAccessToken(user);
            if (!accessToken) {
                logger.error("no accessToken");
                return null;
            }
            boolean update = userDao.updateUser(user);
            if (!update) {
                return null;
            }
        }

        return user;
    }

    @Override
    public User addOrUpdateUser(User user) {
        user.setUpdateTime(new Date());
        //如果是需要更新
        if (user.getId() != null && user.getId() != 0) {
            boolean up = userDao.updateUser(user);
            return up ? user : null;
        }
        //如果是需要新增用户
        boolean res = userDao.saveUser(user);
        if (res) {
            user.setId(userDao.getNewestId());
            return user;
        }
        return null;
    }

    @Override
    public UserBase getUserBaseById(int id) {
        return userDao.getUserBaseById(id);
    }

    @Override
    public List<UserBase> getUserBaseListByIdList(List<Integer> idList) {
        idList.add(-1);//防止mybatis查询出错
        return userDao.getUserBaseListByUserIdList(idList);
    }

    @Override
    public User getUserByDeviceId(String deviceId) {
        return userDao.getUserByDeviceId(deviceId);
    }
    
    /**
     * 
     * @param id
     * @return 连续登陆次数
     */
    public TwoTuple<Integer, Boolean> addContinusLandDay(Integer id) {
    	Jedis jedis = JedisUtil.getJedis();
    	String todayLoginKey = "login:" + DateUtil.dateToString(new Date(), "yyyyMMdd");
		//创建今日登陆记录
		if(!jedis.exists(todayLoginKey)){
			jedis.set(todayLoginKey, "");
			jedis.expireAt(todayLoginKey, MyDateUtil.dateToUnix(0));
		}			
		Boolean hasLogin = jedis.getbit(todayLoginKey, id);
		
		//今天未登陆过
		if(!hasLogin){
			jedis.setbit(todayLoginKey, id.longValue(), true);
			String continusDays = jedis.get("logincontinus:"+id);
			//用户没有连续登陆记录
			if(continusDays == null){
				jedis.set("logincontinus:"+id,"1");				
			}else{
				jedis.incr("logincontinus:"+id);
			}
			jedis.expireAt("logincontinus:"+id, MyDateUtil.dateToUnix(1));		
		}
		jedis.close();
		TwoTuple<Integer,Boolean> tt = new TwoTuple<>();
		tt.setId(Integer.parseInt(jedis.get("logincontinus:"+id)));//设置连续登陆次数
		tt.setValue(hasLogin);		
		return tt;
	}
    
	@Override
	public boolean isLoginTodayFirst(Integer userId) {
		Jedis jedis = JedisUtil.getJedis();
    	String todayLoginKey = "login:" + DateUtil.dateToString(new Date(), "yyyyMMdd");
    	Boolean hasLogin = jedis.getbit(todayLoginKey, userId);
    	jedis.close();
    	return !hasLogin;
	}
	
}
