package cn.edu.nju.software.dao;

import cn.edu.nju.software.dao.user.AppUserDao;
import cn.edu.nju.software.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-test.xml"})
public class UserDaoTest {
    @Autowired
    private AppUserDao appUserDao;

    @Autowired
    private AdminDao adminDao;

    @Test
    public void testSaveUser() {
        for (int i = 0; i <10 ; i++) {
            User user = new User();
            user.setNickname("xmc-" + i);
            user.setHeadImgUrl("http://127.0.0.1/source/head/12321321_42131231.jpg");
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            appUserDao.saveUser(user);
        }

    }
}
