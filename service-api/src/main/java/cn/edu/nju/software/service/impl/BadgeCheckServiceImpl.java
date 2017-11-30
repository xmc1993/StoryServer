package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.BabyReadPlanDao;
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
    @Autowired
    private ReadPlanService readPlanService;
    @Autowired
    private BabyReadPlanDao babyReadPlanDao;
    @Autowired
    private BabyService babyService;
    @Autowired
    private ReadPlanStoryGroupService readPlanStoryGroupService;
    @Autowired
    private StoryTopicServcie storyTopicServcie;


    @Override
    public List<Badge> judgeAddBadgesWhenPublish(User user, Works works) {
        List<Badge> badges = new ArrayList<>();
        int[] workCountBadgeArr = {5000, 2000, 1000, 500, 300, 200, 150, 100, 30, 10, 3};
        int[] readDayCountBadgeArr = {1, 3, 7, 15, 21, 30, 50, 100, 200, 365, 500, 1000};

        List<Integer> tagIdList = tagRelationService.getTagIdListByStoryId(works.getStoryId());

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String format = sf.format(date);

        // 小小爱国徽章
        // 如果今天是十月一
        if ("20171001".equals(format)) {
            // 如果今天有上传过作品
            //if (worksService.getWorksAfterSomeDate(user.getId(), "2017-10-1 00:00:00") > 0) {}
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
        //zj:如果上传的作品对应的故事对应的tag列表中包含“奇幻故事”标签
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

        //刷新下阅读天数
        recordStatisticService.saveRecord(user.getId());
        // 最高连续阅读天数
        int maxDays = recordStatisticService.getHistoryMaxCount(user.getId());

        // 49的id是连续阅读一天的ID,后面依次类推
        for (int i = 0; i < readDayCountBadgeArr.length; i++) {
            if (maxDays == readDayCountBadgeArr[i]) {
                UserBadge userBadge = new UserBadge();
                userBadge.setUserId(user.getId());
                Badge badge = badgeService.getBadgeByMeasureAndType(readDayCountBadgeArr[i], 6);
                userBadge.setBadgeId(badge.getId());
                userBadgeService.saveUserBadge(userBadge);
                badges.add(badge);
            }
        }


        // 作品数量徽章
        for (int i = 0; i < workCountBadgeArr.length; i++) {
            //因为他上传了故事但缓存中没有变
            if (user.getWorkCount() + 1 == workCountBadgeArr[i]) {
                UserBadge userBadge = new UserBadge();
                userBadge.setUserId(user.getId());
                Badge badge = badgeService.getBadgeByMeasureAndType(workCountBadgeArr[i], 5);
                userBadge.setBadgeId(badge.getId());
                userBadgeService.saveUserBadge(userBadge);
                badges.add(badge);
                return badges;
            }
        }

        //阅读计划完成前7天的徽章
        Integer userId = user.getId();
        Baby baby = babyService.getUserOneBaby(userId);
        boolean flag2 = true;
        List<Integer> storyIdList = new ArrayList<>();
        if (baby != null) {
            Integer planId = babyReadPlanDao.getBabyReadPlanByBabyId(baby.getId()).getReadPlanId();
            storyIdList = readPlanStoryGroupService.getStoryIdListInReadPlanByPlanId(planId);
            //取前7条判断
            for (Integer storyId : storyIdList.subList(0, 7)) {
                boolean finish = worksService.getWorksByUserAndStory(userId, storyId);
                if (!finish) {
                    flag2 = false;
                    break;
                }
            }
        } else {
            flag2 = false;
        }

        //当前7条完成时
        if (flag2) {
            boolean flag = true;
            //如果没有获得过这个徽章
            if (userBadgeService.getUserBadge(Const.WEEK_READING_PLAN_BADGE_ID, user.getId()) == null) {
                UserBadge userBadge = new UserBadge();
                userBadge.setUserId(userId);
                userBadge.setBadgeId(Const.WEEK_READING_PLAN_BADGE_ID);
                userBadgeService.saveUserBadge(userBadge);
                Badge badge = badgeService.getBadgeById(Const.WEEK_READING_PLAN_BADGE_ID);
                badges.add(badge);
            }
            //阅读计划类徽章-完成该月计划徽章
            for (Integer storyId : storyIdList) {
                boolean finish = worksService.getWorksByUserAndStory(userId, storyId);
                if (!finish) {
                    flag = false;
                    break;
                }
            }
            //如果阅读计划全部完成
            if (flag) {
                //如果没有获得过这个徽章
                if (userBadgeService.getUserBadge(Const.MONTH_READING_PLAN_BADGE_ID, user.getId()) == null) {
                    UserBadge userBadge = new UserBadge();
                    userBadge.setUserId(userId);
                    userBadge.setBadgeId(Const.MONTH_READING_PLAN_BADGE_ID);
                    userBadgeService.saveUserBadge(userBadge);
                    Badge badge = badgeService.getBadgeById(Const.MONTH_READING_PLAN_BADGE_ID);
                    badges.add(badge);
                }
            }
        }


        //专题徽章
        if (userBadgeService.getUserBadge(Const.DOUBLE_ELEVEN_BADGE_ID, user.getId()) == null) {
            List<StoryTopicRelation> list = storyTopicServcie.getStoryListByTopicId(Const.DOUBLE_ELEVEN_STORY_TOPIC);
            for (StoryTopicRelation storyTopicRelation : list) {
                Integer storyId = storyTopicRelation.getstoryId();
                if (works.getStoryId().equals(storyId)) {
                    UserBadge userBadge = new UserBadge();
                    userBadge.setUserId(userId);
                    userBadge.setBadgeId(Const.DOUBLE_ELEVEN_BADGE_ID);
                    userBadgeService.saveUserBadge(userBadge);
                    Badge badge = badgeService.getBadgeById(Const.DOUBLE_ELEVEN_BADGE_ID);
                    badges.add(badge);
                    break;
                }
            }
        }

        return badges;


    }

    /**
     * 验证用户是否可以获取“小小爱国者徽章”。如果十月一日录制过故事且这天还没有获得过此徽章就触发
     */
    @Override
    public List<Badge> judgeAddBadgeWhenListen(User user) {
        List<Badge> badges = new ArrayList<>();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String format = sf.format(date);

        if ("20171001".equals(format)) {
            // 如果今天有收听过故事
            //if (workUserLogService.getLogAfterSomeDate(user.getId(), "2017-10-1 00:00:00") > 0) {}
            // 如果没有拥有过这个徽章,这个if是从上一个if里提取出来的，控制器中收听接口中收听成功了才调用的这个方法，所以这里不用

            //如果没有获得过这个徽章
            if (userBadgeService.getUserBadge(Const.PATRIOT_BADGE_ID, user.getId()) == null) {
                UserBadge userBadge = new UserBadge();
                userBadge.setUserId(user.getId());
                userBadge.setBadgeId(Const.PATRIOT_BADGE_ID);
                userBadgeService.saveUserBadge(userBadge);
                Badge badge = badgeService.getBadgeById(Const.PATRIOT_BADGE_ID);
                badges.add(badge);
            }
        }
        return badges;
    }

    /**
     * @param authorId 传入作者id判断作者是否可以获取“被收听徽章”
     */
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
