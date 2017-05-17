package cn.edu.nju.software.service;

import cn.edu.nju.software.dao.SoundEffectDao;
import cn.edu.nju.software.entity.SoundEffect;
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
public class SoundEffectServiceTest {

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
