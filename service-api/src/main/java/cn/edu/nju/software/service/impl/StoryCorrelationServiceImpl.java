package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.StoryCorrelationDao;
import cn.edu.nju.software.entity.StoryCorrelation;
import cn.edu.nju.software.service.StoryCorrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class StoryCorrelationServiceImpl implements StoryCorrelationService {

    @Autowired
    private StoryCorrelationDao storyCorrelationDao;

    @Override
    public boolean saveOrUpdateStoryCorrelation(StoryCorrelation storyCorrelation) {
        StoryCorrelation _storyCorrelation = storyCorrelationDao.getByPrimaryKey(storyCorrelation.getStoryIdA(), storyCorrelation.getStoryIdB());
        if (_storyCorrelation == null){
            return storyCorrelationDao.saveStoryCorrelation(storyCorrelation);
        }else {
            storyCorrelation.setId(_storyCorrelation.getId());
            return storyCorrelationDao.updateStoryCorrelation(storyCorrelation);
        }
    }

    @Override
    public List<StoryCorrelation> getCorrelationListByStoryId(int storyId) {
        return storyCorrelationDao.getByStoryId(storyId);
    }

    @Override
    public boolean deleteStoryCorrelationById(int id) {
        return storyCorrelationDao.deleteStoryCorrelationById(id);
    }


}
