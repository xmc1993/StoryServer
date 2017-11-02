package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Resource;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface ResourceService {

    Resource saveResource(Resource resource);

    boolean deleteResource(int id);

    Resource getResourceById(int id);

    List<Resource> getAllResourceByPage(int page, int pageSize);

}
