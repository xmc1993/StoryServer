package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.BackgroundMusicTagDao;
import cn.edu.nju.software.entity.BackgroundMusicTag;
import cn.edu.nju.software.service.BackgroundMusicTagService;
import cn.edu.nju.software.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class BackgroundMusicTagServiceImpl implements BackgroundMusicTagService {

    @Autowired
    private BackgroundMusicTagDao backgroundMusicTagDao;

    @Override
    public int saveBackgroundMusicTag(BackgroundMusicTag backgroundMusicTag) {
        return backgroundMusicTagDao.saveBackgroundMusicTag(backgroundMusicTag);
    }

    @Override
    public BackgroundMusicTag updateBackgroundMusicTag(BackgroundMusicTag backgroundMusicTag) {
        backgroundMusicTag.setUpdateTime(new Date());
        boolean res = backgroundMusicTagDao.updateBackgroundMusicTag(backgroundMusicTag);
        return res ? backgroundMusicTagDao.getBackgroundMusicTagById(backgroundMusicTag.getId()) : null;
    }

    @Override
    public boolean deleteBackgroundMusicTag(int tagId) {
        return backgroundMusicTagDao.deleteBackgroundMusicTagById(tagId);
    }

    @Override
    public BackgroundMusicTag getBackgroundMusicTagById(int tagId) {
        return backgroundMusicTagDao.getBackgroundMusicTagById(tagId);
    }


    @Override
    public List<BackgroundMusicTag> getBackgroundMusicTagListByIdList(List<Integer> idList) {
        idList.add(-1);
        return backgroundMusicTagDao.getGroundMusicTagListByIdList(idList);
    }

    @Override
    public List<BackgroundMusicTag> getAllBackgroundMusicTags() {
        return backgroundMusicTagDao.getAllBackgroundMusicTags();
    }

    @Override
    public List<BackgroundMusicTag> getBackgroundMusicTagsByPage(int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return backgroundMusicTagDao.getBackgroundMusicTagListByPage(offset, limit);
    }

    @Override
    public BackgroundMusicTag getBackgroundMusicTagByIdHard(int tagId) {
        return backgroundMusicTagDao.getBackgroundMusicTagByIdHard(tagId);
    }

    @Override
    public Integer getBackgroundMusicTagCount(){
        return backgroundMusicTagDao.getBackgroundMusicTagCount();
    }
}
