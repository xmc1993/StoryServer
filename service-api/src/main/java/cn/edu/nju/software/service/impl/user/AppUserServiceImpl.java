package cn.edu.nju.software.service.impl.user;

import cn.edu.nju.software.dao.user.AppUserDao;
import cn.edu.nju.software.dto.TimeVo;
import cn.edu.nju.software.entity.TwoTuple;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.entity.UserBase;
import cn.edu.nju.software.service.user.AppUserService;
import cn.edu.nju.software.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.util.Date;
import java.util.List;

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
        return userDao.getUserById(Integer.valueOf(unionId));

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
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
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

    //TODO review Ly's code 这一块用redis做好么？
    /**
     * 
     * @param id
     * @return 连续登陆次数
     */
    @Override
    public TwoTuple<Integer, Boolean> addContinuousLandDay(Integer id) {
    	Jedis jedis = JedisUtil.getJedis();
    	String todayLoginKey = "login:" + DateUtil.dateToString(new Date(), "yyyyMMdd");
		//今天没有任何用户登录过，则创建今日登陆记录的key（todayLoginKey）
		if(!jedis.exists(todayLoginKey)){
			jedis.set(todayLoginKey, "");//他的值不重要，只要将他存入redis并限制他的死亡时间。
			jedis.expireAt(todayLoginKey, MyDateUtil.dateToUnix(0));
		}
		//根据用户id查看今天用户是否登录过
		Boolean hasLogin = jedis.getbit(todayLoginKey, id);
		
		//如果该用户今天未登陆过则设置他的登录记录：String continusDays = jedis.get("logincontinus:"+id);
		if(!hasLogin){
			jedis.setbit(todayLoginKey, id.longValue(), true);
			String continusDays = jedis.get("logincontinus:"+id);
			//用户没有连续登陆记录或者昨天一整天都没登录，则将连续登录记录重置为1
			if(continusDays == null){
				jedis.set("logincontinus:"+id,"1");				
			}else{//连续登录记录加一
				jedis.incr("logincontinus:"+id);
			}
			//设置连续登录记录在明天23：59：59清除。所以如果明天一整天都没登录（刷新）过logincontinus
            //则连续登录记录清空。下次登录时检验continusDays == null，然后将连续登录天数重置为1
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

    @Override
    public boolean updateUserWorkCount(Integer workCount,Integer userId) {
        return userDao.updateUserWorkCount(workCount,userId);
    }

    @Override
    public boolean updateListenCountByUserId(Integer listenCount,Integer userId) {
        return userDao.updateListenCountByUserId(listenCount,userId);
    }
	@Override
	public boolean deleteUserById(int id) {
		return userDao.deleteUserById(id);
	}
	
	@Override
	public boolean recoverUserById(int id) {
		return userDao.recoverUserById(id);
	}

    @Override
    public String getTotalRecordTime(int id) {
        return userDao.getTotalRecordTime(id);
    }

    @Override
    public boolean updateTotalRecordTime(int id, String time) {
        return userDao.updateTotalRecordTime(id, time);
    }

    @Override
    public boolean updateTotalRecordTime(int id, String gap, boolean increase) {
        TimeVo timeVo = new TimeVo(getTotalRecordTime(id));
        if (increase){
            timeVo.increase(gap);
        }else {
            timeVo.reduce(gap);
        }
        return updateTotalRecordTime(id, timeVo.toString());
    }

    @Override
    public Integer getListenCount(int userId) {
        return userDao.getListenCount(userId);
    }

    @Override
    public boolean decreaseWorkCountSafely(int userId) {
        User user = userDao.getUserById(userId);
        //防止出现-1的情况
        if (user == null || 0 == user.getWorkCount()) {
            return true;
        }
        return userDao.removeWork(userId);
    }

    @Override
    public boolean updateDeviceToken(String deviceToken, int userId) {
        return userDao.updateDevcieToken(userId,deviceToken);
    }

}
