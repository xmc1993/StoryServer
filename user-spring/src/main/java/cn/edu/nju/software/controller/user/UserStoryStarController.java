package cn.edu.nju.software.controller.user;

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
import cn.edu.nju.software.entity.StoryStar;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.StoryStarService;
import cn.edu.nju.software.util.TokenConfig;

/**
 * @author zs1996 E-mail:806949096@qq.com
 * @version 创建时间：2017年10月24日
 */

@Api(value = "/story", description = "和故事有关的接口")
@Controller
@RequestMapping("/user")
public class UserStoryStarController extends BaseController {
	@Autowired
	StoryStarService storyStarService;

	@ApiOperation(value = "为故事打星", notes = "需登录")
	@RequestMapping(value = "/setStarForStory", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> setStarForStory(@ApiParam("故事id") @RequestParam Integer storyId,
			@ApiParam("星数") @RequestParam Integer starCount, HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			user = new User();
			user.setId(-1);
		}
		ResponseData<Boolean> responseData = new ResponseData<>();
		StoryStar storyStar = new StoryStar();
		storyStar.setCreatetime(new Date());
		storyStar.setUpdatetime(new Date());
		storyStar.setstoryId(storyId);
		storyStar.setValid(1);
		storyStar.setuserId(user.getId());
		storyStar.setStarNumber(starCount);
		Integer res = storyStarService.insertStoryStar(storyStar);
		if (res == 1) {
			responseData.jsonFill(1, null, true);
			return responseData;
		}
		responseData.jsonFill(2, "评价失败", false);
		return responseData;
	}

	@ApiOperation(value = "根据故事id和用户id查询故事打星情况", notes = "需登录")
	@RequestMapping(value = "/getStoryStarByStoryIdUserId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryStar>> getStoryStarByStoryIdUserId(@ApiParam("故事id") @RequestParam Integer storyId,
			HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			user = new User();
			user.setId(-1);
		}
		
		ResponseData<List<StoryStar>> responseData = new ResponseData<>();
		List<StoryStar> storyStars = storyStarService.getStoryStarByUserAndStory(storyId, user.getId());

		responseData.jsonFill(1, null, storyStars);
		return responseData;
	}
}
