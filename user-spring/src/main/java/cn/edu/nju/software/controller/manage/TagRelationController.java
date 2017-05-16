package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryTag;
import cn.edu.nju.software.entity.TagRelation;
import cn.edu.nju.software.service.CheckValidService;
import cn.edu.nju.software.service.StoryTagService;
import cn.edu.nju.software.service.TagRelationService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
public class TagRelationController {
    private static final Logger logger = LoggerFactory.getLogger(TagRelationController.class);
    @Autowired
    private TagRelationService tagRelationService;
    @Autowired
    private CheckValidService checkValidService;
    @Autowired
    private StoryTagService storyTagService;


    @ApiOperation(value = "添加标签", notes = "")
    @RequestMapping(value = "/manage/addTagToStory", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> addTagToStory(
            @ApiParam("故事ID") @RequestParam("storyId") Integer storyId,
            @ApiParam("标签ID") @RequestParam("tagId") Integer tagId,
            HttpServletRequest request, HttpServletResponse response) {
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

        TagRelation tagRelation = new TagRelation();
        tagRelation.setTagId(tagId);
        tagRelation.setStoryId(storyId);
        tagRelation.setCreateTime(new Date());
        tagRelation.setUpdateTime(new Date());
        boolean success = tagRelationService.saveTagRelation(tagRelation);
        responseData.jsonFill(success ? 1 : 2, null, success);
        return responseData;
    }

    @ApiOperation(value = "删除标签", notes = "")
    @RequestMapping(value = "/manage/removeTagFromStory", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> removeTagFromStory(
            @ApiParam("故事ID") @RequestParam("storyId") Integer storyId,
            @ApiParam("标签ID") @RequestParam("tagId") Integer tagId,
            HttpServletRequest request, HttpServletResponse response) {
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
        boolean success = tagRelationService.deleteTagRelationByStoryIdAndTagId(storyId, tagId);
        responseData.jsonFill(success ? 1 : 2, null, success);
        return responseData;
    }

    @ApiOperation(value = "获得一个故事的所有标签(parentId为0的为一级标签)", notes = "")
    @RequestMapping(value = "/manage/getTagListOfStory", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<StoryTag>> getTagListOfStory(
            @ApiParam("故事ID") @RequestParam("storyId") Integer storyId,
            HttpServletRequest request, HttpServletResponse response) {

        ResponseData<List<StoryTag>> responseData = new ResponseData<>();
        if (!checkValidService.isStoryExist(storyId)) {
            logger.error("无效的storyId");
            responseData.jsonFill(2, "无效的storyId", null);
            return responseData;
        }
        List<Integer> idList = tagRelationService.getTagIdListByStoryId(storyId);


        return responseData;
    }

}
