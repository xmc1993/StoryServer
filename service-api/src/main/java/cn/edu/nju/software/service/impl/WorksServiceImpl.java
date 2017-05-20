package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.WorksDao;
import cn.edu.nju.software.entity.Works;
import cn.edu.nju.software.service.WorksService;
import cn.edu.nju.software.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class WorksServiceImpl implements WorksService {

    @Autowired
    private WorksDao worksDao;

    @Override
    public boolean saveWorks(Works works) {
        return worksDao.saveWorks(works);
    }

    @Override
    public boolean updateWorks(Works works) {
        works.setUpdateTime(new Date());
        return worksDao.updateWorks(works);
    }

    @Override
    public boolean deleteWorksById(int id) {
        return worksDao.deleteWorksById(id);
    }

    @Override
    public boolean deleteWorks(int storyId, int userId) {
        return false;
    }

    @Override
    public List<Works> getWorksListByUserId(int userId, int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return worksDao.getWorksListByUserId(userId, offset, limit);
    }

    @Override
    public List<Works> getWorksListByStoryId(int storyId, int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return worksDao.getWorksListByStoryId(storyId, offset, limit);
    }

    @Override
    public List<Works> getWorksListByIdList(List<Integer> idList) {
        idList.add(-1);//防止mybatis查询出错
        return worksDao.getWorksListByIdList(idList);
    }
}
