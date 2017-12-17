package cn.edu.nju.software.service.impl.user;

import cn.edu.nju.software.dao.user.UserGoldAccountMapper;
import cn.edu.nju.software.entity.UserGoldAccount;
import cn.edu.nju.software.entity.UserGoldAccountExample;
import cn.edu.nju.software.service.user.UserGoldAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zj
 */
@Service
public class UserGoldAccountServiceImpl implements UserGoldAccountService {
    @Autowired
    private UserGoldAccountMapper userGoldAccountMapper;

    @Override
    public int addUserGoldAccount(Integer userId) {
        UserGoldAccountExample userGoldAccountExample=new UserGoldAccountExample();
        UserGoldAccountExample.Criteria criteria=userGoldAccountExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<UserGoldAccount> list=userGoldAccountMapper.selectByExample(userGoldAccountExample);
        //如果已存在则执行insert影响记录条数为0，所以返回0
        if (list.size()!=0){
            return 0;
        }
        UserGoldAccount userGoldAccount = new UserGoldAccount();
        userGoldAccount.setUserId(userId);
        userGoldAccount.setCreateTime(new Date());
        userGoldAccount.setUpdateTime(new Date());
        return userGoldAccountMapper.insert(userGoldAccount);
    }

    @Override
    public int updateUserGoldAccount(UserGoldAccount userGoldAccount) {
        return userGoldAccountMapper.updateByPrimaryKeySelective(userGoldAccount);
    }

    @Override
    public UserGoldAccount getUserGoldAccountById(Integer id) {
        return userGoldAccountMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer getGoldAmountByUserId(Integer userId) {
        UserGoldAccountExample userGoldAccountExample=new UserGoldAccountExample();
        UserGoldAccountExample.Criteria criteria=userGoldAccountExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<UserGoldAccount> list = userGoldAccountMapper.selectByExample(userGoldAccountExample);
        UserGoldAccount userGoldAccount=list.get(0);
        return userGoldAccount.getAmount();
    }

    @Override
    public int deleteUserGoldAccount(Integer id) {
        return userGoldAccountMapper.deleteByPrimaryKey(id);
    }
}
