package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.dto.MsgVo;
import cn.edu.nju.software.entity.*;
import cn.edu.nju.software.entity.feed.MessageType;
import cn.edu.nju.software.feed.service.MessageFeedService;
import cn.edu.nju.software.service.*;
import cn.edu.nju.software.service.user.AppUserService;
import cn.edu.nju.software.service.wxpay.util.RandCharsUtils;
import cn.edu.nju.software.util.Const;
import cn.edu.nju.software.util.TokenConfig;
import cn.edu.nju.software.util.UploadFileUtil;
import cn.edu.nju.software.dto.WorksVo;
import cn.edu.nju.software.util.UserChecker;
import com.google.gson.Gson;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Api(value = "/work", description = "和作品有关的接口")
@Controller
@RequestMapping("/user")
public class UserWorksController extends BaseController {

	private static final String WORKS_ROOT = "/works/"; // 头像的基础路径
	@Autowired
	private WorksService worksService;
	@Autowired
	private AgreeService agreeService;
	@Autowired
	private CheckValidService checkValidService;
	@Autowired
	private StoryService storyService;
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
	MessageFeedService messageFeedService;
	@Autowired
	private RecordStatisticService recordStatisticService;

	@ApiOperation(value = "获得最新的作品列表", notes = "需要登录")
	@RequestMapping(value = "/getLatestWorksByPage", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<WorksVo>> getLatestWorksByPage(@ApiParam("页") @RequestParam int page,
			@ApiParam("页大小") @RequestParam int pageSize, HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<WorksVo>> responseData = new ResponseData<>();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return responseData;
		}
		List<Works> worksList = worksService.getLatestWorksByPage(page, pageSize);
		responseData.jsonFill(1, null, worksList2VoList(worksList, user.getId()));
		responseData.setCount(worksService.getWorksCount());
		return responseData;
	}

	@ApiOperation(value = "获得最受欢迎的作品列表", notes = "需要登录")
	@RequestMapping(value = "/getMostPopularByPage", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<WorksVo>> getMostPopularByPage(@ApiParam("页") @RequestParam int page,
			@ApiParam("页大小") @RequestParam int pageSize, HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<WorksVo>> responseData = new ResponseData<>();
		// User user = (User)
		// request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		// if (user == null) {
		// responseData.jsonFill(2, "请先登录", null);
		// response.setStatus(401);
		// return responseData;
		// }
		User user = UserChecker.checkUser(request);

		List<Works> worksList = worksService.getMostPopularByPage(page, pageSize);

		responseData.jsonFill(1, null, worksList2VoList(worksList, user.getId()));
		responseData.setCount(worksService.getWorksCount());
		return responseData;
	}

	@ApiOperation(value = "获取某个用户的作品列表", notes = "需要登录")
	@RequestMapping(value = "/getWorksByUserId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<WorksVo>> getWorksById(@ApiParam("用户ID") @RequestParam("userId") int userId,
			@ApiParam("OFFSET") @RequestParam int offset, @ApiParam("LIMIT") @RequestParam int limit,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<WorksVo>> responseData = new ResponseData();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return responseData;
		}
		List<Works> worksList = worksService.getWorksListByUserId(userId, offset, limit);
		responseData.jsonFill(1, null, worksList2VoList(worksList, user.getId()));
		responseData.setCount(worksService.getWorksCountByUserId(userId));
		return responseData;
	}

	// TODO 抽取service 赶进度先
	private List<WorksVo> worksList2VoList(List<Works> worksList, int userId) {
		List<WorksVo> worksVoList = new ArrayList<>();
		for (Works works : worksList) {
			worksVoList.add(works2Vo(works, userId));
		}
		return worksVoList;
	}

	private WorksVo works2Vo(Works works, int userId) {
		WorksVo worksVo = new WorksVo();
		BeanUtils.copyProperties(works, worksVo);
		if (agreeService.getAgree(userId, works.getId()) != null) {
			worksVo.setLike(true);
		}
		List<Integer> tagIdList = tagRelationService.getTagIdListByStoryId(works.getStoryId());
		List<StoryTag> tagList = storyTagService.getStoryTagListByIdList(tagIdList);
		worksVo.setTagList(tagList);
		return worksVo;
	}

	@ApiOperation(value = "保存Work访问记录", notes = "")
	@RequestMapping(value = "/saveWorkUserLog", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> saveWorkUserLog(@ApiParam("workId") @RequestParam("workId") int workId,
			@ApiParam("userID") @RequestParam("userId") int userId,
			@ApiParam("渠道") @RequestParam("channel") String channel, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<Boolean> result = new ResponseData<>();
		WorkUserLog workUserLog = new WorkUserLog(userId, workId, channel, new Date());
		boolean success = workUserLogService.saveWorkUserLog(workUserLog);
		if (success == false) {
			result.jsonFill(2, "保存访问记录失败", false);
			return result;
		} else {
			result.jsonFill(1, null, true);
			return result;
		}
	}

	@ApiOperation(value = "获取一个故事的所有作品列表(按照点赞数降序)", notes = "需登录")
	@RequestMapping(value = "/getWorksListByStoryId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<WorksVo>> getWorksListByStoryId(@ApiParam("故事ID") @RequestParam("storyId") int storyId,
			@ApiParam("OFFSET") @RequestParam int offset, @ApiParam("LIMIT") @RequestParam int limit,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<WorksVo>> responseData = new ResponseData();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return responseData;
		}
		List<Works> worksList = worksService.getWorksListByStoryId(storyId, offset, limit);
		responseData.jsonFill(1, null, worksList2VoList(worksList, user.getId()));
		responseData.setCount(worksService.getWorksCountByStoryId(storyId));
		return responseData;
	}

	@ApiOperation(value = "获得一个用户所有喜欢的作品列表", notes = "需登录")
	@RequestMapping(value = "/getAgreeWorksListByUserId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<WorksVo>> getWorksListByUserId(@ApiParam("用户ID") @RequestParam("userId") int userId,
			@ApiParam("OFFSET") @RequestParam int offset, @ApiParam("LIMIT") @RequestParam int limit,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<WorksVo>> responseData = new ResponseData();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return responseData;
		}
		List<Integer> idList = agreeService.getAgreeWorksIdListByUserId(userId, offset, limit);
		List<Works> worksList = worksService.getWorksListByIdList(idList);
		responseData.jsonFill(1, null, worksList2VoList(worksList, user.getId()));
		responseData.setCount(worksService.getWorksCountByIdList(idList));
		return responseData;
	}

	@ApiOperation(value = "根据ID获得一个作品", notes = "需要登录")
	@RequestMapping(value = "/getWorksById", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<WorksVo> getWorksById(@ApiParam("作品ID") @RequestParam("id") int id, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<WorksVo> responseData = new ResponseData();
		Works works = worksService.getWorksById(id);
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return responseData;
		}
		if (works == null) {
			responseData.jsonFill(2, "作品不存在", null);
			return responseData;
		}
		WorksVo worksVo = works2Vo(works, user.getId());
		responseData.jsonFill(1, null, worksVo);
		return responseData;
	}

	@ApiOperation(value = "根据ID获得一个分享作品（无需登录）", notes = "")
	@RequestMapping(value = "/getShareWorksById", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<Works> getShareWorksById(@ApiParam("作品ID") @RequestParam("id") int id,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Works> responseData = new ResponseData();
		Works works = worksService.getWorksById(id);
		if (works == null) {
			responseData.jsonFill(2, "作品不存在", null);
			return responseData;
		}
		responseData.jsonFill(1, null, works);
		return responseData;
	}

	@ApiOperation(value = "喜欢一个作品", notes = "需登录")
	@RequestMapping(value = "/likeWorks", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> likeWorks(@ApiParam("作品ID") @RequestParam("worksId") int worksId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return responseData;
		}
		if (!checkValidService.isWorksExist(worksId)) {
			responseData.jsonFill(2, "作品不存在", null);
			response.setStatus(404);
			return responseData;
		}
		Agree agree = new Agree();
		agree.setCreateTime(new Date());
		agree.setUpdateTime(new Date());
		agree.setWorksId(worksId);
		agree.setUserId(user.getId());
		boolean res = agreeService.addAgree(agree);
		responseData.jsonFill(res ? 1 : 2, null, res);
		return responseData;
	}

	@ApiOperation(value = "取消喜欢一个作品", notes = "需登录")
	@RequestMapping(value = "/unlikeWorks", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> unlikeWorks(@ApiParam("作品ID") @RequestParam("worksId") int worksId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return responseData;
		}
		if (!checkValidService.isWorksExist(worksId)) {
			responseData.jsonFill(2, "作品不存在", null);
			response.setStatus(404);
			return responseData;
		}
		boolean res = agreeService.deleteAgree(worksId, user.getId());
		responseData.jsonFill(res ? 1 : 2, null, res);
		return responseData;

	}

	@ApiOperation(value = "删除自己的某个作品", notes = "需登录")
	@RequestMapping(value = "/deleteWorks", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> deleteWorks(@ApiParam("作品ID") @RequestParam("worksId") int worksId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return responseData;
		}

		Works works = worksService.getWorksById(worksId);
		if (works == null) {
			responseData.jsonFill(2, "作品不存在", null);
			response.setStatus(404);
			return responseData;
		}
		if (!works.getUserId().equals(user.getId())) {
			responseData.jsonFill(2, "无效的请求。", null);
			response.setStatus(404);
			return responseData;
		}
		boolean res = worksService.deleteWorksById(worksId);
		if (res) {
			appUserService.updateTotalRecordTime(user.getId(), works.getDuration(), false);
			messageFeedService.unfeedFollowers(worksId, user.getId(), false);
		}
		responseData.jsonFill(res ? 1 : 2, null, res);
		return responseData;
	}

	@ApiOperation(value = "发布作品", notes = "需登录")
	@RequestMapping(value = "/publishWorks", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<WorksVo> publishWorks(@ApiParam("故事ID") @RequestParam("storyId") int storyId,
			@ApiParam("音频长度") @RequestParam("duration") String duration,
			@ApiParam("音频文件") @RequestParam(value = "uploadFile") MultipartFile uploadFile, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<WorksVo> responseData = new ResponseData();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return responseData;
		}
		if (uploadFile == null) {
			responseData.jsonFill(2, "请选择音频文件上传。", null);
			return responseData;
		}
		Story story = storyService.getStoryById(storyId);
		if (story == null) {
			responseData.jsonFill(2, "无效的故事ID", null);
			response.setStatus(404);
			return responseData;
		}

		String url = uploadFile(uploadFile, user.getId());
		if (url == null) {
			responseData.jsonFill(2, "文件上传失败", null);
			return responseData;
		}
		Works works = new Works();
		works.setDuration(duration);
		works.setCreateTime(new Date());
		works.setUpdateTime(new Date());
		works.setStoryId(storyId);
		works.setStoryTitle(story.getTitle());
		works.setCoverUrl(story.getCoverUrl());
		works.setUserId(user.getId());
		works.setUsername(user.getNickname());
		works.setUrl(url);
		works.setHeadImgUrl(user.getHeadImgUrl());
		Works res = worksService.saveWorks(works);
		if (res != null) {
			appUserService.updateTotalRecordTime(user.getId(), works.getDuration(), true);
			MsgVo msgVo = new MsgVo();
			msgVo.setUserId(user.getId());
			msgVo.setHeadImgUrl(user.getHeadImgUrl());
			msgVo.setUserName(user.getNickname());
			Feed feed = new Feed();
			feed.setCreateTime(new Date());
			feed.setUpdateTime(new Date());
			feed.setFid(user.getId());
			feed.setContent(new Gson().toJson(msgVo));
			feed.setMid(res.getId());
			feed.setType(MessageType.NEW_WORKS);
			messageFeedService.feedFollowers(feed, user.getId(), true);
		}

		WorksVo worksVo = new WorksVo();
		if (res != null) {
			List<Badge> badges = judgeUserAddBadgeByPublish(user, works);
			BeanUtils.copyProperties(works, worksVo);
			responseData.jsonFill(1, null, worksVo);
			responseData.setBadgeList(badges);
		} else {
			responseData.jsonFill(2, "发布失败", null);
		}
		return responseData;

	}

	/**
	 * 判断是否需要给用户增加新徽章
	 * 
	 * @param user
	 * @return
	 */
	private List<Badge> judgeUserAddBadgeByPublish(User user, Works works) {
		List<Badge> badges = new ArrayList<>();
		int[] workCountBadgeArr = { 5000, 2000, 1000, 500, 300, 200, 150, 100, 30, 10, 3 };

		List<Integer> tagIdList = tagRelationService.getTagIdListByStoryId(works.getStoryId());

		int[] workRecordDayCountArr = { 1, 3, 7, 15, 21, 30, 50, 100, 200, 365, 500, 1000 };

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
		// 最高连续阅读天数
		int maxDays = recordStatisticService.getHistoryMaxCount(user.getId());

		recordStatisticService.saveRecord(user.getId());

		// 判断连续阅读故事的天数
		int days = recordStatisticService.getCurCount(user.getId());
		if (maxDays < days) {
			// 49的id是连续阅读一天的ID,后面依次类推
			if (days < 3 && days >= 0) {
				UserBadge userBadge = new UserBadge();
				userBadge.setUserId(user.getId());
				userBadge.setBadgeId(49);
				userBadgeService.saveUserBadge(userBadge);
				Badge badge = badgeService.getBadgeById(49);
				badges.add(badge);
			} else if (days < 7 && days >= 3) {
				UserBadge userBadge = new UserBadge();
				userBadge.setUserId(user.getId());
				userBadge.setBadgeId(50);
				userBadgeService.saveUserBadge(userBadge);
				Badge badge = badgeService.getBadgeById(50);
				badges.add(badge);
			} else if (days < 15 && days >= 7) {
				UserBadge userBadge = new UserBadge();
				userBadge.setUserId(user.getId());
				userBadge.setBadgeId(51);
				userBadgeService.saveUserBadge(userBadge);
				Badge badge = badgeService.getBadgeById(51);
				badges.add(badge);
			} else if (days < 21 && days >= 15) {
				UserBadge userBadge = new UserBadge();
				userBadge.setUserId(user.getId());
				userBadge.setBadgeId(52);
				userBadgeService.saveUserBadge(userBadge);
				Badge badge = badgeService.getBadgeById(52);
				badges.add(badge);
			} else if (days < 30 && days >= 21) {
				UserBadge userBadge = new UserBadge();
				userBadge.setUserId(user.getId());
				userBadge.setBadgeId(53);
				userBadgeService.saveUserBadge(userBadge);
				Badge badge = badgeService.getBadgeById(53);
				badges.add(badge);
			} else if (days < 50 && days >= 30) {
				UserBadge userBadge = new UserBadge();
				userBadge.setUserId(user.getId());
				userBadge.setBadgeId(54);
				userBadgeService.saveUserBadge(userBadge);
				Badge badge = badgeService.getBadgeById(54);
				badges.add(badge);
			} else if (days < 100 && days >= 50) {
				UserBadge userBadge = new UserBadge();
				userBadge.setUserId(user.getId());
				userBadge.setBadgeId(55);
				userBadgeService.saveUserBadge(userBadge);
				Badge badge = badgeService.getBadgeById(55);
				badges.add(badge);
			}
		}

		// 作品数量徽章
		for (int i = 0; i < workCountBadgeArr.length; i++) {
			if (user.getWorkCount() == workCountBadgeArr[i]) {
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

	@ApiOperation(value = "重新发布作品", notes = "需登录")
	@RequestMapping(value = "/rePublishWorks", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Works> rePublishWorks(@ApiParam("作品ID") @RequestParam("worksId") int worksId,
			@ApiParam("音频长度") @RequestParam("duration") String duration,
			@ApiParam("音频文件") @RequestParam("uploadFile") MultipartFile uploadFile, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<Works> responseData = new ResponseData();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return responseData;
		}
		if (uploadFile == null) {
			responseData.jsonFill(2, "请选择音频文件上传。", null);
			return responseData;
		}

		Works works = worksService.getWorksById(worksId);
		if (works == null) {
			responseData.jsonFill(2, "无效的作品ID", null);
			response.setStatus(404);
			return responseData;
		}
		if (works.getUserId() != user.getId()) {
			responseData.jsonFill(2, "非法请求。", null);
			response.setStatus(401);
			return responseData;
		}

		String url = uploadFile(uploadFile, user.getId());
		if (url == null) {
			responseData.jsonFill(2, "文件上传失败", null);
			return responseData;
		}
		works.setDuration(duration);
		works.setUpdateTime(new Date());
		// 删除原有的作品文件
		UploadFileUtil.deleteFileByUrl(works.getUrl());
		works.setUrl(url);
		boolean res = worksService.updateWorks(works);
		if (res) {
			responseData.jsonFill(1, null, works);
		} else {
			responseData.jsonFill(2, "发布失败", null);
		}
		return responseData;
	}

	@ApiOperation(value = "收听作品", notes = "")
	@RequestMapping(value = "/listenWorks", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> listenWorks(@ApiParam("作品ID") @RequestParam("worksId") int worksId,
			HttpServletRequest request, HttpServletResponse response) throws ParseException {
		ResponseData<Boolean> responseData = new ResponseData<>();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return responseData;
		}
		// TODO 抽取成service
		boolean res = worksService.listenWorks(worksId);
		Integer authorId = worksService.getUserIdByWorkId(worksId);

		judgeUserAddBadgeByListen(authorId);

		Works works = worksService.getWorksById(worksId);
		if (works != null) {
			WorkUserLog workUserLog = new WorkUserLog();
			workUserLog.setAccessTime(new Date());
			workUserLog.setStoryId(works.getStoryId());
			workUserLog.setWorkId(worksId);
			workUserLog.setUserId(user.getId());
			workUserLogService.saveWorkUserLog(workUserLog);
		}
		responseData.setBadgeList(checkoutListenBadge(user, works));
		responseData.jsonFill(1, null, res);
		return responseData;
	}

	@ApiOperation(value = "获得总的录制时长", notes = "需登录")
	@RequestMapping(value = "/getTotalRecordTime", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<String> getTotalRecordTime(HttpServletRequest request, HttpServletResponse response) {
		ResponseData<String> responseData = new ResponseData();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return responseData;
		}

		String totalRecordTime = appUserService.getTotalRecordTime(user.getId());
		responseData.jsonFill(1, null, totalRecordTime);
		return responseData;
	}

	@ApiOperation(value = "获得收听数", notes = "需登录")
	@RequestMapping(value = "/getListenCount", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<Integer> getListenCount(HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Integer> responseData = new ResponseData();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return responseData;
		}

		Integer listenCount = appUserService.getListenCount(user.getId());
		responseData.jsonFill(1, null, listenCount);
		return responseData;
	}

	@ApiOperation(value = "根据workId获取作品的Url(分享时调用)", notes = "需登录")
	@RequestMapping(value = "/getWorkUrlByWorkId", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<String> getWorkUrlByWorkId(@ApiParam("作品ID") @RequestParam("worksId") int worksId,
			HttpServletRequest request, HttpServletResponse response) { 
		ResponseData<String> responseData=new ResponseData<>();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return responseData;
		}
		Works works=worksService.getWorksById(worksId);
		if(works!=null){
			responseData.jsonFill(1, null, works.getUrl());
			return responseData;
		}
			responseData.jsonFill(2, "该作品不存在", null);
			return responseData;
	}

	// TODO 抽取service
	private List<Badge> checkoutListenBadge(User user, Works works) {
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

	private void judgeUserAddBadgeByListen(Integer authorId) {
		UserBase userBase = appUserService.getUserBaseById(authorId);
		int[] listenCounts = { 1000000, 500000, 100000, 50000, 10000, 5000, 2000, 1000, 100, 10 };
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

	/**
	 * 上传作品的音频文件
	 *
	 * @param file
	 * @return
	 */
	private String uploadFile(MultipartFile file, int userId) {
		String realPath = UploadFileUtil.getBaseUrl() + WORKS_ROOT + userId + "/";
		String fileName = RandCharsUtils.getRandomString(16) + "."
				+ UploadFileUtil.getSuffix(file.getOriginalFilename());
		boolean success = UploadFileUtil.mvFile(file, realPath, fileName);
		if (!success) {
			throw new RuntimeException("文件上传失败");
		}
		String url = UploadFileUtil.SOURCE_BASE_URL + WORKS_ROOT + userId + "/" + fileName;
		return url;
	}
}
