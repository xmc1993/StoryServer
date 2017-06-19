package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.SoundEffectTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface SoundEffectTagDao {

    int saveSoundEffectTag(SoundEffectTag soundEffectTag);

    boolean updateSoundEffectTag(SoundEffectTag soundEffectTag);

    boolean deleteSoundEffectTagById(int tagId);

    SoundEffectTag getSoundEffectTagById(int tagId);

    SoundEffectTag getSoundEffectTagByIdHard(int tagId);

    List<SoundEffectTag> getSoundEffectTagListByPage(int offset, int limit);

    List<SoundEffectTag> getAllSoundEffectTags();

    List<SoundEffectTag> getTagListByIdList(@Param("idList") List<Integer> idList);

    boolean deleteHard(int id);

    List<SoundEffectTag> getAllSecondLevelTags();

    Integer getSoundEffectTagCount();
}
