package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.SystemNotice;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface SystemNoticeService {

    SystemNotice saveSystemNotice(SystemNotice systemNotice);

    boolean updateSystemNotice(SystemNotice systemNotice);

    boolean deleteSystemNotice(int id);

    SystemNotice getSystemNoticeById(int id);

    List<SystemNotice> getAllSystemNoticeByPage(int page, int pageSize);

}
