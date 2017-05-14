package cn.edu.nju.software.service;

import cn.edu.nju.software.dao.StoryTagDao;
import cn.edu.nju.software.entity.StoryTag;
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
public class StoryTagServiceTest {

    @Autowired
    private StoryTagService storyTagService;
    @Autowired
    private StoryTagDao storyTagDao;
    private StoryTag storyTag;

    @Before
    public void setUp() {
        storyTag = new StoryTag();
        storyTag.setId(99999);
        storyTag.setContent("测试标签");
        storyTag.setParentId(0);
        storyTag.setCreateTime(new Date());
        storyTag.setUpdateTime(new Date());
    }

    @Test
    public void test1Save() {
        assert storyTagService.saveStoryTag(storyTag);
    }

    @Test
    public void test2Get() {
        assert storyTagService.getStoryTagById(99999).getContent().equals("测试标签");
        assert storyTagService.getStoryTagListByParentId(0).size() == 1;
    }

    @Test
    public void test3Update() {
        storyTag.setContent("测试更新");
        assert storyTagService.updateStoryTag(storyTag);
        assert storyTagService.getStoryTagById(99999).getContent().equals("测试更新");
    }

    @Test
    public void test4Delete() {
        assert storyTagService.deleteStoryTag(99999);
        assert storyTagService.getStoryTagById(99999).getValid() == 0;
        assert  storyTagDao.deleteHard(99999);
    }
}
