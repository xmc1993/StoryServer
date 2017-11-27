package cn.edu.nju.software.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.vo.request.StatQueryBean;

/**
 * 所有的传参顺序第一个都是businessId
 */
@Repository
public interface UserDao {


	public boolean updateUser(User user);

	public User getUserById(int businessId, int userId);

	public List<User> getAllUsers(int businessId);

	public User getUserByMobile(int businessId, String mobile);

	public List<User> getStatsUserAll(StatQueryBean bean);
	
	public Integer getStatsUserAllCount(StatQueryBean bean);

	public int addLikeStoryCount(int userId);

	public int delLikeStoryCount(int userId);

    Integer getLikeCountByUserId(int userId);

    List<User> getUserListByIdList(@Param("idList") List<Integer> idList, @Param("offset") int offset, @Param("limit") int limit);

	User getUserByPrimaryKey(int userId);

	List<Integer> getDummyIdListByPage(int limit);
}
