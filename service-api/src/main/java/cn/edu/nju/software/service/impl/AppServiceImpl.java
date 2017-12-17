package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.AppDao;
import cn.edu.nju.software.entity.App;
import cn.edu.nju.software.service.AppService;
import cn.edu.nju.software.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Kt on 2017/6/25.
 */
@Service
public class AppServiceImpl implements AppService {
    @Autowired
    private AppDao appDao;

    @Override
    public boolean saveApp(App app) {
        return appDao.insertApp(app);
    }

    @Override
    public boolean updateApp(App app) {
        return appDao.updateApp(app);
    }

    @Override
    public boolean deleteApp(int id) {
        return appDao.deleteAppById(id);
    }

    @Override
    public boolean deleteAppByIdList(int[] idList) {
        if (appDao.deleteAppByIdList(idList) == idList.length) return true;
        else return false;
    }

    @Override
    public App getAppById(int id) {
        return appDao.getAppById(id);
    }

    @Override
    public App getNewApp() {
        List<App> appList = appDao.getAppListByPageDesc(0, 1);
        return appList.get(0);
    }

    @Override
    public List<App> getAppListByIdList(int[] idList) {
        return appDao.getAppListByIdList(idList);
    }

    @Override
    public List<App> getAppListByPageDesc(int offset, int limit) {
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return appDao.getAppListByPageDesc(offset, limit);
    }

    @Override
    public Integer getAppCount() {
        return appDao.getAppCount();
    }

    @Override
    public App getLastApp() {
        return appDao.getLastApp();
    }

    @CacheEvict(value = "appVersion", key = "'minAppVersion'")
    @Override
    public boolean updateMinLimitVersion(int versionId) {
        //先清除已经有的为1的flag 然后设置新的flag
        return appDao.resetAllLimitFlags() && appDao.setLimitFlag(versionId);
    }

    @Cacheable(value = "appVersion", key = "'minAppVersion'")
    @Override
    public App getMinLimitVersionApp() {
        App limitVersionApp = appDao.getLimitVersionApp();
        return limitVersionApp;
    }

    @Override
    public App getAppByVersion(String version) {
        return appDao.getAppByVersion(version);
    }

    @Override
    public String test() {
        return null;
    }

//    @Override
//    @Cacheable(value = "appVersion")
//    public String test() {
//        System.out.println("方法被调用了");
//        return "测试 啊啊啊啊啊啊啊啊啊";
//    }


}
