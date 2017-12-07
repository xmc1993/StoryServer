package cn.edu.nju.software.service.impl.user;

import cn.edu.nju.software.dao.user.WorkTagMapper;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.WorkTag;
import cn.edu.nju.software.entity.WorkTagExample;
import cn.edu.nju.software.service.user.WorkTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zj
 */
@Service
public class WorkTagServiceImpl implements WorkTagService {
    @Autowired
    private WorkTagMapper workTagMapper;

    @Override
    public boolean isExistSameContent(String content) {
        List<WorkTag> list = selectAll();
        for (WorkTag workTag : list) {
            String con = workTag.getContent();
            if (con != null) {
                if (con.equals(content)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ResponseData<WorkTag> insertWorkTag(String content,Integer authorId) {
        ResponseData<WorkTag> responseData = new ResponseData<>();
        boolean isExist = isExistSameContent(content);
        if (isExist) {
            responseData.jsonFill(2, "已存在相同内容的作品标签", null);
            return responseData;
        }
        WorkTag workTag = new WorkTag();
        workTag.setAuthorId(authorId);
        workTag.setContent(content);
        workTag.setCreateTime(new Date());
        workTag.setUpdateTime(new Date());
        int res = workTagMapper.insert(workTag);
        if (res != 1) {
            responseData.jsonFill(2, "添加失败", null);
            return responseData;
        }
        responseData.jsonFill(1, null, workTag);
        return responseData;
    }

    @Override
    public List<WorkTag> selectAll() {
        WorkTagExample workTagExample=new WorkTagExample();
        List<WorkTag> list =workTagMapper.selectByExample(workTagExample);
        return list;
    }

    @Override
    public List<WorkTag> selectTagsRecommendedAndCustomized(Integer userId) {
        WorkTagExample workTagExample=new WorkTagExample();
        WorkTagExample.Criteria criteria=workTagExample.createCriteria();
        List<Integer> authorIdList= new ArrayList<>();
        authorIdList.add(0);//后台上传作品标签的作者id为0
        authorIdList.add(userId);
        criteria.andAuthorIdIn(authorIdList);
        criteria.andValidEqualTo(1);
        return workTagMapper.selectByExample(workTagExample);
    }

    @Override
    public ResponseData<WorkTag> selectWorkTagById(Integer id) {
        ResponseData<WorkTag> responseData = new ResponseData<>();
        WorkTag workTag = workTagMapper.selectByPrimaryKey(id);
        if (workTag == null) {
            responseData.jsonFill(2, "无效的id", null);
            return responseData;
        }
        responseData.jsonFill(1, null, workTag);
        return responseData;
    }

    @Override
    public int update(WorkTag workTag) {
        return workTagMapper.updateByPrimaryKeySelective(workTag);
    }

    @Override
    public int deleteWorkTag(Integer id) {
        return workTagMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByAuthorId(Integer authorId) {
        WorkTagExample workTagExample=new WorkTagExample();
        WorkTagExample.Criteria criteria=workTagExample.createCriteria();
        criteria.andAuthorIdEqualTo(authorId);
        return workTagMapper.deleteByExample(workTagExample);
    }
}
