package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.StoryTag;
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
public class StoryTagDaoTest {
    @Autowired
    private StoryTagDao storyTagDao;


    @Test
    public void testSaveStory() {
        for (int i = 0; i <3 ; i++) {
            StoryTag storyTag = new StoryTag();
            storyTag.setCreateTime(new Date());
            storyTag.setUpdateTime(new Date());
            storyTag.setContent("测试标签-" + i);
            storyTag.setParentId(0);
            storyTagDao.saveStoryTag(storyTag);
        }
        for (int i = 0; i <3 ; i++) {
            StoryTag storyTag = new StoryTag();
            storyTag.setCreateTime(new Date());
            storyTag.setUpdateTime(new Date());
            storyTag.setContent("测试子标签-" + i);
            storyTag.setParentId(100016);
            storyTagDao.saveStoryTag(storyTag);
        }
    }
}
