package cn.edu.nju.software.controller.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.software.entity.*;
import cn.edu.nju.software.service.*;
import cn.edu.nju.software.vo.ShareWorkWithreadPlan;
import cn.edu.nju.software.vo.StoryWithIntroduction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.util.TokenConfig;
import cn.edu.nju.software.vo.StoryNewWorksVo;
import sun.security.provider.SHA;

/**
 * @author zs
 * @version 创建时间：2017年9月12日 上午11:23:37
 */

@Api("ReadPlan controller")
@Controller
@RequestMapping("/user")
public class UserReadPlanController extends BaseController {
    @Autowired
    ReadPlanService readPlanService;
    @Autowired
    ReadPlanStoryGroupService readPlanStoryGroupService;
    @Autowired
    private BabyService babyService;
    @Autowired
    TagRelationService tagRelationService;
    @Autowired
    StoryTagService storyTagService;
    @Autowired
    WorksService worksService;
    @Autowired
    StoryService storyService;
    @Autowired
    UserService userService;

    @ApiOperation(value = "获取用户的阅读计划", notes = "需要登录")
    @RequestMapping(value = "/getReadPlanByUser", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<ReadingPlan>> getReadPlanByUser(HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<ReadingPlan>> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        // 根据用户id获取用户选中的宝宝
        Baby baby = babyService.getSelectedBaby(user.getId());
        //为空说明没有添加宝宝或者没有选中任何宝宝
        if (baby == null) {
            List<Baby> babyList = babyService.getBabyListByParentId(user.getId());
            //没有注册宝宝或者用户不存在
            if (babyList == null || babyList.isEmpty()) {
                responseData.jsonFill(2, "用户没有宝宝或者用户不存在", null);
                return responseData;
            } else {
                List<ReadingPlan> list = readPlanService.getReadingPlanByTime(babyList.get(0));
                responseData.jsonFill(1, null, list);
                return responseData;
            }
        }
        List<ReadingPlan> list = readPlanService.getReadingPlanByTime(baby);
        responseData.jsonFill(1, null, list);
        return responseData;
    }

    @ApiOperation(value = "分享阅读计划的中的作品(网页分享)")
    @RequestMapping(value = "/shareWorksForReadPlan", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<ShareWorkWithreadPlan> shareWorksForReadPlan(
            @ApiParam("作品id") @RequestParam Integer workId,
            @ApiParam("用户id") @RequestParam Integer userId) {
        ResponseData<ShareWorkWithreadPlan> responseData = new ResponseData<>();

        User user = userService.getUserById(userId);
        if (user == null) {
            responseData.jsonFill(2, "用户不存在", null);
            return responseData;
        }
        ShareWorkWithreadPlan shareWorkWithreadPlan = new ShareWorkWithreadPlan();
        shareWorkWithreadPlan.setUserName(user.getNickname());
        Works works = worksService.getWorksById(workId);
        if (works == null) {
            responseData.jsonFill(2, "作品不存在", null);
            return responseData;
        }
        shareWorkWithreadPlan.setWorks(works);

        // 根据用户id获取用户选中的宝宝
        Baby baby = babyService.getSelectedBaby(user.getId());
        //为空说明没有添加宝宝或者没有选中任何宝宝
        if (baby == null) {
            List<Baby> babyList = babyService.getBabyListByParentId(user.getId());
            //没有注册宝宝
            if (babyList == null || babyList.isEmpty()) {
                responseData.jsonFill(2, "用户没有宝宝", null);
                return responseData;
            } else {//注册了宝宝，但是没有选中宝宝的情况。默认根据第一个宝宝推送阅读计划
                List<ReadingPlan> readPlanlist = readPlanService.getReadingPlanByTime(babyList.get(0));
                //阅读计划中的其中一个故事
                ReadingPlanStoryGroup storyGroup = readPlanStoryGroupService.getReadPlanStoryByIdAndStory(readPlanlist.get(0).getId(), works.getStoryId());
                shareWorkWithreadPlan.setDay(storyGroup.getMyorder());
                responseData.jsonFill(1, null, shareWorkWithreadPlan);
                return responseData;
            }
        }
        List<ReadingPlan> list = readPlanService.getReadingPlanByTime(baby);
        ReadingPlanStoryGroup storyGroup = readPlanStoryGroupService.getReadPlanStoryByIdAndStory(list.get(0).getId(), works.getStoryId());
        shareWorkWithreadPlan.setDay(storyGroup.getMyorder());
        responseData.jsonFill(1, null, shareWorkWithreadPlan);
        return responseData;
    }

    @ApiOperation(value = "判断该作品是否是属于阅读计划里的")
    @RequestMapping(value = "/worksBeLongToReadPlan", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<ShareWorkWithreadPlan> worksBeLongToReadPlan(
            @ApiParam("作品id") @RequestParam Integer workId, HttpServletRequest request, HttpServletResponse response) {

        ResponseData<ShareWorkWithreadPlan> responseData = new ResponseData<>();

        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }

        ShareWorkWithreadPlan shareWorkWithreadPlan = new ShareWorkWithreadPlan();
        shareWorkWithreadPlan.setUserName(user.getNickname());
        Works works = worksService.getWorksById(workId);
        if (works == null) {
            responseData.jsonFill(2, "作品不存在", null);
            return responseData;
        }
        shareWorkWithreadPlan.setWorks(works);

        // 根据用户id获取用户选中的宝宝
        Baby baby = babyService.getSelectedBaby(user.getId());
        //为空说明没有添加宝宝或者没有选中任何宝宝
        if (baby == null) {
            List<Baby> babyList = babyService.getBabyListByParentId(user.getId());
            //没有注册宝宝或者用户不存在
            if (babyList == null || babyList.isEmpty()) {
                responseData.jsonFill(2, "用户没有宝宝", null);
                return responseData;
            } else {//注册了宝宝，但是没有选中宝宝的情况。默认根据第一个宝宝推送阅读计划
                List<ReadingPlan> readPlanlist = readPlanService.getReadingPlanByTime(babyList.get(0));
                //阅读计划中的其中一个故事
                ReadingPlanStoryGroup storyGroup = readPlanStoryGroupService.getReadPlanStoryByIdAndStory(readPlanlist.get(0).getId(), works.getStoryId());
                shareWorkWithreadPlan.setDay(storyGroup.getMyorder());
                responseData.jsonFill(1, null, shareWorkWithreadPlan);
                return responseData;
            }
        }
        List<ReadingPlan> list = readPlanService.getReadingPlanByTime(baby);
        ReadingPlanStoryGroup storyGroup = readPlanStoryGroupService.getReadPlanStoryByIdAndStory(list.get(0).getId(), works.getStoryId());
        shareWorkWithreadPlan.setDay(storyGroup.getMyorder());
        responseData.jsonFill(1, null, shareWorkWithreadPlan);
        return responseData;
    }


    @ApiOperation(value = "根据阅读计划id查询故事组")
    @RequestMapping(value = "/getStoryGroupByPlanId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<StoryNewWorksVo>> getStoryGroupByPlanId(
            @ApiParam("阅读计划id") @RequestParam Integer ReadingPlanId, HttpServletRequest request,
            HttpServletResponse response) {
        ResponseData<List<StoryNewWorksVo>> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        List<ReadingPlanStoryGroup> list = readPlanStoryGroupService.getReadPlanStoryGroupByPlanId(ReadingPlanId);
        List<StoryNewWorksVo> storyNewWorksVoList = new ArrayList<StoryNewWorksVo>();
        for (ReadingPlanStoryGroup readingPlanStoryGroup : list) {
            StoryWithIntroduction storyWithIntroduction = storyService.getStoryByIdWithIntroduction(readingPlanStoryGroup.getStoryid());
            StoryNewWorksVo storyNewWorksVo = new StoryNewWorksVo();
            storyNewWorksVo = story2Vo(storyWithIntroduction, user.getId());

            storyNewWorksVoList.add(storyNewWorksVo);
        }
        responseData.jsonFill(1, null, storyNewWorksVoList);
        return responseData;
    }

    // 根据故事和用户id获得一个故事vo类(故事的标签，用户是否完成过这个故事)
    private StoryNewWorksVo story2Vo(Story story, int userId) {
        if (story == null) {
            return null;
        }
        StoryNewWorksVo storyWorkVo = new StoryNewWorksVo();
        List<Integer> idList = tagRelationService.getTagIdListByStoryId(story.getId());
        List<StoryTag> storyTagList = storyTagService.getStoryTagListByIdList(idList);
        storyWorkVo.setTagList(storyTagList);
        BeanUtils.copyProperties(story, storyWorkVo);
        if (userId > 0) {
            boolean finish = worksService.getWorksByUserAndStory(userId, story.getId());
            storyWorkVo.setFinish(finish);
        }
        return storyWorkVo;
    }
}