package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.SoundEffectDao;
import cn.edu.nju.software.entity.SoundEffect;
import cn.edu.nju.software.service.SoundEffectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class SoundEffectServiceTest implements SoundEffectService {
    @Autowired
    private SoundEffectDao soundEffectDao;

    @Override
    public boolean saveSoundEffect(SoundEffect soundEffect) {
        return soundEffectDao.saveSoundEffect(soundEffect);
    }

    @Override
    public boolean deleteSoundEffect(int id) {
        return soundEffectDao.deleteSoundEffectById(id);
    }

    @Override
    public boolean updateSoundEffect(SoundEffect soundEffect) {
        soundEffect.setUpdateTime(new Date());
        return soundEffectDao.updateSoundEffect(soundEffect);
    }

    @Override
    public List<SoundEffect> getAllSoundEffect() {
        return soundEffectDao.getAllSoundEffects();
    }

    @Override
    public SoundEffect getSoundEffectById(int id) {
        return soundEffectDao.getSoundEffectById(id);
    }

}
