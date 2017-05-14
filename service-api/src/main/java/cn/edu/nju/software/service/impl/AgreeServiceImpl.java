package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.AgreeDao;
import cn.edu.nju.software.dao.WorksDao;
import cn.edu.nju.software.dao.user.AppUserDao;
import cn.edu.nju.software.entity.Agree;
import cn.edu.nju.software.entity.UserBase;
import cn.edu.nju.software.service.AgreeService;
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
        return agreeDao.saveAgree(agree);
    }

    @Override
    public boolean deleteAgree(int worksId, int userId) {
        return agreeDao.deleteAgree(worksId, userId);
    }

    @Override
    public boolean deleteAgreeById(int id) {
        return agreeDao.deleteAgreeById(id);
    }

    @Override
    public List<UserBase> getAgreeUserIdListByWorksId(int worksId) {
        List<Integer> userIdList = agreeDao.getAgreeUserIdListByWorksId(worksId);
        userIdList.add(-1);//防止空数组使mybatis查询出错
        return appUserDao.getUserBaseListByUserIdList(userIdList);
    }
}
