package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.TagRelation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xmc1993 on 2017/5/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test/context/config/spring-test.xml"})
public class TagRelationDaoTest {
    @Autowired
    private TagRelationDao tagRelationDao;


    @Test
    public void testSaveTagRelation() {
        for (int i = 8; i < 11 ; i++) {
            TagRelation tagRelation = new TagRelation();
            tagRelation.setStoryId(i);
            tagRelation.setTagId(100020);
            tagRelationDao.saveTagRelation(tagRelation);
        }

        for (int i = 9; i < 12 ; i++) {
            TagRelation tagRelation = new TagRelation();
            tagRelation.setStoryId(i);
            tagRelation.setTagId(100016);
            tagRelationDao.saveTagRelation(tagRelation);
        }
    }
}
