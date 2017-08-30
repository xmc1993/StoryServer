package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.TagUserLogDao;
import cn.edu.nju.software.dao.StoryTagDao;
import cn.edu.nju.software.dao.user.AppUserDao;
import cn.edu.nju.software.entity.TagUserLog;
import cn.edu.nju.software.service.TagUserLogService;
import cn.edu.nju.software.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class TagUserLogServiceImpl implements TagUserLogService {
    @Autowired
    private TagUserLogDao tagUserLogDao;
    @Autowired
    private StoryTagDao tagDao;
    @Autowired
    private AppUserDao appUserDao;

    @Override
    public boolean saveOrUpdate(TagUserLog tagUserLog) {
        TagUserLog temp = tagUserLogDao.getTagUserLog(tagUserLog.getUserId(), tagUserLog.getTagId());
        if (temp != null) {
            temp.setTotalCount(temp.getTotalCount() + 1);
            return tagUserLogDao.updateTagUserLog(temp);
        }
        tagUserLog.setCreateTime(new Date());
        tagUserLog.setUpdateTime(new Date());
        tagUserLog.setTotalCount(1);
        return tagUserLogDao.saveTagUserLog(tagUserLog);
    }

    @Override
    public boolean deleteTagUserLog(int tagId, int userId) {
        return tagUserLogDao.deleteTagUserLog(tagId, userId);
    }

    @Override
    public boolean deleteTagUserLogById(int id) {
        return tagUserLogDao.deleteTagUserLogById(id);
    }

    @Override
    public List<Integer> getTagUserLogUserIdListByTagId(int tagId, int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        List<Integer> userIdList = tagUserLogDao.getTagUserLogUserIdListByTagId(tagId);
        return userIdList;
    }

    @Override
    public List<Integer> getTagUserLogTagIdListByUserId(int userId, int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return tagUserLogDao.getTagUserLogTagListByUserId(userId, offset, limit);
    }

    @Override
    public TagUserLog getTagUserLog(int userId, int tagId) {
        return tagUserLogDao.getTagUserLog(userId, tagId);
    }

    @Override
    public List<TagUserLog> extractNewRecords(Date lastExtractTime) {
        return tagUserLogDao.extractNewRecords(lastExtractTime);
    }
}
