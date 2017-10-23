package cn.edu.nju.software.controller.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.StoryTopicRelation;
import cn.edu.nju.software.entity.StoryUserLog;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.StoryUserLogService;
import cn.edu.nju.software.util.TokenConfig;

/**
 * @author zs1996 E-mail:806949096@qq.com
 * @version 创建时间：2017年10月19日
 */

@Api("StoryUserLog controller")
@Controller
@RequestMapping("/user")
public class UserStoryUserLog extends BaseController {
	@Autowired
	StoryUserLogService storyUserLogService;

	@ApiOperation(value = "添加阅读记录", notes = "需登录")
	@RequestMapping(value = "/addReadStoryLog", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> addReadStoryLog(@ApiParam("故事id") @RequestParam Integer storyId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData<>();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", false);
			response.setStatus(401);
			return responseData;
		}

		StoryUserLog storyUserLog = new StoryUserLog();
		storyUserLog.setAccessTime(new Date());
		storyUserLog.setUserId(user.getId());
		storyUserLog.setStoryId(storyId);
		storyUserLog.setChannel("app");
		boolean res = storyUserLogService.saveLog(storyUserLog);
		if (res == true) {
			responseData.jsonFill(1, null, true);
			return responseData;
		}
		responseData.jsonFill(2, "日志收集失败", false);
		return responseData;
	}

	@ApiOperation(value = "某个故事的阅读次数")
	@RequestMapping(value = "/getViewCountByStoryId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<Integer> getViewCountByStoryId(@ApiParam("故事id") @RequestParam Integer storyId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Integer> responseData = new ResponseData<>();
		Integer viewCount = storyUserLogService.getUserCountByStoryID(storyId);
		if (viewCount != null) {
			responseData.jsonFill(1, null, viewCount);
			return responseData;
		}
		responseData.jsonFill(1,null, 0);
		return responseData;
	}
}
