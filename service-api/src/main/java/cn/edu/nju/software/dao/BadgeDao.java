package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Badge;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface BadgeDao {

    boolean saveBadge(Badge badge);

    boolean updateBadge(Badge badge);

    boolean deleteBadge(int id);

    Badge getBadgeById(int id);

    List<Badge> getBadgeListByTypeId(int typeId);

    List<Badge> getAllBadgeByPage(int offset, int limit);
    
    List<Badge> getBadgeOfUser(Integer userId);

}
