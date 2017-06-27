package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Discovery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kt on 2017/6/26.
 */
@Repository
public interface DiscoveryDao {
     boolean saveDiscovery(@Param("discovery") Discovery discovery);
    boolean updateDiscovery(@Param("discovery") Discovery discovery);
    boolean deleteDiscoveryById(@Param("id") int id);
    int deleteDiscoveryByIdList(@Param("idList") int[] idList);
    List<Discovery> getDiscoveryByRandomPage(@Param("offset") int offset, @Param("limit") int limit, @Param("excludeIdList") int[] excludeIdList);
    List<Discovery> getDiscoveryByCreateTimeDescPage(@Param("offset") int offset, @Param("limit") int limit);

    Discovery getDiscoveryById(@Param("id") int id);

    int getDiscoveryCount();
}
