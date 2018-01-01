package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.BabyReadPlanDao;
import cn.edu.nju.software.dao.user.AppUserDao;
import cn.edu.nju.software.entity.*;
import cn.edu.nju.software.service.*;
import cn.edu.nju.software.service.user.AppUserService;
import cn.edu.nju.software.util.Const;
import cn.edu.nju.software.util.DateUtil;
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
    private AppUserDao appUserDao;
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
    private BabyReadPlanDao babyReadPlanDao;
    @Autowired
    private BabyService babyService;
    @Autowired
    private ReadPlanStoryGroupService readPlanStoryGroupService;
    @Autowired
    private StoryTopicServcie storyTopicServcie;
    @Autowired
    private FollowService followService;

    //抽取封装的逻辑代码
    public void packageBadgeLogic(List<Badge> badges, Integer userId, Integer badgeId) {
        UserBadge userBadge = new UserBadge();
        userBadge.setUserId(userId);
        userBadge.setBadgeId(badgeId);
        userBadgeService.saveUserBadge(userBadge);
        Badge badge = badgeService.getBadgeById(badgeId);
        badges.add(badge);
    }

    //触发专题故事阅读徽章的复用方法
/*    private void topicStoryReadingBadge(Integer badgeId,Integer userId,Integer storyTopicId){
        if (userBadgeService.getUserBadge(Const.DOUBLE_ELEVEN_BADGE_ID, userId) == null) {
            List<StoryTopicRelation> list = storyTopicServcie.getStoryListByTopicId(Const.DOUBLE_ELEVEN_STORY_TOPIC);
            for (StoryTopicRelation storyTopicRelation : list) {
                Integer storyId = storyTopicRelation.getstoryId();
                if (works.getStoryId().equals(storyId)) {
                    packageBadgeLogic(badges,userId,Const.DOUBLE_ELEVEN_BADGE_ID);
                    break;
                }
            }
        }
    }*/

    @Override
    public List<Badge> judgeAddBadgesWhenPublish(User user, Works works) {
        List<Badge> badges = new ArrayList<>();
        int[] workCountBadgeArr = {5000, 2000, 1000, 500, 300, 200, 150, 100, 30, 10, 3};
        int[] readDayCountBadgeArr = {1, 3, 7, 15, 21, 30, 50, 100, 200, 365, 500, 1000};

        List<Integer> tagIdList = tagRelationService.getTagIdListByStoryId(works.getStoryId());

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String format = sf.format(date);
        Integer userId = user.getId();

        // 小小爱国徽章
        // 如果今天是十月一
        if ("20171001".equals(format)) {
            // 如果今天有上传过作品
            //if (worksService.getWorksAfterSomeDate(userId, "2017-10-1 00:00:00") > 0) {}
            // 如果没有拥有过这个徽章
            if (userBadgeService.getUserBadge(Const.PATRIOT_BADGE_ID, userId) == null) {
                packageBadgeLogic(badges, userId, Const.PATRIOT_BADGE_ID);
            }
        }

        // "感谢有你"徽章
        if ("20171123".equals(format)) {
            // 如果没有拥有过这个徽章
            if (userBadgeService.getUserBadge(Const.Thinking_BADGE_ID, userId) == null) {
                packageBadgeLogic(badges, userId, Const.Thinking_BADGE_ID);
            }
        }


        // 魔法骑士
        //zj:如果上传的作品对应的故事对应的tag列表中包含“奇幻故事”标签
        if (tagIdList.contains(Const.FANTASY_STORY_TAG_ID)) {
            if (userBadgeService.getUserBadge(Const.MAGIC_KNIGHT_BADGE_ID, userId) == null) {
                packageBadgeLogic(badges, userId, Const.MAGIC_KNIGHT_BADGE_ID);
            }
        }

        // 温馨陪伴
        if (user.getWorkCount() == 1) {
            if (userBadgeService.getUserBadge(Const.WARM_COMPANY_BADGE_ID, userId) == null) {
                packageBadgeLogic(badges, userId, Const.WARM_COMPANY_BADGE_ID);
            }
        }

        // 最高连续阅读天数
        int maxDays = recordStatisticService.getHistoryMaxCount(userId);
        // 49的id是连续阅读一天的ID,后面依次类推
        for (int i = 0; i < readDayCountBadgeArr.length; i++) {
            if (maxDays == readDayCountBadgeArr[i]) {
                Badge badge = badgeService.getBadgeByMeasureAndType(readDayCountBadgeArr[i], 6);
                Integer badgeId = badge.getId();
                if (userBadgeService.getUserBadge(badgeId, userId) == null) {
                    UserBadge userBadge = new UserBadge();
                    userBadge.setUserId(userId);
                    userBadge.setBadgeId(badgeId);
                    userBadgeService.saveUserBadge(userBadge);
                    badges.add(badge);
                }
            }
        }


        // 作品数量徽章
        for (int i = 0; i < workCountBadgeArr.length; i++) {
            //因为他上传了故事但缓存中没有变
            if (user.getWorkCount() + 1 == workCountBadgeArr[i]) {
                UserBadge userBadge = new UserBadge();
                userBadge.setUserId(userId);
                Badge badge = badgeService.getBadgeByMeasureAndType(workCountBadgeArr[i], 5);
                userBadge.setBadgeId(badge.getId());
                userBadgeService.saveUserBadge(userBadge);
                badges.add(badge);
            }
        }

        //阅读计划完成前7天的徽章
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
                //判断添加第一次按照计划阅读的徽章
                if (finish && userBadgeService.getUserBadge(Const.FIRST_READING_BY_PLAN_BADGE_ID, userId) == null) {
                    packageBadgeLogic(badges, userId, Const.FIRST_READING_BY_PLAN_BADGE_ID);
                }
            }
        } else {//没有宝宝就没有阅读计划
            flag2 = false;
        }

        //当前7条完成时
        if (flag2) {
            boolean flag = true;
            //如果没有获得过这个徽章
            if (userBadgeService.getUserBadge(Const.WEEK_READING_PLAN_BADGE_ID, userId) == null) {
                packageBadgeLogic(badges, userId, Const.WEEK_READING_PLAN_BADGE_ID);
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
                if (userBadgeService.getUserBadge(Const.MONTH_READING_PLAN_BADGE_ID, userId) == null) {
                    packageBadgeLogic(badges, userId, Const.MONTH_READING_PLAN_BADGE_ID);
                }
            }
        }

        //专题故事徽章-双十一专题徽章
        if (userBadgeService.getUserBadge(Const.DOUBLE_ELEVEN_BADGE_ID, userId) == null) {
            List<StoryTopicRelation> list = storyTopicServcie.getStoryListByTopicId(Const.DOUBLE_ELEVEN_STORY_TOPIC);
            for (StoryTopicRelation storyTopicRelation : list) {
                Integer storyId = storyTopicRelation.getstoryId();
                if (works.getStoryId().equals(storyId)) {
                    packageBadgeLogic(badges, userId, Const.DOUBLE_ELEVEN_BADGE_ID);
                    break;
                }
            }
        }

        //专题故事徽章-孕期专题徽章
        if (userBadgeService.getUserBadge(Const.PREGNANCY_READING_BADGE_ID, userId) == null) {
            List<StoryTopicRelation> list = storyTopicServcie.getStoryListByTopicId(Const.PREGNANCY_STORY_TOPIC);
            for (StoryTopicRelation storyTopicRelation : list) {
                Integer storyId = storyTopicRelation.getstoryId();
                if (works.getStoryId().equals(storyId)) {
                    packageBadgeLogic(badges, userId, Const.PREGNANCY_READING_BADGE_ID);
                    break;
                }
            }
        }

        //生日徽章
        if (baby != null) {
            Date birthday = baby.getBirthday();
            boolean res = new DateUtil().judgeBirthDay(birthday);
            if (res) {
                //如果没有获得过这个徽章
                if (userBadgeService.getUserBadge(Const.BABY_BIRTHDAY_BADGE_ID, userId) == null) {
                    packageBadgeLogic(badges, userId, Const.BABY_BIRTHDAY_BADGE_ID);
                }
            }
        }

        //2018新年徽章
        int day = new DateUtil().dayOfYear();
        if (day >= 1 && day <= 5) {
            if (userBadgeService.getUserBadge(Const.NEW_YEAR_BADGE_ID, userId) == null) {
                packageBadgeLogic(badges, userId, Const.NEW_YEAR_BADGE_ID);
            }
        }

        //圣诞节徽章
        DateUtil dateUtil = new DateUtil();
        if (dateUtil.monthOfYear() == 12 && dateUtil.dayOfMonth() == 25) {
            if (userBadgeService.getUserBadge(Const.CHRISTMAS_BADGE_ID, userId) == null) {
                packageBadgeLogic(badges, userId, Const.CHRISTMAS_BADGE_ID);
            }
        }


        return badges;
    }

    //判断添加第一次分享徽章
    @Override
    public ResponseData<Boolean> judgeAddFirstShareBadge(Integer userId) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        List<Badge> badges = new ArrayList<>();
        if (userBadgeService.getUserBadge(Const.FIRST_SHARE_BADGE_ID, userId) == null) {
            packageBadgeLogic(badges, userId, Const.FIRST_SHARE_BADGE_ID);
            responseData.setBadgeList(badges);
            responseData.jsonFill(1, null, true);
        } else {
            responseData.jsonFill(1, null, false);
        }
        return responseData;
    }

    //判断添加第一次添加原创故事徽章
    @Override
    public Badge judgeAddFirstAddOriginalStoryBadge(Integer userId) {
        List<Badge> badges = new ArrayList<>();
        if (userBadgeService.getUserBadge(Const.FIRST_ADD_ORIGINAL_STORY_BADGE_ID, userId) == null) {
            packageBadgeLogic(badges, userId, Const.FIRST_ADD_ORIGINAL_STORY_BADGE_ID);
            return badges.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Badge judgeAddFirstFollowBadge(Integer userId, Integer followeeId) {
        //第一次被关注
        Integer followedCount = followService.getUserFollowerCountByUserId(followeeId);
        if (followedCount == 1) {
            UserBadge userBadge = new UserBadge();
            userBadge.setUserId(followeeId);
            userBadge.setBadgeId(Const.FIRST_FOLLOWED_BADGE_ID);
            userBadgeService.saveUserBadge(userBadge);
        }
        //第一次关注别人
        List<Badge> badges = new ArrayList<>();
        if (userBadgeService.getUserBadge(Const.FIRST_FOLLOW_BADGE_ID, userId) == null) {
            packageBadgeLogic(badges, userId, Const.FIRST_FOLLOW_BADGE_ID);
            return badges.get(0);
        } else {
            return null;
        }
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
        Integer userId = user.getId();

        if ("20171001".equals(format)) {
            // 如果今天有收听过故事
            //if (workUserLogService.getLogAfterSomeDate(userId, "2017-10-1 00:00:00") > 0) {}
            // 如果没有拥有过这个徽章,这个if是从上一个if里提取出来的，控制器中收听接口中收听成功了才调用的这个方法，所以这里不用

            //如果没有获得过这个徽章
            if (userBadgeService.getUserBadge(Const.PATRIOT_BADGE_ID, userId) == null) {
                packageBadgeLogic(badges, userId, Const.PATRIOT_BADGE_ID);
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

    /*//这个是后台人员瞎改数据后，刷出收听次数的服务，没啥用，不用看
    @Override
    public Boolean freshBadge() {
        List<User> list = appUserDao.getAllUserList();
        for (User user : list) {
        Integer userId = user.getId();
            if (user.getListenCount() < 100 && user.getListenCount() >= 10) {
                if (userBadgeService.getUserBadge(23, userId) == null) {
                    UserBadge userBadge = new UserBadge();
                    userBadge.setUserId(userId);
                    userBadge.setBadgeId(23);
                    userBadgeService.saveUserBadge(userBadge);
                }
            } else if (user.getListenCount() < 1000 && user.getListenCount() >= 100) {
                if (userBadgeService.getUserBadge(24, userId) == null) {
                    UserBadge userBadge = new UserBadge();
                    userBadge.setUserId(userId);
                    userBadge.setBadgeId(24);
                    userBadgeService.saveUserBadge(userBadge);
                }
            } else if (user.getListenCount() < 2000 && user.getListenCount() >= 1000) {
                if (userBadgeService.getUserBadge(25, userId) == null) {
                    UserBadge userBadge = new UserBadge();
                    userBadge.setUserId(userId);
                    userBadge.setBadgeId(25);
                    userBadgeService.saveUserBadge(userBadge);
                }
            } else if (user.getListenCount() < 5000 && user.getListenCount() >= 2000) {
                if (userBadgeService.getUserBadge(27, userId) == null) {
                    UserBadge userBadge = new UserBadge();
                    userBadge.setUserId(userId);
                    userBadge.setBadgeId(27);
                    userBadgeService.saveUserBadge(userBadge);
                }
            } else if (user.getListenCount() < 10000 && user.getListenCount() >= 5000) {
                if (userBadgeService.getUserBadge(28, userId) == null) {
                    UserBadge userBadge = new UserBadge();
                    userBadge.setUserId(userId);
                    userBadge.setBadgeId(28);
                    userBadgeService.saveUserBadge(userBadge);
                }
            }

        }
        return true;
    }*/
}
