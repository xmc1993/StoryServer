package cn.edu.nju.software.service.user;

public interface UserMessageService {
	
	boolean sendMessage(String phoneNumber, String code);
	boolean sendMessageByBusiness(String username, String secret, String phoneNumber, String code);
}
