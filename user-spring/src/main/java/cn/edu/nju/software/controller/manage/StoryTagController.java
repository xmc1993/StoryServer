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
import org.springframework.http.HttpStatus;
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
@Controller()
@RequestMapping("/manage")
public class StoryTagController {
    private static final Logger logger = LoggerFactory.getLogger(StoryTagController.class);
    @Autowired
    private CheckValidService checkValidService;
    @Autowired
    private StoryTagService storyTagService;

    @ApiOperation(value = "新增标签", notes = "")
    @RequestMapping(value = "/storyTag",method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public StoryTag publishStoryTag(
            @ApiParam("标签文字") @RequestBody StoryTag storyTag,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        if (storyTag.getParentId() !=0 && !checkValidService.isTagExist(storyTag.getParentId())) {
            logger.error("无效的parentId");
            responseData.jsonFill(2, "无效的parentId", null);
            throw new RuntimeException("无效的parentId");
        }
        storyTag.setCreateTime(new Date());
        storyTag.setUpdateTime(new Date());
        storyTagService.saveStoryTag(storyTag);
        return storyTag;

    }

    @ApiOperation(value = "更新标签文字", notes = "")
    @RequestMapping(value = "/updateStoryTag", method = {RequestMethod.GET})
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
    @RequestMapping(value = "/storyTag/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStoryTag(
            @ApiParam("标签ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        if (!checkValidService.isTagExist(id)) {
            logger.error("无效的tagId");
            throw new RuntimeException("无效的parentId");
        }
        boolean success = storyTagService.deleteStoryTag(id);
        if (!success){
            response.setStatus(404);
            throw new RuntimeException("删除失败");
        }
    }

    @ApiOperation(value = "根据parentId得到标签列表", notes = "")
    @RequestMapping(value = "/getStoryTagListByParentId", method = {RequestMethod.GET})
    @ResponseBody
    public List<StoryTag> getStoryTagListByParentId(
            @ApiParam("父标签ID") @RequestParam("parentId") int parentId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<StoryTag>> responseData = new ResponseData<>();
        if (!checkValidService.isTagExist(parentId)) {
            logger.error("无效的parentId");
            throw new RuntimeException("无效的parentId");
        }

        List<StoryTag> tagList = storyTagService.getStoryTagListByParentId(parentId);
        return tagList;
    }
}
