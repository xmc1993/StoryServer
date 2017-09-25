package cn.edu.nju.software.controller.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

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
@Api(value = "Admin", description = "管理接口")
@Controller()
@RequestMapping("/manage")
public class ManageStoryTopicController {

	@Autowired
	private StoryTopicServcie storyTopicServcie;
	@Autowired
	private StoryService storyService;

	@ApiOperation(value = "分页获取所有故事专题", notes = "")
	@RequestMapping(value = "/selectAllStoryTopic", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryTopic>> selectAllStoryTopic(@ApiParam("page") @RequestParam Integer page,
			@ApiParam("pageSize") @RequestParam Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<List<StoryTopic>> responseData = new ResponseData<>();
		responseData = storyTopicServcie.getAllStoryTopic(page, pageSize);
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

	@ApiOperation(value = "保存故事专题", notes = "")
	@RequestMapping(value = "/saveStoryTopic", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<StoryTopic> saveStoryTopic(@ApiParam("专题名字") @RequestParam String title,
			@ApiParam("专题内容") @RequestParam String content, @ApiParam("封面Url") @RequestParam String coverUrl,
			@ApiParam("是否要显示(1表示显示，0表示不显示)") @RequestParam Integer isShow, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<StoryTopic> responseData = new ResponseData<>();
		StoryTopic storyTopic = new StoryTopic();
		storyTopic.setTitle(title);
		storyTopic.setCoverurl(coverUrl);
		storyTopic.setContent(content);
		storyTopic.setIsshow(isShow);
		storyTopic.setValid(1);
		storyTopic.setCreatetime(new Date());
		storyTopic.setUpdatetime(new Date());

		int res = storyTopicServcie.saveStoryTopic(storyTopic);
		if (res == 1) {
			responseData.jsonFill(1, null, storyTopic);
			return responseData;
		}
		responseData.jsonFill(2, "添加失败", null);
		return responseData;
	}
	

	@ApiOperation(value = "修改故事专题", notes = "")
	@RequestMapping(value = "/updateStoryTopicById", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<StoryTopic> updateStoryTopicById(@ApiParam("专题id") @RequestParam Integer id,
			@ApiParam("专题名字") @RequestParam(value = "title", required = false) String title,
			@ApiParam("专题内容") @RequestParam(value = "content", required = false) String content,
			@ApiParam("封面Url") @RequestParam(value = "coverUrl", required = false) String coverUrl,
			@ApiParam("是否要显示(1表示显示，0表示不显示)") @RequestParam(value = "isShow", required = false) Integer isShow,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<StoryTopic> responseData = new ResponseData<>();
		StoryTopic storyTopic = storyTopicServcie.getStoryTopicById(id);
		if (title != null)
			storyTopic.setTitle(title);
		if (coverUrl != null)
			storyTopic.setCoverurl(coverUrl);
		if (content != null)
			storyTopic.setContent(content);
		if (isShow != null)
			storyTopic.setIsshow(isShow);
		storyTopic.setUpdatetime(new Date());

		int res = storyTopicServcie.updateStoryTopic(storyTopic);
		if (res == 1) {
			responseData.jsonFill(1, null, storyTopic);
			return responseData;
		}
		responseData.jsonFill(2, "修改失败", null);
		return responseData;
	}

	@ApiOperation(value = "删除故事专题", notes = "")
	@RequestMapping(value = "/deleteStoryTopic/{id}", method = { RequestMethod.DELETE })
	@ResponseBody
	public ResponseData<Boolean> deleteStoryTopic(@ApiParam("专题id") @PathVariable Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData<>();
		storyTopicServcie.deleteStoryTopicStorys(id);
		int res = storyTopicServcie.deleteStoryTopicById(id);
		if (res == 1) {
			responseData.jsonFill(1, null, true);
			return responseData;
		}
		responseData.jsonFill(2, "删除失败", null);
		return responseData;
	}
	
	@ApiOperation(value = "根据id故事专题详情", notes = "")
	@RequestMapping(value = "/getStoryTopicById", method = { RequestMethod.GET})
	@ResponseBody
	public ResponseData<StoryTopic> getStoryTopicById(@ApiParam("专题id") @RequestParam Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<StoryTopic> responseData = new ResponseData<>();
		StoryTopic storyTopic=storyTopicServcie.getStoryTopicById(id);
		responseData.jsonFill(2, "删除失败", storyTopic);
		return responseData;
	}


	@ApiOperation(value = "根据故事专题获得故事列表", notes = "")
	@RequestMapping(value = "/getStorysByStoryTopic", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<Story>> getStorysByStoryTopic(@ApiParam("专题id") @RequestParam Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<Story>> responseData = new ResponseData<>();
		List<Story> storyList = new ArrayList<Story>();
		List<StoryTopicRelation> list = storyTopicServcie.getStoryListByTopicId(id);
		for (StoryTopicRelation storyTopicRelation : list) {
			storyList.add(storyService.getStoryById(storyTopicRelation.getstoryId()));
		}
		responseData.jsonFill(1, null, storyList);
		return responseData;
	}

	@ApiOperation(value = "为某个专题添加故事")
	@RequestMapping(value = "/addStoryForStoryTopic", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> addStoryForStoryTopic(@ApiParam("专题id") @RequestParam Integer storyTopicId,
			@ApiParam("添加故事id列表") @RequestParam Integer[] storyIds) {
		ResponseData<Boolean> responseData = new ResponseData<>();
		storyTopicServcie.deleteStoryTopicStorys(storyTopicId);
		for (Integer storyId : storyIds) {
			StoryTopicRelation storyTopicRelation = new StoryTopicRelation();
			storyTopicRelation.setCreatetime(new Date());
			storyTopicRelation.setUpdatetime(new Date());
			storyTopicRelation.setstoryId(storyId);
			storyTopicRelation.setValid(1);
			storyTopicRelation.setstorytopicId(storyTopicId);
			int res =storyTopicServcie.saveStoryTopicRelation(storyTopicRelation);
			if(res!=1){
				responseData.jsonFill(2, null, false);
				return responseData;
			}
		}
		responseData.jsonFill(1, null, true);
		return responseData;
	}

	@ApiOperation(value = "删除某个专题的某个故事")
	@RequestMapping(value = "/deleteStoryForStoryTopic", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> deleteStoryForStoryTopic(@ApiParam("专题id") @RequestParam Integer storyTopicId,
			@ApiParam("故事Id") @RequestParam Integer storyId) {
		ResponseData<Boolean> responseData = new ResponseData<>();
		int res = storyTopicServcie.deleteStoryForStoryTopic(storyTopicId, storyId);
		if (res == 1) {
			responseData.jsonFill(1, null, true);
			return responseData;
		}
		responseData.jsonFill(2, null, false);
		return responseData;
	}
}
