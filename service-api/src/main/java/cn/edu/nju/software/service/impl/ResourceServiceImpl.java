package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.ResourceDao;
import cn.edu.nju.software.entity.Resource;
import cn.edu.nju.software.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;


    @Override
    public Resource saveResource(Resource resource) {
        if (resourceDao.saveResource(resource)) {
            return resource;
        }
        return null;
    }

    @Override
    public boolean deleteResource(int id) {
        return resourceDao.deleteResource(id);
    }

    @Override
    public Resource getResourceById(int id) {
        return resourceDao.getResourceById(id);
    }

    @Override
    public List<Resource> getAllResourceByPage(int page, int pageSize) {
        int offset = page*pageSize;
        int limit = pageSize;
        return resourceDao.getAllResourceByPage(offset, limit);
    }

    @Override
    public int getAllResourceCount() {
        return resourceDao.getAllResourceCount();
    }
}
