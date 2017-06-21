package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.service.StoryService;
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

@Api(value = "/story", description = "和故事有关的接口")
@Controller
@RequestMapping("/user")
public class UserStoryController extends BaseController {
    @Autowired
    private StoryService storyService;
    @Autowired
    private TagRelationService tagRelationService;

    @ApiOperation(value = "获取ID获取故事", notes = "")
    @RequestMapping(value = "/getStoryById", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Story> getStoryById(
            @ApiParam("故事ID") @RequestParam("id") Integer id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Story> responseData = new ResponseData();
        Story story = storyService.getStoryById(id);
        if (story == null) {
            responseData.jsonFill(2, "该故事不存在", null);
        } else {
            responseData.jsonFill(1, null, story);
        }
        return responseData;
    }

    @ApiOperation(value = "分页得到故事列表", notes = "分页得到故事列表")
    @RequestMapping(value = "/getStoryListByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Story>> getAllStory(
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Story>> responseData = new ResponseData();
        List<Story> storyList = storyService.getStoryListByPage(offset, limit);
        responseData.jsonFill(1, null, storyList);
        responseData.setCount(storyService.getStoryCount());
        return responseData;
    }

    @ApiOperation(value = "根据一级标签获得故事列表", notes = "根据一级标签获得故事列表")
    @RequestMapping(value = "/getStoryIdListByFirstLevelTagId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Story>> getStoryIdListByFirstLevelTagId(
            @ApiParam("一级标签ID") @RequestParam("tagId") int tagId,
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Story>> responseData = new ResponseData();
        List<Integer> idList = tagRelationService.getStoryIdListByFirstLevelTagId(tagId);
        List<Story> storyList = storyService.getStoryListByIdList(idList,offset,limit);
        responseData.jsonFill(1, null, storyList);
        responseData.setCount(storyService.getStoryCountByIdList(idList, offset, limit));
        return responseData;
    }

    @ApiOperation(value = "根据二级标签获得故事列表", notes = "根据二级标签获得故事列表")
    @RequestMapping(value = "/getStoryIdListBySecondLevelTagId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Story>> getStoryIdListBySecondLevelTagId(
            @ApiParam("二级标签ID") @RequestParam("tagId") int tagId,
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Story>> responseData = new ResponseData();
        List<Integer> idList = tagRelationService.getStoryIdListBySecondLevelTagId(tagId);
        List<Story> storyList = storyService.getStoryListByIdList(idList, offset, limit);
        responseData.jsonFill(1, null, storyList);
        responseData.setCount(storyService.getStoryCountByIdList(idList, offset, limit));
        return responseData;
    }

    @ApiOperation(value = "根据标题获得故事列表", notes = "")
    @RequestMapping(value = "/getStoryListByTitle", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Story>> getStoryListByTitle(
            @ApiParam("查询字段") @RequestParam("query") String query,
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Story>> responseData = new ResponseData<>();
        List<Story> storyList = storyService.getStoryListByTitle(query, offset, limit);
        responseData.jsonFill(1, null, storyList);
        responseData.setCount(storyService.getStoryCountByTitle(query));
        return responseData;
    }

    @ApiOperation(value = "获得推荐故事列表", notes = "")
    @RequestMapping(value = "/getRecommendedStoryListByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Story>> getRecommendedStoryListByPage(
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Story>> responseData = new ResponseData<>();
        List<Story> storyList = storyService.getRecommendedStoryListByPage(offset, limit);
        responseData.jsonFill(1, null, storyList);
        responseData.setCount(storyService.getRecommendedStoryCount());
        return responseData;
    }

}
