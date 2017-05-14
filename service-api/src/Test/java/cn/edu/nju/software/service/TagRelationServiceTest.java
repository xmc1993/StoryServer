package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.TagRelation;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/test/context/config/spring-test.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagRelationServiceTest {
    @Autowired
    private TagRelationService tagRelationService;
    private TagRelation tagRelation;
    private TagRelation tagRelation1;

    @Before
    public void setUp() {
        tagRelation = new TagRelation();
        tagRelation.setId(99999);
        tagRelation.setStoryId(88888);
        tagRelation.setTagId(77777);
        tagRelation.setCreateTime(new Date());
        tagRelation.setUpdateTime(new Date());

        tagRelation1 = new TagRelation();
        tagRelation1.setId(99998);
        tagRelation1.setStoryId(88887);
        tagRelation1.setTagId(77778);
        tagRelation1.setCreateTime(new Date());
        tagRelation1.setUpdateTime(new Date());
    }

    @Test
    public void test1Save() {
        assert tagRelationService.saveTagRelation(tagRelation);
        assert tagRelationService.saveTagRelation(tagRelation1);
    }

    @Test
    public void test2Get() {
        assert tagRelationService.getStoryIdsByTagId(77777).get(0) == 88888;
        assert tagRelationService.getTagIdsByStoryId(88888).get(0) == 77777;
        ArrayList<Integer> idList = new ArrayList<>();
        idList.add(1);
        idList.add(2);
        idList.add(77777);
        idList.add(77778);
        assert tagRelationService.getStoryIdListByTagIdList(idList).size() == 2;
    }

    @Test
    public void test3Update() {

    }

    @Test
    public void test4Delete() {
        assert tagRelationService.deleteTagRelationById(99999);
        assert tagRelationService.deleteTagRelationById(99998);
    }
}
