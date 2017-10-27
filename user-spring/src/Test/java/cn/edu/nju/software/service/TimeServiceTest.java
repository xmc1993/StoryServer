package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Works;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by xmc1993 on 2017/10/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})
public class TimeServiceTest {
//    @Autowired
    private WorksService worksService;
    @Autowired
    private WxOAuthService wxOAuthService;

    @Test
    public void getTime() {
        List<Works> works = worksService.getLatestWorksByPage(0, 999999);
        Map<Integer, ArrayList<String>> map = new HashMap();
        for (Works work : works) {
            ArrayList<String> arr = map.get(work.getUserId());
            if (arr == null) {
                arr = new ArrayList<>();
                map.put(work.getUserId(), arr);
            }
            arr.add(work.getDuration());
        }

        Iterator<Map.Entry<Integer, ArrayList<String>>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, ArrayList<String>> next = iterator.next();
            ArrayList<String> values = next.getValue();
            String s = "";
            s += next.getKey() + " ";
            for (String value : values) {
                s += value + "-";
            }
            s += "\n";
            System.out.println(s);
        }
    }

    @Test
    public void getToken(){
        String accessToken = wxOAuthService.getAccessToken();
        System.out.println(accessToken);
    }

}
