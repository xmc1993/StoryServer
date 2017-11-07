package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.StorySet;
import cn.edu.nju.software.service.StoryService;
import cn.edu.nju.software.service.StorySetService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/15.
 */
@Api(value = "Admin", description = "管理接口")
@Controller()
@RequestMapping("/manage")
public class ManageStorySetController {
    private static final Logger logger = LoggerFactory.getLogger(ManageStorySetController.class);
    @Autowired
    private StorySetService storySetService;
    @Autowired
    private StoryService storyService;

    @ApiOperation(value = "新增故事集", notes = "")
    @RequestMapping(value = "/storySets", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public StorySet publishStorySet(@ApiParam("故事集") @RequestBody StorySet storySet, HttpServletRequest request,
                                    HttpServletResponse response) {
        storySet.setIsSet(1);
        storySet.setValid(1);
        storySet.setCreateTime(new Date());
        storySet.setUpdateTime(new Date());
        storySetService.saveStorySet(storySet);
        return storySet;
    }

    @ApiOperation(value = "更新故事集", notes = "")
    @RequestMapping(value = "/storySets/{id}", method = {RequestMethod.PUT})
    @ResponseBody
    public StorySet updateStorySet(@ApiParam("ID") @PathVariable int id,
                                   @ApiParam("故事集") @RequestBody StorySet storySet, HttpServletRequest request, HttpServletResponse response) {
        storySet.setId(id);
        storySet.setUpdateTime(new Date());
        return storySetService.updateStorySet(storySet) ? storySet : null;

    }

    @ApiOperation(value = "删除故事集", notes = "")
    @RequestMapping(value = "/storySets/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStorySet(@ApiParam("ID") @PathVariable int id, HttpServletRequest request,
                               HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean success = storySetService.deleteStorySet(id);
        if (!success) {
            throw new RuntimeException("删除失败");
        }
    }

    @ApiOperation(value = "根据ID获得故事集", notes = "")
    @RequestMapping(value = "/storySets/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public StorySet getStorySetById(@ApiParam("ID") @PathVariable int id, HttpServletRequest request,
                                    HttpServletResponse response) {
        StorySet storySet = storySetService.getStorySetById(id);
        if (storySet == null) {
            throw new RuntimeException("无效的ID");
        } else {
            return storySet;
        }
    }

    @ApiOperation(value = "根据故事集名字搜索故事集")
    @RequestMapping(value = "/getStorySetByFuzzyQuery", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<List<StorySet>> getStorySetByFuzzyQuery(@ApiParam("PAGE") @RequestParam int page,
                                                                @ApiParam("SIZE") @RequestParam int pageSize, @ApiParam("query") @RequestParam String query,
                                                                HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<StorySet>> responseData = new ResponseData<>();
        List<StorySet> setList = new ArrayList<>();
        // 查询条件为空，返回全部故事集
        if (null == query) {
            setList = storySetService.getAllStorySetByPage(page, pageSize);
            responseData.jsonFill(1, null, setList);
            responseData.setCount(storySetService.getAllStorySetCount());
            return responseData;
        }
        setList = storySetService.getStorySetByFuzzyQuery(query);
        responseData.jsonFill(1, null, setList);
        return responseData;
    }

    @ApiOperation(value = "分页获得集合列表", notes = "")
    @RequestMapping(value = "/getAllStorySetByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<StorySet>> getAllStorySetByPage(@ApiParam("PAGE") @RequestParam int page,
                                                             @ApiParam("SIZE") @RequestParam int pageSize, HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<StorySet>> responseData = new ResponseData<>();
        List<StorySet> storySetList = storySetService.getAllStorySetByPage(page, pageSize);
        responseData.jsonFill(1, null, storySetList);
        responseData.setCount(storySetService.getAllStorySetCount());
        return responseData;
    }

    @ApiOperation(value = "获得一个故事所属的故事集", notes = "")
    @RequestMapping(value = "/getStorySetByStoryId", method = {RequestMethod.GET})
    @ResponseBody
    public StorySet getStorySetByStoryId(@ApiParam("故事ID") @RequestParam int storyId, HttpServletRequest request,
                                         HttpServletResponse response) {
        StorySet storySet = storySetService.getStorySetByStoryId(storyId);
        if (storySet == null) {
            throw new RuntimeException("该故事不存在所属故事集");
        } else {
            return storySet;
        }
    }

    @ApiOperation(value = "推荐故事集", notes = "")
    @RequestMapping(value = "/storySets/{id}/recommendations", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void recommendStory(@ApiParam("故事集ID") @PathVariable int id) {
        boolean res = storyService.recommendStory(id);
        if (!res) {
            throw new RuntimeException("推荐失败");
        }
    }

    @ApiOperation(value = "取消推荐故事集", notes = "")
    @RequestMapping(value = "/storySets/{id}/recommendations", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelRecommendStory(@ApiParam("故事集ID") @PathVariable int id) {
        boolean res = storyService.cancelRecommendStory(id);
        if (!res) {
            throw new RuntimeException("取消推荐失败");
        }
    }

    @ApiOperation(value = "分页获得一个数据集下所有故事", notes = "")
    @RequestMapping(value = "/getStoryListBySetId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Story>> getStoryListBySetId(@ApiParam("故事集ID") @RequestParam int setId,
                                                         @ApiParam("PAGE") @RequestParam int page, @ApiParam("SIZE") @RequestParam int pageSize,
                                                         HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Story>> responseData = new ResponseData<>();
        List<Story> storyList = storyService.getStoryListBySetId(setId, page, pageSize);
        responseData.jsonFill(1, null, storyList);
        return responseData;
    }

}
