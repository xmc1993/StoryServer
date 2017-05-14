package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryTag;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/15.
 */
@Api(value = "/story", description = "和故事有关的接口")
@Controller
public class TagRelationController {

    @ApiOperation(value = "添加标签", notes = "")
    @RequestMapping(value = "/manage/publishSoundEffect", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> addTagToStory(
            @ApiParam("故事ID") @RequestParam("storyId") MultipartFile storyId,
            @ApiParam("标签ID") @RequestParam("tagId") String tagId,
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @ApiOperation(value = "删除标签", notes = "")
    @RequestMapping(value = "/manage/removeTagFromStory", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> removeTagFromStory(
            @ApiParam("故事ID") @RequestParam("storyId") MultipartFile storyId,
            @ApiParam("标签ID") @RequestParam("tagId") String tagId,
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @ApiOperation(value = "获得一个故事的所有标签(parentId为0的为一级标签)", notes = "")
    @RequestMapping(value = "/manage/getTagListOfStory", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<StoryTag>> getTagListOfStory(
            @ApiParam("故事ID") @RequestParam("storyId") MultipartFile storyId,
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
