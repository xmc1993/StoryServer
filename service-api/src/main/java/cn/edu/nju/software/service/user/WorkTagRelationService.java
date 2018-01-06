package cn.edu.nju.software.service.user;

import cn.edu.nju.software.entity.WorkTag;
import cn.edu.nju.software.entity.WorkTagRelation;
import cn.edu.nju.software.entity.Works;

import java.util.List;

/**
 * @author zj
 */

public interface WorkTagRelationService {
    int insert(WorkTagRelation workTagRelation);
    List<Integer> getWorkTagIdListByWorkId(Integer workId);

}
