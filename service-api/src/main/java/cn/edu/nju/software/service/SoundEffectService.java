package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.SoundEffect;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
public interface SoundEffectService {

    boolean saveSoundEffect(SoundEffect soundEffect);

    boolean deleteSoundEffect(int id);

    boolean updateSoundEffect(SoundEffect soundEffect);

    List<SoundEffect> getAllSoundEffect();

    SoundEffect getSoundEffectById(int id);


}
