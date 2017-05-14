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
public class StoryTagController {

    @ApiOperation(value = "新增标签", notes = "")
    @RequestMapping(value = "/manage/publishStoryTag", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> publishStoryTag(
            @ApiParam("标签文字") @RequestParam("content") MultipartFile content,
            @ApiParam("副标签ID（一级标签的parentId为0）") @RequestParam("parentId") String parentId,
            HttpServletRequest request, HttpServletResponse response) {

        return null;
    }

    @ApiOperation(value = "更新标签文字", notes = "")
    @RequestMapping(value = "/manage/updateStoryTag", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> updateStoryTag(
            @ApiParam("标签文字") @RequestParam("content") MultipartFile content,
            @ApiParam("标签ID") @RequestParam("tagId") String tagId,
            HttpServletRequest request, HttpServletResponse response) {
        //TODO 删除旧的音效文件
        return null;
    }

    @ApiOperation(value = "删除标签", notes = "")
    @RequestMapping(value = "/manage/deleteStoryTag", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> deleteStoryTag(
            @ApiParam("标签ID") @RequestParam("id") String id,
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @ApiOperation(value = "根据parentId得到标签列表", notes = "")
    @RequestMapping(value = "/manage/getStoryTagListByParentId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<StoryTag>> getStoryTagListByParentId(
            @ApiParam("父标签ID") @RequestParam("parentId") String parentId,
            HttpServletRequest request, HttpServletResponse response) {
        //todo 是否要分页
        return null;
    }
}
