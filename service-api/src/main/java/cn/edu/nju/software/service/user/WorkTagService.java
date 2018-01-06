package cn.edu.nju.software.service.user;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.WorkTag;
import cn.edu.nju.software.entity.Works;

import java.util.List;

/**
 * @author zj
 */

public interface WorkTagService {
    WorkTag selectByContent(String content);
    ResponseData<WorkTag> insertWorkTag(String content,Integer authorId);
    ResponseData<List<WorkTag>> selectAll();
    List<WorkTag> selectTagsRecommendedAndCustomized(Integer authorId);
    ResponseData<WorkTag> selectWorkTagById(Integer id);
    List<WorkTag> getWorkTagByIdList(List<Integer> idList);
    int update(WorkTag workTag);
    int deleteWorkTag(Integer id);
    int deleteByAuthorId(Integer authorId);
}
