package cn.edu.nju.software.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.software.vo.StoryWithIntroduction;
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
import cn.edu.nju.software.entity.StoryTopic;
import cn.edu.nju.software.entity.StoryTopicRelation;
import cn.edu.nju.software.service.StoryService;
import cn.edu.nju.software.service.StoryTopicServcie;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月24日 
*/

@Api("StoryTopic controller")
@Controller
@RequestMapping("/user")
public class UserStoryTopicController extends BaseController{
	@Autowired
	StoryTopicServcie storyTopicServcie;
	@Autowired
	StoryService storyService;
	
	@ApiOperation(value = "根据故事专题获得故事列表", notes = "")
	@RequestMapping(value = "/getStorysByStoryTopic", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryWithIntroduction>> getStorysByStoryTopic(@ApiParam("专题id") @RequestParam Integer id,
																		   HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<StoryWithIntroduction>> responseData = new ResponseData<>();
		List<StoryWithIntroduction> storyList = new ArrayList<>();
		List<StoryTopicRelation> list = storyTopicServcie.getStoryListByTopicId(id);
		for (StoryTopicRelation storyTopicRelation : list) {
			storyList.add(storyService.getStoryByIdWithIntroduction(storyTopicRelation.getstoryId()));
		}
		responseData.jsonFill(1, null, storyList);
		return responseData;
	}
	
	@ApiOperation(value = "获取所有有效的故事专题", notes = "")
	@RequestMapping(value = "/selectAllShowStoryTopic", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryTopic>> selectAllShowStoryTopic(HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<List<StoryTopic>> responseData = new ResponseData<>();
		List<StoryTopic> list = storyTopicServcie.selectAllShowStoryTopic();
		responseData.jsonFill(1, null, list);
		return responseData;
	}
	
}
