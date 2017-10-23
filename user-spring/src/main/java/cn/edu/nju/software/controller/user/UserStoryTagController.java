package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryTag;
import cn.edu.nju.software.service.CheckValidService;
import cn.edu.nju.software.service.StoryTagService;
import cn.edu.nju.software.service.TagRelationService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Api("tag controller")
@Controller
@RequestMapping("/user")
public class UserStoryTagController extends BaseController {
    @Autowired
    private StoryTagService storyTagService;
    @Autowired
    private TagRelationService tagRelationService;
    @Autowired
    private CheckValidService checkValidService;

    @ApiOperation(value = "获得所有一级标签", notes = "获得所有一级标签")
    @RequestMapping(value = "/getAllFirstLevelTags", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<StoryTag>> getAllFirstLevelTags(
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<StoryTag>> responseData = new ResponseData<>();
        List<StoryTag> storyTagList = storyTagService.getStoryTagListByParentId(0);
        responseData.jsonFill(1, null, storyTagList);
        responseData.setCount(storyTagList.size());
        return responseData;
    }

    @ApiOperation(value = "获得所有二级标签", notes = "")
    @RequestMapping(value = "/getAllSecondLevelTags", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<StoryTag>> getAllSecondLevelTags(
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<StoryTag>> responseData = new ResponseData<>();
        List<StoryTag> storyTagList = storyTagService.getAllSecondLevelTags();
        responseData.jsonFill(1, null, storyTagList);
        responseData.setCount(storyTagList.size());
        return responseData;
    }

    @ApiOperation(value = "根据父标签ID（目前是根据一级标签id或者二级标签）获得下一级标签列表", notes = "")
    @RequestMapping(value = "/getTagListByParentId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<StoryTag>> getTagListByParentId(
            @ApiParam("父标签ID") @RequestParam("parentId") int parentId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<StoryTag>> responseData = new ResponseData<>();
        if (parentId != 0 && !checkValidService.isTagExist(parentId)) {
            responseData.jsonFill(2, "父标签不存在。", null);
            return responseData;
        }
        List<StoryTag> storyTagList = storyTagService.getStoryTagListByParentId(parentId);
        responseData.jsonFill(1, null, storyTagList);
        responseData.setCount(storyTagList.size());
        return responseData;
    }

    @ApiOperation(value = "获得故事的所有标签", notes = "")
    @RequestMapping(value = "/getStoryTagListByStoryId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<StoryTag>> getStoryTagListByStoryId(
            @ApiParam("故事ID") @RequestParam("storyId") int storyId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<StoryTag>> responseData = new ResponseData<>();
        if (!checkValidService.isStoryExist(storyId)) {
            responseData.jsonFill(2, "故事不存在", null);
            return responseData;
        }
        List<Integer> idList = tagRelationService.getTagIdListByStoryId(storyId);
        List<StoryTag> storyTagList = storyTagService.getStoryTagListByIdList(idList);
        responseData.jsonFill(1, null, storyTagList);
        responseData.setCount(storyTagList.size());
        return responseData;
    }

    @ApiOperation(value = "获得热门搜索的标签", notes = "")
    @RequestMapping(value = "/getHotTagList", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<StoryTag>> getHotTagList(
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<StoryTag>> responseData = new ResponseData<>();

        //storyId为2表示后台需要显示的热搜标签
        List<Integer> idList = tagRelationService.getTagIdListByStoryId(2);
        List<StoryTag> storyTagList = storyTagService.getStoryTagListByIdList(idList);
        responseData.jsonFill(1, null, storyTagList);
        responseData.setCount(storyTagList.size());
        return responseData;
    }
}
