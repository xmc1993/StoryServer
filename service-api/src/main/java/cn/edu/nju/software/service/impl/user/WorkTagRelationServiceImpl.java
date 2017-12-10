package cn.edu.nju.software.service.impl.user;

import cn.edu.nju.software.dao.user.WorkTagMapper;
import cn.edu.nju.software.dao.user.WorkTagRelationMapper;
import cn.edu.nju.software.entity.WorkTagRelation;
import cn.edu.nju.software.service.user.WorkTagRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zj
 */
@Service
public class WorkTagRelationServiceImpl implements WorkTagRelationService {
    @Autowired
    private WorkTagRelationMapper workTagRelationMapper;

    @Override
    public int insert(WorkTagRelation workTagRelation) {
        return workTagRelationMapper.insert(workTagRelation);
    }
}
