package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.UserBadge;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-test.xml"})
public class BadgeServiceTest {
    @Autowired
    UserBadgeService userBadgeService;
    @Autowired
    OssService ossService;
    @Test
    public void testSaveBadge(){
        UserBadge userBadge = new UserBadge();
        userBadge.setUserId(123);
        userBadge.setBadgeId(12);
        userBadgeService.saveUserBadge(userBadge);
    }

    @Test
    public void testGetToken(){
        Map<String, String> token = ossService.getToken();
        System.out.println("");
    }

    @Test
    public void testOss(){

    }

}
