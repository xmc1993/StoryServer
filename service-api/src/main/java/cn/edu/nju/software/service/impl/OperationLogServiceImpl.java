package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.OperationLogDao;
import cn.edu.nju.software.entity.OperationLog;
import cn.edu.nju.software.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogDao operationLogDao;

    @Override
    public OperationLog saveOperationLog(OperationLog operationLog) {
        if (operationLogDao.saveOperationLog(operationLog)) {
            return operationLog;
        }
        return null;
    }

    @Override
    public boolean deleteOperationLog(int id) {
        return operationLogDao.deleteOperationLog(id);
    }

    @Override
    public OperationLog getOperationLogById(int id) {
        return operationLogDao.getOperationLogById(id);
    }

    @Override
    public List<OperationLog> getOperationLogListByPage(int page, int pageSize) {
        int offset = page*pageSize;
        int limit = pageSize;
        return operationLogDao.getOperationLogListByPage(offset, limit);
    }
}
