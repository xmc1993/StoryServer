package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.SystemNotice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface SystemNoticeDao {

    boolean saveSystemNotice(SystemNotice systemNotice);

    boolean updateSystemNotice(SystemNotice systemNotice);

    boolean deleteSystemNotice(int id);

    SystemNotice getSystemNoticeById(int id);

    List<SystemNotice> getAllSystemNoticeByPage(int offset, int limit);

	List<SystemNotice> getSystemNoticeList();
	
	Integer getAllSystemNoticeCount();
}
