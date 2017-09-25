package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.SystemNoticeDao;
import cn.edu.nju.software.entity.SystemNotice;
import cn.edu.nju.software.service.SystemNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class SystemNoticeServiceImpl implements SystemNoticeService {

    @Autowired
    private SystemNoticeDao systemNoticeDao;


    @Override
    public SystemNotice saveSystemNotice(SystemNotice systemNotice) {
        if (systemNoticeDao.saveSystemNotice(systemNotice)) {
            return systemNotice;
        }
        return null;
    }

    @Override
    public boolean updateSystemNotice(SystemNotice systemNotice) {
        return systemNoticeDao.updateSystemNotice(systemNotice);
    }

    @Override
    public boolean deleteSystemNotice(int id) {
        return systemNoticeDao.deleteSystemNotice(id);
    }

    @Override
    public SystemNotice getSystemNoticeById(int id) {
        return systemNoticeDao.getSystemNoticeById(id);
    }

    @Override
    public List<SystemNotice> getAllSystemNoticeByPage(int page, int pageSize) {
        int offset = page*pageSize;
        int limit = pageSize;
        return systemNoticeDao.getAllSystemNoticeByPage(offset, limit);
    }

}
