package cn.edu.nju.software.service;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xmc1993 on 2017/5/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-test.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmptyServiceTest {


    @Before
    public void setUp() {

    }

    @Test
    public void test1Save() {
    }

    @Test
    public void test2Get() {

    }

    @Test
    public void test3Update() {

    }

    @Test
    public void test4Delete() {

    }
}
