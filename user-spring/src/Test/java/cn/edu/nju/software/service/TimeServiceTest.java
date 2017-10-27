package cn.edu.nju.software.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xmc1993 on 2017/10/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})
public class TimeServiceTest {
    @Autowired
    private WxOAuthService wxOAuthService;

    @Test
    public void getToken(){
        String accessToken = wxOAuthService.getAccessToken();
        System.out.println(accessToken);
    }

}
