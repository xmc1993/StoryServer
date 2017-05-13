package cn.edu.nju.software.dao.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.edu.nju.software.entity.User;

/**
 * 所有的传参顺序第一个都是businessId
 */
@Repository
public interface AppUserDao {

    boolean saveUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(int id);

    User getUserById(int userId);

    User getUserByUnionId(String unionId);

    List<User> getUserByNickname(String nickname);

    User getUserByMobile(String mobile);

    Integer getNewestId();

    boolean newFollower(int id);

    boolean loseFollower(int id);

    boolean newFollowee(int id);

    boolean removeFollowee(int id);

    boolean newWork(int id);

    boolean removeWork(int id);


}
