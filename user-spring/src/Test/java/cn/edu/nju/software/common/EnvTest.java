package cn.edu.nju.software.common;

import cn.edu.nju.software.util.UploadFileUtil;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;

import org.junit.Test;

import java.io.File;
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
    public void testGetSuffix(){
        System.out.println(UploadFileUtil.getSuffix("213213.jpg"));
    }
  
}
