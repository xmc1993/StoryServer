package cn.edu.nju.software.service.impl.user;

import cn.edu.nju.software.dao.user.WorkTagRelationMapper;
import cn.edu.nju.software.entity.WorkTagRelation;
import cn.edu.nju.software.entity.WorkTagRelationExample;
import cn.edu.nju.software.entity.Works;
import cn.edu.nju.software.service.WorksService;
import cn.edu.nju.software.service.user.WorkTagRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Integer> getWorkTagIdListByWorkId(Integer workId) {
        return workTagRelationMapper.getWorkTagIdListByWorkId(workId);
    }


}
