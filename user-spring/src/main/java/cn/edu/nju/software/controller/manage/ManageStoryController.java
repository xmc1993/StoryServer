package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.StoryTag;
import cn.edu.nju.software.service.StoryService;
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
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/15.
 */
@Api(value = "Admin", description = "管理接口")
@Controller
public class ManageStoryController {
    private static final Logger logger = LoggerFactory.getLogger(ManageStoryController.class);
    @Autowired
    private StoryService storyService;



    @ApiOperation(value = "新增故事", notes = "")
    @RequestMapping(value = "/stories", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Story publicStory() {

        //新建故事
        return null;
    }


    @ApiOperation(value = "更新故事", notes = "")
    @RequestMapping(value = "/stories/{id}", method = {RequestMethod.PUT})
    @ResponseBody
    public Story updateStoryTag(
            @ApiParam("故事ID") @PathVariable int id,
            @ApiParam("标签") @RequestBody StoryTag storyTag,
            HttpServletRequest request, HttpServletResponse response) {
        //TODO 更新
        return null;

    }


    @ApiOperation(value = "删除故事", notes = "")
    @RequestMapping(value = "/stories/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStory(
            @ApiParam("故事ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean success = storyService.deleteStoryById(id);
        if (!success) {
            throw new RuntimeException("删除失败");
        }
    }

    @ApiOperation(value = "根据ID获得故事", notes = "")
    @RequestMapping(value = "/stories/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public Story getStoryId(
            @ApiParam("标签ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        Story story = storyService.getStoryById(id);
        if (story == null) {
            throw new RuntimeException("无效的ID");
        } else {
            return story;
        }
    }

    @ApiOperation(value = "标签列表", notes = "")
    @RequestMapping(value = "/stories", method = {RequestMethod.GET})
    @ResponseBody
    public List<Story> getAllStories(
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {

        //TODO 分页存取
//        List<StoryTag> tagList = storyTagService.getStoryTagsByPage(offset, limit);
        return null;
    }

}
