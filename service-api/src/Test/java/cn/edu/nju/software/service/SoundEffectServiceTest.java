package cn.edu.nju.software.service;

import cn.edu.nju.software.dao.SoundEffectDao;
import cn.edu.nju.software.dto.TimeVo;
import cn.edu.nju.software.entity.SoundEffect;
import cn.edu.nju.software.entity.Works;
import cn.edu.nju.software.service.user.AppUserService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by xmc1993 on 2017/5/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-test.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SoundEffectServiceTest {

    @Autowired
    private WorksService worksService;
    @Autowired
    private AppUserService appUserService;


//    @Test
    public void updateTime(){

        appUserService.updateTotalRecordTime(25, "1'12\"", true);
    }

//    @Test
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
            TimeVo timeVo = new TimeVo("0");
            for (String value : values) {
//                s += value + "-";
                timeVo.increase(value);
            }
//            s += "\n";
            appUserService.updateTotalRecordTime(next.getKey(), timeVo.toString());
            s += timeVo.toString() + "\n";
            System.out.println(s);
        }
    }

    @Autowired
    private SoundEffectService soundEffectService;
    @Autowired
    private SoundEffectDao soundEffectDao;

    private SoundEffect soundEffect;

    @Before
    public void setUp() {
        soundEffect = new SoundEffect();
        soundEffect.setId(99999);
        soundEffect.setUrl("www.source.com/mock.mp3");
        soundEffect.setDescription("海豚音");
        soundEffect.setCreateTime(new Date());
        soundEffect.setUpdateTime(new Date());
    }

    @Test
    public void test1Save() {
        assert soundEffectService.saveSoundEffect(soundEffect);
    }

    @Test
    public void test2Get() {
        SoundEffect temp = null;
        temp = soundEffectService.getSoundEffectById(99999);
        assert temp.getDescription().equals("海豚音");
    }

    @Test
    public void test3Update() {
        soundEffect.setDescription("小狗叫");
        assert soundEffectService.updateSoundEffect(soundEffect) != null;
        SoundEffect temp = null;
        assert (temp = soundEffectService.getSoundEffectById(99999)) != null;
        assert temp.getDescription().equals("小狗叫");
    }

    @Test
    public void test4Delete() {
        assert soundEffectService.deleteSoundEffect(99999);
        assert soundEffectService.getSoundEffectById(99999).getValid() == 0;
        assert soundEffectDao.deleteHard(99999);
    }
}
