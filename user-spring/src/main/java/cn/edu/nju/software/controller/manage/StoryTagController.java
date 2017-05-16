package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryTag;
import cn.edu.nju.software.service.CheckValidService;
import cn.edu.nju.software.service.StoryTagService;
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
public class StoryTagController {
    private static final Logger logger = LoggerFactory.getLogger(StoryTagController.class);
    @Autowired
    private CheckValidService checkValidService;
    @Autowired
    private StoryTagService storyTagService;

    @ApiOperation(value = "新增标签", notes = "")
    @RequestMapping(value = "/manage/publishStoryTag", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> publishStoryTag(
            @ApiParam("标签文字") @RequestParam("content") String content,
            @ApiParam("副标签ID（一级标签的parentId为0）") @RequestParam("parentId") int parentId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        if (!checkValidService.isTagExist(parentId)) {
            logger.error("无效的parentId");
            responseData.jsonFill(2, "无效的parentId", null);
            return responseData;
        }
        StoryTag storyTag = new StoryTag();
        storyTag.setContent(content);
        storyTag.setParentId(parentId);
        storyTag.setCreateTime(new Date());
        storyTag.setUpdateTime(new Date());
        boolean success = storyTagService.saveStoryTag(storyTag);
        responseData.jsonFill(success ? 1 : 2, null, success);
        return responseData;
    }

    @ApiOperation(value = "更新标签文字", notes = "")
    @RequestMapping(value = "/manage/updateStoryTag", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> updateStoryTag(
            @ApiParam("标签文字") @RequestParam("content") String content,
            @ApiParam("标签ID") @RequestParam("tagId") int tagId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        if (!checkValidService.isTagExist(tagId)) {
            logger.error("无效的tagId");
            responseData.jsonFill(2, "无效的tagId", null);
            return responseData;
        }
        StoryTag storyTag = new StoryTag();
        storyTag.setId(tagId);
        storyTag.setContent(content);
        storyTag.setUpdateTime(new Date());
        boolean success = storyTagService.updateStoryTag(storyTag);
        responseData.jsonFill(success ? 1 : 2, null, success);
        return responseData;
    }

    @ApiOperation(value = "删除标签", notes = "")
    @RequestMapping(value = "/manage/deleteStoryTag", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> deleteStoryTag(
            @ApiParam("标签ID") @RequestParam("tagId") int tagId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        if (!checkValidService.isTagExist(tagId)) {
            logger.error("无效的tagId");
            responseData.jsonFill(2, "无效的tagId", null);
            return responseData;
        }
        boolean success = storyTagService.deleteStoryTag(tagId);
        responseData.jsonFill(success ? 1 : 2, null, success);
        return responseData;
    }

    @ApiOperation(value = "根据parentId得到标签列表", notes = "")
    @RequestMapping(value = "/manage/getStoryTagListByParentId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<StoryTag>> getStoryTagListByParentId(
            @ApiParam("父标签ID") @RequestParam("parentId") int parentId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<StoryTag>> responseData = new ResponseData<>();
        if (!checkValidService.isTagExist(parentId)) {
            logger.error("无效的parentId");
            responseData.jsonFill(2, "无效的parentId", null);
            return responseData;
        }

        List<StoryTag> tagList = storyTagService.getStoryTagListByParentId(parentId);
        responseData.jsonFill(1, null, tagList);
        return responseData;
    }
}
