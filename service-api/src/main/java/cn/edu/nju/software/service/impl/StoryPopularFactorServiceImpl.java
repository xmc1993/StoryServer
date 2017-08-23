package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.StoryPopularFactorDao;
import cn.edu.nju.software.entity.StoryPopularFactor;
import cn.edu.nju.software.service.StoryPopularFactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class StoryPopularFactorServiceImpl implements StoryPopularFactorService {

    @Autowired
    private StoryPopularFactorDao storyPopularFactorDao;


    @Override
    public StoryPopularFactor saveStoryPopularFactor(StoryPopularFactor storyPopularFactor) {
        if (storyPopularFactorDao.saveStoryPopularFactor(storyPopularFactor)) {
            return storyPopularFactor;
        }
        return null;
    }

    @Override
    public boolean updateStoryPopularFactor(StoryPopularFactor storyPopularFactor) {
        return storyPopularFactorDao.updateStoryPopularFactor(storyPopularFactor);
    }

    @Override
    public boolean deleteStoryPopularFactor(int id) {
        return storyPopularFactorDao.deleteStoryPopularFactor(id);
    }

    @Override
    public StoryPopularFactor getStoryPopularFactorById(int id) {
        return storyPopularFactorDao.getStoryPopularFactorById(id);
    }

    @Override
    public List<StoryPopularFactor> getStoryPopularFactorListByTypeId(int typeId) {
        return storyPopularFactorDao.getStoryPopularFactorListByTypeId(typeId);
    }

    @Override
    public List<StoryPopularFactor> getAllStoryPopularFactorByPage(int page, int pageSize) {
        int offset = page*pageSize;
        int limit = pageSize;
        return storyPopularFactorDao.getAllStoryPopularFactorByPage(offset, limit);
    }

	@Override
	public List<StoryPopularFactor> getStoryPopularFactorOfUser(Integer userId) {
		return storyPopularFactorDao.getStoryPopularFactorOfUser(userId);
	}

}
