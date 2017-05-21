package cn.edu.nju.software.service.impl.user;

import cn.edu.nju.software.dao.user.AppUserDao;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.entity.UserBase;
import cn.edu.nju.software.service.user.AppUserService;
import cn.edu.nju.software.util.PhoneFormatCheckUtils;
import cn.edu.nju.software.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
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
    public User loginByUnionId(int businessId, String unionId) {
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
}
