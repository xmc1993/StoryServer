package cn.edu.nju.software.service.user;

import cn.edu.nju.software.entity.User;

import java.util.List;

public interface UserMessageService {

	boolean sendMessage(String phoneNumber, String code);

	boolean sendMessageByBusiness(String username, String secret, String phoneNumber, String code);

	// 添加用户
	User saveUser(User user);

	// 根据姓名查询用户
	List<User> getUserByNickname(String nickname);

	// 根据用户ID查询用户
	User getUserById(int userId);

	// 删除用户
	boolean deleteUserById(int id);

	// 更新用户信息
	boolean updateUser(User user);

	// 获取所有用户
	List<User> getAllUserList();

	List<Integer> getAllUserIdList();

	// 根据页数获取所有用户信息
	List<User> getUserListByPage(int page, int pageSize);
	
	//获取用户数
    int getUserCount();

}
