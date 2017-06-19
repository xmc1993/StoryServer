package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.SoundEffectTag;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface SoundEffectTagService {

    int saveSoundEffectTag(SoundEffectTag soundEffectTag);

    SoundEffectTag updateSoundEffectTag(SoundEffectTag soundEffectTag);

    boolean deleteSoundEffectTag(int tagId);

    SoundEffectTag getSoundEffectTagById(int tagId);

    List<SoundEffectTag> getSoundEffectTagListByIdList(List<Integer> idList);

    List<SoundEffectTag> getAllSoundEffectTags();

    List<SoundEffectTag> getSoundEffectTagsByPage(int offset, int limit);

    SoundEffectTag getSoundEffectTagByIdHard(int tagId);

    Integer getSoundEffectTagCount();
}
