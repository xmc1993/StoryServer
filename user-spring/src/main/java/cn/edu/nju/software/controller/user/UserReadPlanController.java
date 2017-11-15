package cn.edu.nju.software.controller.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import cn.edu.nju.software.entity.Baby;
import cn.edu.nju.software.entity.ReadingPlan;
import cn.edu.nju.software.entity.ReadingPlanStoryGroup;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.StoryTag;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.BabyService;
import cn.edu.nju.software.service.ReadPlanService;
import cn.edu.nju.software.service.ReadPlanStoryGroupService;
import cn.edu.nju.software.service.StoryService;
import cn.edu.nju.software.service.StoryTagService;
import cn.edu.nju.software.service.TagRelationService;
import cn.edu.nju.software.service.WorksService;
import cn.edu.nju.software.util.TokenConfig;
import cn.edu.nju.software.vo.StoryNewWorksVo;

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

	@ApiOperation(value = "获取用户的阅读计划", notes = "需要登录")
	@RequestMapping(value = "/getReadPlanByUser", method = { RequestMethod.GET })
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
		if (baby == null) {
			List<Baby> babyList = babyService.getBabyListByParentId(user.getId());
			if (babyList == null||babyList.isEmpty()) {
				responseData.jsonFill(2, "用户没有宝宝或者用户不存在", null);
				return responseData;
			}
			List<ReadingPlan> list = getBabyReadPlan(babyList.get(0));
			responseData.jsonFill(1, null, list);
			return responseData;
		}
		List<ReadingPlan> list = getBabyReadPlan(baby);
		responseData.jsonFill(1, null, list);
		return responseData;
	}

	@ApiOperation(value = "根据阅读计划id查询故事组")
	@RequestMapping(value = "/getStoryGroupByPlanId", method = { RequestMethod.GET })
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
			Story story = storyService.getStoryById(readingPlanStoryGroup.getStoryid());
			StoryNewWorksVo storyNewWorksVo = new StoryNewWorksVo();
			storyNewWorksVo = story2Vo(story, user.getId());
			storyNewWorksVoList.add(storyNewWorksVo);
		}
		responseData.jsonFill(1, null, storyNewWorksVoList);
		return responseData;
	}

	// 方法抽取：根据baby生日获取宝宝的的阅读计划
	// 3个月：91天
	// 3-6个月：182天
	// 6-12个月：365天
	// 12-18个月：547天
	// 18-24个月：730天
	// 2-3岁：1095天
	// 3-4岁：1460天
	// 4-5岁：1825天
	// 5-6岁：2190天
	// 当宝宝年龄大于6岁时，小于7岁时，阅读计划给他返回 5-6岁的阅读计划
	// 还未出生的宝宝给他返回0-2岁的阅读计划
	private List<ReadingPlan> getBabyReadPlan(Baby baby) {
		// 获取当前时间
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		String babyBirthday = formatter.format(baby.getBirthday());

		// 获取当前年份和月份
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM");
		String timePoint = formatter2.format(currentTime);
		// 计算天数
		long days = 0;
		days = getDays(babyBirthday, dateString);
		if (days <= 730) {
			List<ReadingPlan> list = readPlanService.getReadingPlanByTime("0-2岁", timePoint);
			return list;
		} else if (days <= 1095 && days > 730) {
			List<ReadingPlan> list = readPlanService.getReadingPlanByTime("2-3岁", timePoint);
			return list;
		} else if (days <= 1460 && days > 1095) {
			List<ReadingPlan> list = readPlanService.getReadingPlanByTime("3-4岁", timePoint);
			return list;
		} else if (days <= 1825 && days > 1460) {
			List<ReadingPlan> list = readPlanService.getReadingPlanByTime("4-5岁", timePoint);
			return list;
		} else if (days <= 2190 && days > 1825) {
			List<ReadingPlan> list = readPlanService.getReadingPlanByTime("5-6岁", timePoint);
			return list;
		} else if (days <= 2555 && days > 2190) {
			List<ReadingPlan> list = readPlanService.getReadingPlanByTime("5-6岁", timePoint);
			return list;
		}
		return null;
	}

	// 方法抽取获取 获取俩段时间的天数 date1为开始时间
	private static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return Math.abs(day);
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
			boolean isWorks = worksService.getWorksByUserAndStory(userId, story.getId());
			storyWorkVo.setWorks(isWorks);
		}
		return storyWorkVo;
	}
}