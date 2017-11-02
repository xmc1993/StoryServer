package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface ResourceDao {

    boolean saveResource(Resource resource);

    boolean deleteResource(int id);

    Resource getResourceById(int id);

    List<Resource> getAllResourceByPage(int offset, int limit);

}
