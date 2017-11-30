package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Badge;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.entity.Works;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface BadgeCheckService {

    List<Badge> judgeAddBadgesWhenPublish(User user, Works works);

    List<Badge> judgeAddBadgeWhenListen(User user);

    void judgeUserAddBadgeByListen(Integer authorId);

    /*Boolean freshBadge();*/
}
