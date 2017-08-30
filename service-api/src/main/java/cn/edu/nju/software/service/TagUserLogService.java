package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.TagUserLog;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
public interface TagUserLogService {

    boolean saveOrUpdate(TagUserLog tagUserLog);

    boolean deleteTagUserLog(int tagId, int userId);

    boolean deleteTagUserLogById(int id);

    List<Integer> getTagUserLogUserIdListByTagId(int tagId, int offset, int limit);

    List<Integer> getTagUserLogTagIdListByUserId(int userId, int offset, int limit);

    TagUserLog getTagUserLog(int userId, int tagId);

    List<TagUserLog> extractNewRecords(Date lastExtractTime);

}
