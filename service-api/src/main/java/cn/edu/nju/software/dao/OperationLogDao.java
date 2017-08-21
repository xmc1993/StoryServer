package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.OperationLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface OperationLogDao {

    boolean saveOperationLog(OperationLog operationLog);

    boolean deleteOperationLog(int id);

    OperationLog getOperationLogById(int id);

    List<OperationLog> getOperationLogListByPage(int offset, int limit);

}
