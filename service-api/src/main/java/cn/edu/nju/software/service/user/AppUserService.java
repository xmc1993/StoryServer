package cn.edu.nju.software.service.user;

import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.entity.UserBase;

import java.io.File;
import java.util.List;

/**
 * @author dalec, 16/04/02
 */
public interface AppUserService {


    User getUserByMobileOrId(String unionId);

    User getUserByMobile(String mobile);

    boolean updateUser(User user, String appId);

    void clearOldAvatar(File dir, String mobile, String curAvatar);

    User loginByUnionId(String unionId);

    User addOrUpdateUser(User user);

    UserBase getUserBaseById(int id);

    List<UserBase> getUserBaseListByIdList(List<Integer> idList);

    User getUserByDeviceId(String deviceId);

}
