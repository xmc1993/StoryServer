package cn.edu.nju.software.common;

import org.junit.Test;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public class EnvTest {
    @Test
    public void testGetEnv(){
        Map<String, String> map = System.getenv();

        System.out.println(map.get("STORY_HOME"));

    }
}
