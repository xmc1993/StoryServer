package cn.edu.nju.software.common;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Test
    public void testDate() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String simple = "2016-05-12";
        Date parse = dateFormat.parse(simple);
        System.out.println(parse);
    }
}
