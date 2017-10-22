package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.*;
import cn.edu.nju.software.service.*;
import cn.edu.nju.software.util.Const;
import cn.edu.nju.software.util.TokenConfig;
import cn.edu.nju.software.util.UserChecker;
import cn.edu.nju.software.vo.StoryNewVo;
import cn.edu.nju.software.vo.StoryWithIntroduction;

import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Api(value = "/story", description = "和故事有关的接口")
@Controller
@RequestMapping("/user")
public class UserStoryController extends BaseController {
	@Autowired
	private StoryService storyService;
	@Autowired
	private TagRelationService tagRelationService;
	@Autowired
	private UserRelationStoryService userRelationStoryService;
	@Autowired
	private StoryUserLogService storyUserLogService;
	@Autowired
	private StoryTagService storyTagService;
	@Autowired
	private StorySetService storySetService;
	@Autowired
	private TagUserLogService tagUserLogService;
	@Autowired
	private UserBadgeService userBadgeService;
	@Autowired
	private BadgeService badgeService;

	@ApiOperation(value = "获取ID获取故事", notes = "")
	@RequestMapping(value = "/getStoryById", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<StoryWithIntroduction> getStoryById(@ApiParam("故事ID") @RequestParam("id") Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<StoryWithIntroduction> responseData = new ResponseData();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			user = new User();
			user.setId(-1);
		}
		// 需求修改 这里需要返回故事简介
		// Story story = storyService.getStoryById(id);
		StoryWithIntroduction story = storyService.getStoryByIdWithIntroduction(id);
		responseData.jsonFill(1, null, story);
		/*
		 * if (story == null) { responseData.jsonFill(2, "该故事不存在", null); } else
		 * { responseData.jsonFill(1, null, story2Vo(story, user.getId())); }
		 */
		return responseData;
	}

	@ApiOperation(value = "故事访问记录", notes = "")
	@RequestMapping(value = "/saveStoryUserLog", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> saveStoryUserLog(@ApiParam("故事ID") @RequestParam("storyId") Integer storyId,
			@ApiParam("用户Id") @RequestParam("userId") Integer userId,
			@ApiParam("访问渠道") @RequestParam(value = "channel") String channel, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<Boolean> result = new ResponseData();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			result.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return result;
		}
		if (storyService.getStoryById(storyId) == null)
			throw new RuntimeException("无效的故事ID");
		StoryUserLog log = new StoryUserLog(userId, storyId, channel, new Date());
		storyUserLogService.saveLog(log);
		result.setBadgeList(checkoutViewBadge(user));
		result.jsonFill(1, null, true);
		return result;
	}

	// TODO 抽取service
	private List<Badge> checkoutViewBadge(User user) {
		List<Badge> badges = new ArrayList<>();
		if (userBadgeService.getUserBadge(Const.PABULUN_BADGE_ID, user.getId()) == null) {
			UserBadge userBadge = new UserBadge();
			userBadge.setUserId(user.getId());
			userBadge.setBadgeId(Const.PABULUN_BADGE_ID);
			userBadgeService.saveUserBadge(userBadge);
			Badge badge = badgeService.getBadgeById(Const.PABULUN_BADGE_ID);
			badges.add(badge);
		}
		return badges;
	}

	@ApiOperation(value = "分页得到故事列表", notes = "分页得到故事列表")
	@RequestMapping(value = "/getStoryListByPage", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryNewVo>> getAllStory(@ApiParam("OFFSET") @RequestParam int offset,
			@ApiParam("LIMIT") @RequestParam int limit,
			@ApiParam("排序:asc升序／desc降序(不输入时默认)") @RequestParam(required = false, defaultValue = "desc") String sortByCreateTime,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<StoryNewVo>> responseData = new ResponseData();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			user = new User();
			user.setId(-1);
		}
		List<Story> storyList = storyService.getSetStoryListByPage(offset, limit, sortByCreateTime);
		List<StoryNewVo> preList = storyList2VoList(storyList, user.getId());
		transformStorySetList(preList);
		responseData.jsonFill(1, null, preList);
		responseData.setCount(storyService.getSetStoryCount());
		return responseData;
	}

	@ApiOperation(value = "分页获得热门原创故事", notes = "")
	@RequestMapping(value = "/getPopularOriginalStoryListByPage", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryNewVo>> getPopularOriginalStoryListByPage(@ApiParam("PAGE") @RequestParam int page,
			@ApiParam("PAGESIZE") @RequestParam int pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<List<StoryNewVo>> responseData = new ResponseData();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			user = new User();
			user.setId(-1);
		}
		List<Story> storyList = storyService.getPopularOriginalStoryListByPage(page, pageSize);
		List<StoryNewVo> preList = storyList2VoList(storyList, user.getId());
		transformStorySetList(preList);
		responseData.jsonFill(1, null, preList);
		responseData.setCount(storyService.getStoryCountBySecondLevelTagId(100124));
		return responseData;
	}

	@ApiOperation(value = "根据一级标签获得故事列表", notes = "根据一级标签获得故事列表")
	@RequestMapping(value = "/getStoryIdListByFirstLevelTagId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryNewVo>> getStoryIdListByFirstLevelTagId(
			@ApiParam("一级标签ID") @RequestParam("tagId") int tagId, @ApiParam("OFFSET") @RequestParam int offset,
			@ApiParam("LIMIT") @RequestParam int limit, HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<StoryNewVo>> responseData = new ResponseData();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			user = new User();
			user.setId(-1);
		}
		List<Integer> idList = tagRelationService.getStoryIdListByFirstLevelTagId(tagId);
		List<Story> storyList = storyService.getStoryListByIdList(idList, offset, limit);
		responseData.jsonFill(1, null, storyList2VoList(storyList, user.getId()));
		responseData.setCount(storyService.getStoryCountByIdList(idList));
		return responseData;
	}

	@ApiOperation(value = "根据二级标签获得故事列表", notes = "根据二级标签获得故事列表")
	@RequestMapping(value = "/getStoryIdListBySecondLevelTagId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryNewVo>> getStoryIdListBySecondLevelTagId(
			@ApiParam("二级标签ID") @RequestParam("tagId") int tagId, @ApiParam("OFFSET") @RequestParam int offset,
			@ApiParam("LIMIT") @RequestParam int limit, HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<StoryNewVo>> responseData = new ResponseData<>();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			user = new User();
			user.setId(-1);
		}

		// TODO 除去原创故事的特殊处理
		List<Story> storyList;
		if (tagId == 100124) {
			storyList = storyService.getStoryListBySecondLevelTagId(tagId, offset, limit);
			responseData.setCount(storyService.getStoryCountBySecondLevelTagId(tagId));
		} else {
			List<Integer> idList = tagRelationService.getStoryIdListBySecondLevelTagId(tagId);
			if (idList.size() == 0) {
				responseData.jsonFill(1, null, null);
				return responseData;
			}
			storyList = storyService.getStoryListByIdList(idList, offset, limit);
			responseData.setCount(storyService.getStoryCountByIdList(idList));
		}
		List<StoryNewVo> preList = storyList2VoList(storyList, user.getId());
		transformStorySetList(preList);
		responseData.jsonFill(1, null, preList);

		return responseData;
	}

	@ApiOperation(value = "根据标题获得故事列表", notes = "")
	@RequestMapping(value = "/getStoryListByTitle", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryNewVo>> getStoryListByTitle(@ApiParam("查询字段") @RequestParam("query") String query,
			@ApiParam("OFFSET") @RequestParam int offset, @ApiParam("LIMIT") @RequestParam int limit,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<StoryNewVo>> responseData = new ResponseData<>();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			user = new User();
			user.setId(-1);
		}
		List<Story> storyList = storyService.getStoryListByTitle(query, offset, limit);
		responseData.jsonFill(1, null, storyList2VoList(storyList, user.getId()));
		responseData.setCount(storyService.getStoryCountByTitle(query));
		return responseData;
	}

	@ApiOperation(value = "获得推荐故事列表", notes = "")
	@RequestMapping(value = "/getRecommendedStoryListByPage", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryNewVo>> getRecommendedStoryVoByPage(
			@ApiParam("offset") @RequestParam("offset") int offset, @ApiParam("limit") @RequestParam("limit") int limit,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<StoryNewVo>> responseData = new ResponseData<>();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			user = new User();
			user.setId(-1);
		}
		List<Story> storyList = storyService.getSetRecommendedStoryListByPage(offset, limit);
		int count = storyService.getSetRecommendedStoryCount();
		List<StoryNewVo> preList = storyList2VoList(storyList, user.getId());
		transformStorySetList(preList);
		responseData.jsonFill(1, null, preList);
		responseData.setCount(count);
		return responseData;
	}

	@ApiOperation(value = "模糊查询获取故事", notes = "")
	@RequestMapping(value = "/storiesByFuzzyQuery", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<Story>> getStoryByFuzzyQuery(
			@ApiParam("query") @RequestParam(value = "query") String query,
			@ApiParam("OFFSET") @RequestParam int offset, @ApiParam("LIMIT") @RequestParam int limit,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<Story>> responseData = new ResponseData<>();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			user = new User();
			user.setId(-1);
		}
		List<Story> stories = storyService.getStoryByFuzzyQuery(query, offset, limit);
		if (stories == null) {
			responseData.jsonFill(2, "模糊查询失败", null);
			return responseData;
		} else {
			responseData.jsonFill(1, null, stories);
			responseData.setCount(storyService.getStoryCountByFuzzyQuery(query));
			return responseData;
		}
	}

	@ApiOperation(value = "设置喜爱的故事", notes = "")
	@RequestMapping(value = "/setLikeStory", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> likeStory(@ApiParam("storyId") @RequestParam("storyId") int storyId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Boolean> result = new ResponseData<>();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		boolean success = userRelationStoryService.addUserRelationStory(storyId, user.getId());
		if (success) {
			result.jsonFill(1, null, true);
		} else {
			result.jsonFill(2, "设置喜爱的故事失败", false);
		}
		return result;
	}

	@ApiOperation(value = "获取喜爱的故事", notes = "")
	@RequestMapping(value = "/likeStories", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<Story>> getLikeStories(@ApiParam("userId") @RequestParam int userId,
			@ApiParam("OFFSET") @RequestParam int offset, @ApiParam("LIMIT") @RequestParam int limit,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<Story>> result = new ResponseData<>();
		List<Story> storyList = userRelationStoryService.getLikeStories(userId, offset, limit);
		result.jsonFill(1, null, storyList);
		result.setCount(userRelationStoryService.getLikeStoriesCount(userId));
		return result;
	}

	@ApiOperation(value = "是否为用户喜爱的故事", notes = "")
	@RequestMapping(value = "/isLikedStory", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> getLikeStories(@ApiParam("userId") @RequestParam int userId,
			@ApiParam("storyId") @RequestParam int storyId, HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Boolean> result = new ResponseData<>();
		boolean isLiked = userRelationStoryService.isLikedByUser(userId, storyId);
		result.jsonFill(1, null, isLiked);
		return result;
	}

	@ApiOperation(value = "取消喜爱的故事", notes = "")
	@RequestMapping(value = "/setDislikeStory", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> dislikeStory(@ApiParam("storyId") @RequestParam("storyId") int storyId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Boolean> result = new ResponseData<>();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		boolean success = userRelationStoryService.deleteUserRelationStory(storyId, user.getId());
		if (success) {
			result.jsonFill(1, null, true);
		} else {
			result.jsonFill(2, "取消喜爱的故事失败", false);
		}
		return result;
	}

	@ApiOperation(value = "获取草稿列表")
	@RequestMapping(value = "/draftStories", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryNewVo>> getStoryByFuzzyQuery(@ApiParam("offset") @RequestParam("offset") int offset,
			@ApiParam("limit") @RequestParam("limit") int limit, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<List<StoryNewVo>> responseData = new ResponseData<>();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			user = new User();
			user.setId(-1);
		}
		List<Story> storyList = storyService.getDraftList(offset, limit);
		responseData.jsonFill(1, null, storyList2VoList(storyList, user.getId()));
		responseData.setCount(storyService.getDraftCount());
		return responseData;
	}

	@ApiOperation(value = "根据推荐故事列表", notes = "")
	@RequestMapping(value = "/getRecommendedStoryVoByBabyByPage", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryNewVo>> getRecommendedStoryVoByBabyByPage(
			@ApiParam("pageSize") @RequestParam("pageSize") int pageSize,
			@ApiParam("Baby的ID") @RequestParam("babyId") int babyId, @ApiParam("page") @RequestParam("page") int page,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<StoryNewVo>> responseData = new ResponseData<>();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			user = new User();
			user.setId(-1);
		}

		List<Integer> contentList = tagUserLogService.getTagUserLogTagIdListByBabyId(babyId, 0, 10);

		if (contentList.size() == 0) {
			List<Story> storyList = storyService.getSetRecommendedStoryListByPage(page * pageSize, pageSize);
			int count = storyService.getRecommendedStoryCount();
			responseData.jsonFill(1, null, storyList2VoList(storyList, user.getId()));
			responseData.setCount(count);
			return responseData;
		}
		List<Integer> storyIdList = tagRelationService.getStoryIdListByTagIdList(contentList);
		PageInfo<Story> pageInfo = storyService.getStoryListByIdListByPage(storyIdList, page, pageSize);

		responseData.setCount((int) pageInfo.getTotal());
		List<Story> list = pageInfo.getList();
		responseData.jsonFill(1, null, storyList2VoList(list, user.getId()));

		return responseData;
	}

	@ApiOperation(value = "分页获得一个数据集下所有故事", notes = "")
	@RequestMapping(value = "/getStoryListBySetId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryNewVo>> getStoryListBySetId(@ApiParam("故事集ID") @RequestParam int setId,
			@ApiParam("PAGE") @RequestParam int page, @ApiParam("SIZE") @RequestParam int pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<StoryNewVo>> responseData = new ResponseData<>();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			user = new User();
			user.setId(-1);
		}
		List<Story> storyList = storyService.getStoryListBySetId(setId, page, pageSize);
		responseData.jsonFill(1, null, storyList2VoList(storyList, user.getId()));
		responseData.setCount(storyService.getStoryCountBySetId(setId));
		return responseData;
	}

	@ApiOperation(value = "讲故事页面", notes = "需登录")
	@RequestMapping(value = "/getStorysByUser", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<Story>> getStorysByUser(@ApiParam("页") @RequestParam int page,
			@ApiParam("页大小") @RequestParam int pageSize, HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<Story>> responseData = new ResponseData<>();
		User user = UserChecker.checkUser(request);
		List<Integer> contentList = tagUserLogService.getTagUserLogTagIdListByUserId(user.getId(), 0, 10);

		if (contentList.size() == 0) {
			responseData.jsonFill(1, null, null);
			return responseData;
		}
		List<Integer> storyIdList = tagRelationService.getStoryIdListByTagIdList(contentList);
		if (storyIdList.size() == 0) {
			responseData.jsonFill(1, null, null);
			return responseData;
		}
		PageInfo<Story> pageInfo = storyService.getStoryListByIdListByPage(storyIdList, page, pageSize);

		responseData.setCount((int) pageInfo.getTotal());
		responseData.jsonFill(1, null, pageInfo.getList());
		return responseData;
	}

	@ApiOperation(value = "读过这个故事的人还读过", notes = "需要登录")
	@RequestMapping(value = "/getStoryListByReadLog", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<Story>> getStoryListByReadLog(@ApiParam("故事id") @RequestParam int storyId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<Story>> responseData = new ResponseData<>();
		List<Story> storyList = storyService.getStoryListByReadLog(storyId);
		if (storyList.size() >= 5) {
			List<Story> list = storyList.subList(0, 5);
			responseData.jsonFill(1, null, list);
			return responseData;
		}
		responseData.jsonFill(1, null, storyList);
		return responseData;
	}

	@ApiOperation(value = "讲故事页面(新)", notes = "需登录")
	@RequestMapping(value = "/getStoryListByBaby", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<Story>> getStoryListByBaby(@ApiParam("页") @RequestParam int page,
			@ApiParam("页大小") @RequestParam int pageSize, @ApiParam("宝宝ID") @RequestParam int babyId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<Story>> responseData = new ResponseData<>();
		User user = UserChecker.checkUser(request);
		List<Integer> contentList = tagUserLogService.getTagUserLogTagIdListByBabyId(babyId, 0, 10);

		if (contentList.size() == 0) {
			responseData.jsonFill(1, null, null);
			return responseData;
		}
		List<Integer> storyIdList = tagRelationService.getStoryIdListByTagIdList(contentList);
		if (storyIdList.size() == 0) {

			responseData.jsonFill(1, null, null);
			return responseData;
		}
		PageInfo<Story> pageInfo = storyService.getStoryListByIdListByPage(storyIdList, page, pageSize);

		responseData.setCount((int) pageInfo.getTotal());
		responseData.jsonFill(1, null, pageInfo.getList());
		return responseData;
	}

	@ApiOperation(value = "分页获取热门故事", notes = "需登录")
	@RequestMapping(value = "/getMostPopularStoryByPage", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<Story>> getMostPopularStoryByPage(@ApiParam("页") @RequestParam int page,
			@ApiParam("页大小") @RequestParam int pageSize, HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<Story>> responseData = new ResponseData<>();
		User user = UserChecker.checkUser(request);

		List<Story> list = storyService.getMostPopularStoryByPage(page, pageSize);
		responseData.jsonFill(1, null, list);
		responseData.setCount(storyService.getSetStoryCount());
		return responseData;
	}

	@ApiOperation(value = "分页获得集合列表", notes = "")
	@RequestMapping(value = "/getAllStorySetByPage", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StorySet>> getAllStorySetByPage(@ApiParam("PAGE") @RequestParam int page,
			@ApiParam("SIZE") @RequestParam int pageSize, HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<StorySet>> responseData = new ResponseData<>();
		List<StorySet> storySetList = storySetService.getAllStorySetByPage(page, pageSize);
		responseData.jsonFill(1, null, storySetList);
		responseData.setCount(storySetService.getAllStorySetCount());
		return responseData;
	}

	private List<StoryNewVo> storyList2VoList(List<Story> list, int userId) {
		List<StoryNewVo> storyVoList = new ArrayList<>();
		for (Story story : list) {
			storyVoList.add(story2Vo(story, userId));
		}
		return storyVoList;
	}

	// 根据故事和用户id获得一个故事vo类(故事的标签，用户是否喜爱这个故事)
	private StoryNewVo story2Vo(Story story, int userId) {
		if (story == null) {
			return null;
		}
		StoryNewVo storyVo = new StoryNewVo();
		List<Integer> idList = tagRelationService.getTagIdListByStoryId(story.getId());
		List<StoryTag> storyTagList = storyTagService.getStoryTagListByIdList(idList);
		storyVo.setTagList(storyTagList);
		BeanUtils.copyProperties(story, storyVo);
		if (userId > 0) {
			boolean isLiked = userRelationStoryService.isLikedByUser(userId, story.getId());
			storyVo.setLike(isLiked);
		}
		return storyVo;
	}

	/**
	 * 为了让故事集有数据可展示 TODO 同上面两个方法一样为丑陋的方法，之后重构抽取为service
	 * 
	 * @param list
	 */
	private void transformStorySetList(List<StoryNewVo> list) {
		for (StoryNewVo storyNewVo : list) {
			if (storyNewVo.getIsSet() == 1) {
				List<Story> storyList = storyService.getStoryListBySetId(storyNewVo.getId(), 0, 1);
				if (storyList.size() > 0) {
					StoryNewVo mockStory = story2Vo(storyList.get(0), -1);
					storyNewVo.setAuthor(mockStory.getAuthor());
					storyNewVo.setTagList(mockStory.getTagList());
					storyNewVo.setContent(mockStory.getContent());
				}
			}
		}
	}

}
