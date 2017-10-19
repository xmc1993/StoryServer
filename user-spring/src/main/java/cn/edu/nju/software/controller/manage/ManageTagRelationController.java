package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.StoryTag;
import cn.edu.nju.software.entity.TagRelation;
import cn.edu.nju.software.service.CheckValidService;
import cn.edu.nju.software.service.StoryService;
import cn.edu.nju.software.service.StoryTagService;
import cn.edu.nju.software.service.TagRelationService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/15.
 */
@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageTagRelationController {
	private static final Logger logger = LoggerFactory.getLogger(ManageTagRelationController.class);

	// 这是热搜显示tag的storyId
	private static final Integer HOTSEARCHTAGID = 2;

	@Autowired
	private TagRelationService tagRelationService;
	@Autowired
	private CheckValidService checkValidService;
	@Autowired
	private StoryTagService storyTagService;
	@Autowired
	private StoryService storyService;

	@ApiOperation(value = "添加标签", notes = "")
	@RequestMapping(value = "/stories/{storyId}/storyTags/{tagId}", method = { RequestMethod.POST })
	@ResponseBody
	public Boolean addTagToStory(@ApiParam("故事ID") @PathVariable Integer storyId,
			@ApiParam("标签ID") @PathVariable Integer tagId, HttpServletRequest request, HttpServletResponse response) {
		if (!checkValidService.isTagExist(tagId)) {
			logger.error("无效的tagId");
			throw new RuntimeException("无效的tagId");
		}
		Story story = storyService.getStoryById(storyId);
		if (story == null) {
			logger.error("无效的storyId");
			throw new RuntimeException("无效的storyId");
		}

		TagRelation tagRelation = new TagRelation();
		tagRelation.setTagId(tagId);
		tagRelation.setStoryId(storyId);
		tagRelation.setCreateTime(new Date());
		tagRelation.setUpdateTime(new Date());
		if (story.getSetId() != 0) {
			Story setStory = storyService.getStoryById(story.getSetId());
			if (setStory != null) {
				TagRelation tagRelation1 = new TagRelation();
				tagRelation1.setTagId(tagId);
				tagRelation1.setStoryId(setStory.getId());
				tagRelation1.setCreateTime(new Date());
				tagRelation1.setUpdateTime(new Date());
				tagRelationService.saveTagRelation(tagRelation1);
			}
		}
		boolean success = tagRelationService.saveTagRelation(tagRelation);
		return success;
	}

	@ApiOperation(value = "删除标签", notes = "")
	@RequestMapping(value = "/stories/{storyId}/storyTags/{tagId}", method = { RequestMethod.DELETE })
	@ResponseBody
	public ResponseData<Boolean> removeTagFromStory(@ApiParam("故事ID") @PathVariable Integer storyId,
			@ApiParam("标签ID") @PathVariable Integer tagId, HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData<>();
		if (!checkValidService.isTagExist(tagId)) {
			logger.error("无效的tagId");
			responseData.jsonFill(2, "无效的tagId", null);
			return responseData;
		}
		if (!checkValidService.isStoryExist(storyId)) {
			logger.error("无效的storyId");
			responseData.jsonFill(2, "无效的storyId", null);
			return responseData;
		}

		Story story = storyService.getStoryById(storyId);
		if (story.getSetId() != 0) {
			Story setStory = storyService.getStoryById(story.getSetId());
			if (setStory != null) {
				tagRelationService.deleteTagRelationByStoryIdAndTagId(setStory.getId(), tagId);
			}
		}
		boolean success = tagRelationService.deleteTagRelationByStoryIdAndTagId(storyId, tagId);
		responseData.jsonFill(success ? 1 : 2, null, success);
		return responseData;
	}

	@ApiOperation(value = "获得一个故事的所有标签", notes = "")
	@RequestMapping(value = "/stories/{id}/storyTags", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryTag>> getTagListOfStory(@ApiParam("故事ID") @PathVariable Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		if (!checkValidService.isStoryExist(id)) {
			logger.error("无效的故事Id");
			throw new RuntimeException("无效的故事ID");
		}
		List<Integer> idList = tagRelationService.getTagIdListByStoryId(id);
		List<StoryTag> storyTagList = storyTagService.getStoryTagListByIdList(idList);
		ResponseData<List<StoryTag>> result = new ResponseData<>();
		if (storyTagList == null) {
			result.jsonFill(2, "获得一个故事的所有标签失败", null);
			return result;
		} else {
			result.jsonFill(1, null, storyTagList);
			result.setCount(storyTagList.size());
			return result;
		}
	}

	// 在tagRelation这张表添加StoryID为2的代表热搜所需要显示的标签(不知道合适不，先加上) write by zs
	@ApiOperation(value = "添加热搜标签", notes = "")
	@RequestMapping(value = "/addTagToHotsearch", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> addTagToHotsearch(@ApiParam("标签ID的数租(按需要显示的顺序添加)") @RequestParam("tagIds") Integer[] tagIds,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Boolean> responseData=new ResponseData<>();
		boolean res=tagRelationService.deleteTagRelationsByStoryId(HOTSEARCHTAGID);
		if(res==false){
			responseData.jsonFill(2,"添加前删除失败", false);
			return responseData;
		}
		for (Integer tagId : tagIds) {
			TagRelation tagRelation = new TagRelation();
			tagRelation.setTagId(tagId);
			tagRelation.setStoryId(HOTSEARCHTAGID);
			tagRelation.setCreateTime(new Date());
			tagRelation.setUpdateTime(new Date());
			boolean success = tagRelationService.saveTagRelation(tagRelation);
			if (!success) {
				responseData.jsonFill(2, "添加失败", false);
				return responseData;
			}
		}
		responseData.jsonFill(1, null, true);
		return responseData;
	}

}
