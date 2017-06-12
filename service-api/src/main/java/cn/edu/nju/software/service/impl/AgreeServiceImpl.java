package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.AgreeDao;
import cn.edu.nju.software.dao.WorksDao;
import cn.edu.nju.software.dao.user.AppUserDao;
import cn.edu.nju.software.entity.Agree;
import cn.edu.nju.software.service.AgreeService;
import cn.edu.nju.software.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class AgreeServiceImpl implements AgreeService {
    @Autowired
    private AgreeDao agreeDao;
    @Autowired
    private WorksDao worksDao;
    @Autowired
    private AppUserDao appUserDao;

    @Override
    public boolean addAgree(Agree agree) {
        if (worksDao.getWorksById(agree.getWorksId()) == null) {
            return false;
        }
        boolean res1 = agreeDao.saveAgree(agree);
        boolean res2 = appUserDao.newLike(agree.getUserId());
        return res1 && res2;
    }

    @Override
    public boolean deleteAgree(int worksId, int userId) {
        boolean res1 = agreeDao.deleteAgree(worksId, userId);
        boolean res2 = appUserDao.removeLike(userId);
        return res1 && res2;
    }

    @Override
    public boolean deleteAgreeById(int id) {
        return agreeDao.deleteAgreeById(id);
    }

    @Override
    public List<Integer> getAgreeUserIdListByWorksId(int worksId, int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        List<Integer> userIdList = agreeDao.getAgreeUserIdListByWorksId(worksId);
        return userIdList;
    }

    @Override
    public List<Integer> getAgreeWorksIdListByUserId(int userId, int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return agreeDao.getAgreeWorksListByUserId(userId, offset, limit);
    }

    @Override
    public Agree getAgree(int userId, int worksId) {
        return agreeDao.getAgree(userId, worksId);
    }
}
