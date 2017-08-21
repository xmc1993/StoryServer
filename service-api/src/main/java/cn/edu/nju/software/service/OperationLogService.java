package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.OperationLog;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface OperationLogService {

    OperationLog saveOperationLog(OperationLog operationLog);

    boolean deleteOperationLog(int id);

    OperationLog getOperationLogById(int id);

    List<OperationLog> getOperationLogListByPage(int page, int pageSize);

}
