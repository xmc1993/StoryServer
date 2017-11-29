package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.entity.*;
import cn.edu.nju.software.service.*;
import cn.edu.nju.software.service.user.AppUserService;
import cn.edu.nju.software.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BadgeCheckServiceImpl implements BadgeCheckService {

    @Autowired
    private WorksService worksService;
    @Autowired
    private WorkUserLogService workUserLogService;
    @Autowired
    StoryTagService storyTagService;
    @Autowired
    TagRelationService tagRelationService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    BadgeService badgeService;
    @Autowired
    UserBadgeService userBadgeService;
    @Autowired
    private RecordStatisticService recordStatisticService;


    @Override
    public List<Badge> judgeUserAddBadgeByPublish(User user, Works works) {
        List<Badge> badges = new ArrayList<>();
        int[] workCountBadgeArr = {5000, 2000, 1000, 500, 300, 200, 150, 100, 30, 10, 3};

        List<Integer> tagIdList = tagRelationService.getTagIdListByStoryId(works.getStoryId());

        // 如果今天是十一
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String format = sf.format(date);

        // 小小爱国徽章
        if ("20171001".equals(format)) {
            // 如果今天有听过过故事
            if (workUserLogService.getLogAfterSomeDate(user.getId(), "2017-10-1 00:00:00") > 0) {
                // 如果没有拥有过这个徽章
                if (userBadgeService.getUserBadge(Const.PATRIOT_BADGE_ID, user.getId()) == null) {
                    UserBadge userBadge = new UserBadge();
                    userBadge.setUserId(user.getId());
                    userBadge.setBadgeId(Const.PATRIOT_BADGE_ID);
                    userBadgeService.saveUserBadge(userBadge);
                    Badge badge = badgeService.getBadgeById(Const.PATRIOT_BADGE_ID);
                    badges.add(badge);
                }
            }
        }

        // "感谢有你"徽章
        if ("20171123".equals(format)) {
            // 如果没有拥有过这个徽章
            if (userBadgeService.getUserBadge(Const.Thinking_BADGE_ID, user.getId()) == null) {
                UserBadge userBadge = new UserBadge();
                userBadge.setUserId(user.getId());
                userBadge.setBadgeId(Const.Thinking_BADGE_ID);
                userBadgeService.saveUserBadge(userBadge);
                Badge badge = badgeService.getBadgeById(Const.Thinking_BADGE_ID);
                badges.add(badge);
            }
        }


        // 魔法骑士
        if (tagIdList.contains(Const.FANTASY_STORY_TAG_ID)) {
            if (userBadgeService.getUserBadge(Const.MAGIC_KNIGHT_BADGE_ID, user.getId()) == null) {
                UserBadge userBadge = new UserBadge();
                userBadge.setUserId(user.getId());
                userBadge.setBadgeId(Const.MAGIC_KNIGHT_BADGE_ID);
                userBadgeService.saveUserBadge(userBadge);
                Badge badge = badgeService.getBadgeById(Const.MAGIC_KNIGHT_BADGE_ID);
                badges.add(badge);
            }
        }

        // 温馨陪伴
        if (user.getWorkCount() == 1) {
            if (userBadgeService.getUserBadge(Const.WARM_COMPANY_BADGE_ID, user.getId()) == null) {
                UserBadge userBadge = new UserBadge();
                userBadge.setUserId(user.getId());
                userBadge.setBadgeId(Const.WARM_COMPANY_BADGE_ID);
                userBadgeService.saveUserBadge(userBadge);
                Badge badge = badgeService.getBadgeById(Const.WARM_COMPANY_BADGE_ID);
                badges.add(badge);
            }
        }

        recordStatisticService.saveRecord(user.getId());
        // 最高连续阅读天数
        int maxDays = recordStatisticService.getHistoryMaxCount(user.getId());

        // 49的id是连续阅读一天的ID,后面依次类推
        if (maxDays < 3 && maxDays >= 0) {
            if (userBadgeService.getUserBadge(49, user.getId()) == null) {
                UserBadge userBadge = new UserBadge();
                userBadge.setUserId(user.getId());
                userBadge.setBadgeId(49);
                userBadgeService.saveUserBadge(userBadge);
                Badge badge = badgeService.getBadgeById(49);
                badges.add(badge);
            }

        } else if (maxDays < 7 && maxDays >= 3) {
            if (userBadgeService.getUserBadge(50, user.getId()) == null) {
                UserBadge userBadge = new UserBadge();
                userBadge.setUserId(user.getId());
                userBadge.setBadgeId(50);
                userBadgeService.saveUserBadge(userBadge);
                Badge badge = badgeService.getBadgeById(50);
                badges.add(badge);
            }

        } else if (maxDays < 15 && maxDays >= 7) {
            if (userBadgeService.getUserBadge(51, user.getId()) == null) {
                UserBadge userBadge = new UserBadge();
                userBadge.setUserId(user.getId());
                userBadge.setBadgeId(51);
                userBadgeService.saveUserBadge(userBadge);
                Badge badge = badgeService.getBadgeById(51);
                badges.add(badge);
            }
        } else if (maxDays < 21 && maxDays >= 15) {
            if (userBadgeService.getUserBadge(52, user.getId()) == null) {
                UserBadge userBadge = new UserBadge();
                userBadge.setUserId(user.getId());
                userBadge.setBadgeId(52);
                userBadgeService.saveUserBadge(userBadge);
                Badge badge = badgeService.getBadgeById(52);
                badges.add(badge);
            }
        }

        // 作品数量徽章
        for (int i = 0; i < workCountBadgeArr.length; i++) {
            //因为他上传了故事但缓存中没有变
            if (user.getWorkCount()+1 == workCountBadgeArr[i]) {
                UserBadge userBadge = new UserBadge();
                userBadge.setUserId(user.getId());
                Badge badge = badgeService.getBadgeByMeasureAndType(workCountBadgeArr[i], 5);
                userBadge.setBadgeId(badge.getId());
                userBadgeService.saveUserBadge(userBadge);
                badges.add(badge);
                return badges;
            }
        }
        return badges;
    }

    @Override
    public List<Badge> checkoutListenBadge(User user, Works works) {
        List<Badge> badges = new ArrayList<>();
        // 如果今天是十一
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String format = sf.format(date);
        if ("20171001".equals(format)) {
            // 如果今天有录制过故事
            if (worksService.getWorksAfterSomeDate(user.getId(), "2017-10-1 00:00:00") > 0) {
                // 如果没有拥有过这个徽章
                if (userBadgeService.getUserBadge(Const.PATRIOT_BADGE_ID, user.getId()) == null) {
                    UserBadge userBadge = new UserBadge();
                    userBadge.setUserId(user.getId());
                    userBadge.setBadgeId(Const.PATRIOT_BADGE_ID);
                    userBadgeService.saveUserBadge(userBadge);
                    Badge badge = badgeService.getBadgeById(Const.PATRIOT_BADGE_ID);
                    badges.add(badge);
                }
            }
        }
        return badges;
    }

    @Override
    public void judgeUserAddBadgeByListen(Integer authorId) {
        UserBase userBase = appUserService.getUserBaseById(authorId);
        int[] listenCounts = {1000000, 500000, 100000, 50000, 10000, 5000, 2000, 1000, 100, 10};
        for (int i = 0; i < listenCounts.length; i++) {
            if (userBase.getListenedCount() == listenCounts[i]) {
                UserBadge userBadge = new UserBadge();
                userBadge.setUserId(authorId);
                Badge badge = badgeService.getBadgeByMeasureAndType(listenCounts[i], 4);
                userBadge.setBadgeId(badge.getId());
                userBadgeService.saveUserBadge(userBadge);
            }
        }
    }
}
