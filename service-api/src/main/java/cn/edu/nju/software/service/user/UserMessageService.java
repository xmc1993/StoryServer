package cn.edu.nju.software.service.user;

import java.util.List;

import cn.edu.nju.software.entity.User;

public interface UserMessageService {
	
	boolean sendMessage(String phoneNumber, String code);
	boolean sendMessageByBusiness(String username, String secret, String phoneNumber, String code);
	
	//添加用户
	boolean saveUser(User user);
	//根据姓名查询用户
	List<User> getUserByNickname(String nickname);
	//根据用户ID查询用户
	User getUserById(int userId);
	//删除用户
	boolean deleteUser(int id);
	//更新用户信息
	boolean updateUser(User user);
}
