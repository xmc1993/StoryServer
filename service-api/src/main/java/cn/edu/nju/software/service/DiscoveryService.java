package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Discovery;

import java.util.List;

/**
 * Created by Kt on 2017/6/26.
 */
public interface DiscoveryService {
    boolean saveDiscovery(Discovery discovery);

    boolean updateDiscovery(Discovery discovery);

    Discovery getDiscoveryById(int id);

    boolean deleteDiscovery(int id);

    boolean deleteDiscoveryByIdList(int[] idList);

    List<Discovery> getDiscoveryByRandomPage(int offset, int limit, int[] excludeIdList);

    List<Discovery> getDiscoveryByCreateTimeDescPage(int offset, int limit);

    int getDiscoveryCount();
}
