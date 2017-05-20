package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Works;
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
@ContextConfiguration(locations = {"classpath*:/test/context/config/spring-test.xml"})
public class WorksDaoTest {
    @Autowired
    private WorksDao worksDao;


    @Test
    public void testSaveWorks() {
        for (int i = 0; i <3 ; i++) {
            Works works = new Works();
            works.setUpdateTime(new Date());
            works.setCreateTime(new Date());
            works.setStoryId(9);
            works.setStoryTitle("测试故事1");
            works.setUserId(i);
            works.setUsername("xmc-" + i);
            works.setUrl("http://127.0.0.1/source/head/12321321_42131231.jpg");
            worksDao.saveWorks(works);
        }

    }
}
