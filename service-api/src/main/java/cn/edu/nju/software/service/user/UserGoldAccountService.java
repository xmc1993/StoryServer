package cn.edu.nju.software.service.user;

import cn.edu.nju.software.entity.UserGoldAccount;

/**
 * @author zj
 */

public interface UserGoldAccountService {
    int addUserGoldAccount(Integer userId);
    int updateUserGoldAccount(UserGoldAccount userGoldAccount);
    UserGoldAccount getUserGoldAccountById(Integer id);
    Integer getGoldAmountByUserId(Integer userId);
    int deleteUserGoldAccount(Integer id);
}
