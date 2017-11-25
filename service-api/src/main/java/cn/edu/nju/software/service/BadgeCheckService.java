package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Badge;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.entity.Works;

import java.util.List;

public interface BadgeCheckService {

    List<Badge> judgeUserAddBadgeByPublish(User user, Works works);

    List<Badge> checkoutListenBadge(User user, Works works);

    void judgeUserAddBadgeByListen(Integer authorId);
}
