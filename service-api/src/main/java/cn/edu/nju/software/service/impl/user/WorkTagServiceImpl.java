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
    public WorkTag selectByContent(String content) {
        List<WorkTag> list = selectAll().getObj();
        for (WorkTag workTag : list) {
            String con = workTag.getContent();
            if (con != null) {
                if (con.equals(content)) {
                    return workTag;
                }
            }
        }
        return null;
    }

    @Override
    public ResponseData<WorkTag> insertWorkTag(String content, Integer authorId) {
        ResponseData<WorkTag> responseData = new ResponseData<>();
        WorkTag workTagInDb = selectByContent(content);
        //不存在才添加
        if (workTagInDb == null) {
            WorkTag workTag = new WorkTag();
            workTag.setAuthorId(authorId);
            workTag.setContent(content);
            workTag.setCreateTime(new Date());
            workTag.setUpdateTime(new Date());
            int res = workTagMapper.insert(workTag);
            if (res != 1) {
                responseData.jsonFill(2, "添加失败", null);
                return responseData;
            } else {
                responseData.jsonFill(1, null, workTag);
                return responseData;
            }

        } else {//已存在就从数据库取出
            responseData.jsonFill(1, null, workTagInDb);
            return responseData;
        }
    }

    @Override
    public ResponseData<List<WorkTag>> selectAll() {
        WorkTagExample workTagExample = new WorkTagExample();
        List<WorkTag> list = workTagMapper.selectByExample(workTagExample);
        ResponseData<List<WorkTag>> responseData = new ResponseData<>();
        responseData.jsonFill(1, null, list);
        responseData.setCount(list.size());
        return responseData;
    }

/*    @Override
    public List<WorkTag> selectTagsRecommendedAndCustomized(Integer userId) {
        WorkTagExample workTagExample = new WorkTagExample();
        WorkTagExample.Criteria criteria = workTagExample.createCriteria();
        List<Integer> authorIdList = new ArrayList<>();
        authorIdList.add(0);//后台上传作品标签的作者id为0
        authorIdList.add(userId);
        criteria.andAuthorIdIn(authorIdList);
        criteria.andValidEqualTo(1);
        return workTagMapper.selectByExample(workTagExample);
    }*/

    @Override
    public List<WorkTag> selectTagsRecommendedAndCustomized(Integer userId) {
        List<WorkTag> workTagList=new ArrayList<>();
        WorkTagExample workTagExample = new WorkTagExample();
        WorkTagExample.Criteria criteria1 = workTagExample.createCriteria();
        workTagExample.setOrderByClause("update_time desc");
        criteria1.andValidEqualTo(1);
        criteria1.andAuthorIdEqualTo(0);
        List<WorkTag> list=workTagMapper.selectByExample(workTagExample);
        workTagList.addAll(list.subList(0,3));

        workTagExample = new WorkTagExample();
        WorkTagExample.Criteria criteria2 = workTagExample.createCriteria();
        workTagExample.setOrderByClause("update_time desc");
        criteria2.andValidEqualTo(1);
        criteria2.andAuthorIdEqualTo(userId);
        list=workTagMapper.selectByExample(workTagExample);
        if (list.size()<= 2){
            workTagList.addAll(list);
        } else{
            workTagList.addAll(list.subList(0,2));
        }
        return workTagList;
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
        WorkTagExample workTagExample = new WorkTagExample();
        WorkTagExample.Criteria criteria = workTagExample.createCriteria();
        criteria.andAuthorIdEqualTo(authorId);
        return workTagMapper.deleteByExample(workTagExample);
    }

}
