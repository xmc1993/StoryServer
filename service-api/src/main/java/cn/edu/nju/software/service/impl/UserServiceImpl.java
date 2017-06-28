package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.UserDao;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by Kt on 2017/6/28.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User getUserById(int id){
        return userDao.getUserByPrimaryKey(id);
    }
}
