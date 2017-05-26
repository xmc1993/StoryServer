package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.SoundEffect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
@Repository
public interface SoundEffectDao {

    boolean saveSoundEffect(SoundEffect soundEffect);

    List<SoundEffect> getAllSoundEffects();

    boolean updateSoundEffect(SoundEffect soundEffect);

    boolean deleteSoundEffectById(int id);

    SoundEffect getSoundEffectById(int id);

    SoundEffect getSoundEffectByIdHard(int id);

    boolean deleteHard(int id);

    List<SoundEffect> getSoundEffectListByPage(int offset, int limit);

    List<SoundEffect> getSoundEffectListByIdList(@Param("idList")List<Integer> idList);

}
