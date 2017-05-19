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
import org.springframework.web.bind.annotation.*;

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
public class TagRelationController {
    private static final Logger logger = LoggerFactory.getLogger(TagRelationController.class);
    @Autowired
    private TagRelationService tagRelationService;
    @Autowired
    private CheckValidService checkValidService;
    @Autowired
    private StoryTagService storyTagService;


    @ApiOperation(value = "添加标签", notes = "")
    @RequestMapping(value = "/stories/{storyId}/storyTags/{tagId}", method = {RequestMethod.POST})
    @ResponseBody
    public Boolean addTagToStory(
            @ApiParam("故事ID") @PathVariable Integer storyId,
            @ApiParam("标签ID") @PathVariable Integer tagId,
            HttpServletRequest request, HttpServletResponse response) {
        if (!checkValidService.isTagExist(tagId)) {
            logger.error("无效的tagId");
            throw new RuntimeException("无效的tagId");
        }
        if (!checkValidService.isStoryExist(storyId)) {
            logger.error("无效的storyId");
            throw new RuntimeException("无效的storyId");
        }

        TagRelation tagRelation = new TagRelation();
        tagRelation.setTagId(tagId);
        tagRelation.setStoryId(storyId);
        tagRelation.setCreateTime(new Date());
        tagRelation.setUpdateTime(new Date());
        boolean success = tagRelationService.saveTagRelation(tagRelation);
        return success;
    }

    @ApiOperation(value = "删除标签", notes = "")
    @RequestMapping(value = "/stories/{storyId}/storyTags/{tagId}", method = {RequestMethod.DELETE})
    @ResponseBody
    public ResponseData<Boolean> removeTagFromStory(
            @ApiParam("故事ID") @PathVariable Integer storyId,
            @ApiParam("标签ID") @PathVariable Integer tagId,
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

    @ApiOperation(value = "获得一个故事的所有标签", notes = "")
    @RequestMapping(value = "/stories/{id}/storyTags", method = {RequestMethod.GET})
    @ResponseBody
    public List<StoryTag> getTagListOfStory(
            @ApiParam("故事ID") @PathVariable Integer id,
            HttpServletRequest request, HttpServletResponse response) {
        if (!checkValidService.isStoryExist(id)) {
            logger.error("无效的故事Id");
            throw new RuntimeException("无效的故事ID");
        }
        List<Integer> idList = tagRelationService.getTagIdListByStoryId(id);

        //TODO 返回列表
        return null;
    }

}
