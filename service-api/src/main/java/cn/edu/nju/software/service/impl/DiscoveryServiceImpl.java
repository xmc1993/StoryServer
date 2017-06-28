package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.DiscoveryDao;
import cn.edu.nju.software.entity.Discovery;
import cn.edu.nju.software.service.DiscoveryService;
import cn.edu.nju.software.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Kt on 2017/6/26.
 */
@Service
public class DiscoveryServiceImpl implements DiscoveryService {
    @Autowired
    private DiscoveryDao discoveryDao;
    @Override
    public boolean saveDiscovery(Discovery discovery){
        return discoveryDao.saveDiscovery(discovery);
    }
    @Override
    public boolean updateDiscovery(Discovery discovery){
        return discoveryDao.updateDiscovery(discovery);
    }
    @Override
    public Discovery getDiscoveryById(int id){
        return discoveryDao.getDiscoveryById(id);
    }
    @Override
    public boolean deleteDiscovery(int id){
        return discoveryDao.deleteDiscoveryById(id);

    }
    @Override
    public boolean deleteDiscoveryByIdList(int[] idList){
        if(idList.length == discoveryDao.deleteDiscoveryByIdList(idList))
            return true;
        return false;
    }
    @Override
    public List<Discovery> getDiscoveryByRandomPage(int offset, int limit, int[] excludeIdList){
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return discoveryDao.getDiscoveryByRandomPage(offset,limit,excludeIdList);
    }
    @Override
    public List<Discovery> getDiscoveryByCreateTimeDescPage(int offset, int limit){
        offset = offset < 0 ? Const.DEFAULT_OFFSET : offset;
        limit = limit < 0 ? Const.DEFAULT_LIMIT : limit;
        return discoveryDao.getDiscoveryByCreateTimeDescPage(offset,limit);
    }
    @Override
    public int getDiscoveryCount(){
        return discoveryDao.getDiscoveryCount();
    }
}
