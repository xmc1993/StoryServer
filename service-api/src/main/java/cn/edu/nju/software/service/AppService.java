package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.App;

import java.util.List;

/**
 * Created by Kt on 2017/6/25.
 */
public interface AppService {
    boolean saveApp(App app);

    boolean updateApp(App app);

    boolean deleteApp(int id);

    boolean deleteAppByIdList(int[] idList);

    App getAppById(int id);

    App getNewApp();

    List<App> getAppListByIdList(int[] idList);

    List<App> getAppListByPageDesc(int offset, int limit);

    Integer getAppCount();

    App getLastApp();

    boolean updateMinLimitVersion(int versionId);

    App getMinLimitVersionApp();

    App getAppByVersion(String version);

    String test();
}
