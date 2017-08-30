package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.TagUserLog;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Repository
public interface TagUserLogDao {

    boolean saveTagUserLog(TagUserLog tagUserLog);

    boolean deleteTagUserLog(int tagId, int userId);

    boolean deleteTagUserLogById(int id);

    List<Integer> getTagUserLogUserIdListByTagId(int tagId);

    List<Integer> getTagUserLogTagListByUserId(int userId, int offset, int limit);

    TagUserLog getTagUserLog(int userId, int tagId);

    List<TagUserLog> extractNewRecords(Date lastExtractTime);

    boolean updateTagUserLog(TagUserLog tagUserLog);

}
