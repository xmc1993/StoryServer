package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.StoryDao;
import cn.edu.nju.software.dao.WorksDao;
import cn.edu.nju.software.dao.user.AppUserDao;
import cn.edu.nju.software.entity.TwoTuple;
import cn.edu.nju.software.entity.Works;
import cn.edu.nju.software.service.WorksService;
import cn.edu.nju.software.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class WorksServiceImpl implements WorksService {

    @Autowired
    private WorksDao worksDao;
    @Autowired
    private StoryDao storyDao;
    @Autowired
    private AppUserDao appUserDao;

    @Override
    public boolean saveWorks(Works works) {
        boolean res = worksDao.saveWorks(works);
        if (res) {
            storyDao.newTell(works.getStoryId());
            appUserDao.newWork(works.getUserId());
        }
        return res;
    }

    @Override
    public boolean listenWorks(int worksId) {
        return worksDao.addListenCount(worksId);
    }

    @Override
    public boolean updateWorks(Works works) {
        works.setUpdateTime(new Date());
        return worksDao.updateWorks(works);
    }

    @Override
    public boolean deleteWorksById(int id) {
        boolean res = worksDao.deleteWorksById(id);
        if (res) {
            Works works = worksDao.getWorksByIdHard(id);
            storyDao.deleteTell(works.getStoryId());
            appUserDao.removeWork(works.getUserId());
        }
        return res;
    }

    @Override
    public Works getWorksById(int id) {
        return worksDao.getWorksById(id);
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

    @Override
    public Integer getWorksCount() {
        return worksDao.getWorksCount();
    }

    @Override
    public Integer getWorksCountByUserId(Integer userId) {
       return worksDao.getWorksCountByUserId(userId);
    }

    @Override
    public Integer getWorksCountByStoryId(Integer storyId){
        return worksDao.getWorksCountByStoryId(storyId);
    }

    @Override
    public Integer getWorksCountByIdList(List<Integer> idList) {
        idList.add(-1);//防止mybatis查询出错
        return worksDao.getWorksCountByIdList(idList);
    }

    @Override
    public List<Works> getLatestWorksByPage(int page, int pageSize) {
        return worksDao.getLatestWorksByPage(page*pageSize, pageSize);
    }

    @Override
    public List<Works> getMostPopularByPage(int page, int pageSize) {
        return worksDao.getMostPopularByPage(page*pageSize, pageSize);
    }

	@Override
	public List<TwoTuple<Integer,String>> getFirstWorkByPlayIdList(List<Integer> playIdList) {
		return worksDao.getFirstWorkByPlayIdList(playIdList);
	}

	@Override
	public PageInfo<Works> getWorksListByStoryIdListByPage(List<Integer> storyIdList, int page, int pageSize) {
		PageHelper.startPage(page+1, pageSize);
		List<Works> workList  = worksDao.getWorksListByStoryIdList(storyIdList);
		PageInfo<Works> pageInfo = new PageInfo<>(workList);
		return pageInfo;
	}

    @Override
    public Integer getUserIdByWorkId(int worksId) {
        return worksDao.getUserIdByWorkId(worksId);
    }

}
