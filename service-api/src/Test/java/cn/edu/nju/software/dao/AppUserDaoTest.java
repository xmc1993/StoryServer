package cn.edu.nju.software.dao;

import cn.edu.nju.software.dao.user.AppUserDao;
import cn.edu.nju.software.entity.Business;
import cn.edu.nju.software.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/10.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-test.xml"})
public class AppUserDaoTest {

    @Autowired
    private AppUserDao appUserDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private Business business;

    @Test
    public void testSaveUser(){
        User user = new User();
        user.setId(923);
        user.setNickname("xmc");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        appUserDao.saveUser(user);
    }

    @Test
    public void testGetUser(){
        User userById = appUserDao.getUserById(923);
        Assert.assertEquals("xmc", userById.getNickname());
    }

    @Test
    public void testDeleteUser(){
        Assert.assertTrue(appUserDao.deleteUser(923));
    }

    @Test
    public void testGetAdmin(){
        adminDao.getAdminByUsername("admin");
    }

    @Test
    public void testBusiness(){
        System.out.println(business.getWxAppId());
    }
}
