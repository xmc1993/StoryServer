package cn.edu.nju.software.service.user;

import cn.edu.nju.software.entity.TwoTuple;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.entity.UserBase;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author dalec, 16/04/02
 */
public interface AppUserService {


    boolean updateUser(User user);

    User getUserByMobileOrId(String unionId);

    User getUserByMobile(String mobile);

    boolean updateUser(User user, String appId);

    void clearOldAvatar(File dir, String mobile, String curAvatar);

    User loginByUnionId(String unionId);

    User addOrUpdateUser(User user);

    UserBase getUserBaseById(int id);

    List<UserBase> getUserBaseListByIdList(List<Integer> idList);

    User getUserByDeviceId(String deviceId);
    
    TwoTuple<Integer,Boolean> addContinusLandDay(Integer id);
    
    boolean isLoginTodayFirst(Integer userId);

    boolean updateUserWorkCount(Integer workCount,Integer userId);
}
