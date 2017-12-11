package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.App;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kt on 2017/6/25.
 */
@Repository
public interface AppDao {
    boolean insertApp(@Param("app") App app);
    boolean updateApp(@Param("app") App app);
    boolean deleteAppById(@Param("id") int id);
    List<App> getAppListByPageDesc(@Param("offset") int offset, @Param("limit") int limit);
    List<App> getForceUpdateVersionAfterCurrentVersion(Integer id);
    Integer deleteAppByIdList(@Param("idList") int[] idList);

    App getAppById(@Param("id") int id);

    List<App> getAppListByIdList(@Param("idList") int[] idList);


    Integer getAppCount();

    //获得最新的App
    App getLastApp();
}
