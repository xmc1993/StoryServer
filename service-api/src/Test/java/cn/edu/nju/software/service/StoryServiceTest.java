package cn.edu.nju.software.service;

import cn.edu.nju.software.dao.StoryDao;
import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.Works;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test/context/config/spring-test.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StoryServiceTest {
    @Autowired
    private StoryService storyService;
    @Autowired
    private StoryDao storyDao;
    private Story story;
    @Autowired
    private WorksService worksService;

    @Before
    public void setUp() {
        story = new Story();
        story.setId(99999);
        story.setContent("测试故事");
        story.setCreateTime(new Date());
        story.setUpdateTime(new Date());
        story.setBackgroundUrl("www.source.com/bg.png");
        story.setCoverUrl("www.source.com/bg.png");
        story.setPreCoverUrl("www.source.com/bg.png");
        story.setPress("没人出版社");
        story.setGuide("无");
        story.setTitle("故事标题");
    }

    @Test
    public void test1Save() {
        assert storyService.saveStory(story);
    }

    @Test
    public void test2Get() {
        assert storyService.getStoryById(99999).getTitle().equals("故事标题");
    }

    @Test
    public void test3Update() {
        story.setTitle("新标题");
        assert storyService.updateStory(story) != null;
        assert storyService.getStoryById(99999).getTitle().equals("新标题");
    }

    @Test
    public void test4Delete() {
        assert storyService.deleteStoryById(99999);
        assert storyService.getStoryById(99999).getValid() == 0;
        assert storyDao.deleteHard(99999);
    }

    @Test
    public void testAddWorks(){
        Works works = new Works();
        works.setUserId(20);
        works.setStoryId(36);
        worksService.saveWorks(works);
    }
}
