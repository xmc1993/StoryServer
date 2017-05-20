package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Story;
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
public class StoryDaoTest {
    @Autowired
    private StoryDao storyDao;


    @Test
    public void testSaveStory() {
        for (int i = 0; i <10 ; i++) {
            Story story = new Story();
            story.setTitle("测试故事-" + i);
            story.setCreateTime(new Date());
            story.setUpdateTime(new Date());
            story.setContent("测试内容");
            story.setAuthor("测试作者");
            story.setPreCoverUrl("http://127.0.0.1/source/head/12321321_42131231.jpg");
            story.setCoverUrl("http://127.0.0.1/source/head/12321321_42131231.jpg");
            story.setBackgroundUrl("http://127.0.0.1/source/head/12321321_42131231.jpg");
            story.setGuide("测试指导");
            story.setPrice("9.9");
            storyDao.saveStory(story);
        }

    }
}
