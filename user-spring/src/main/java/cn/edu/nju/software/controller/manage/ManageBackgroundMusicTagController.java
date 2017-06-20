package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.BackgroundMusicTag;
import cn.edu.nju.software.service.CheckValidService;
import cn.edu.nju.software.service.BackgroundMusicTagService;
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
public class ManageBackgroundMusicTagController {
    private static final Logger logger = LoggerFactory.getLogger(ManageBackgroundMusicTagController.class);
    @Autowired
    private CheckValidService checkValidService;
    @Autowired
    private BackgroundMusicTagService backgroundMusicTagService;

    @ApiOperation(value = "新增背景音乐分类", notes = "")
    @RequestMapping(value = "/backgroundMusicTags", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public BackgroundMusicTag publishBackgroundMusicTag(
            @ApiParam("分类") @RequestBody BackgroundMusicTag backgroundMusicTag,
            HttpServletRequest request, HttpServletResponse response) {
        backgroundMusicTag.setCreateTime(new Date());
        backgroundMusicTag.setUpdateTime(new Date());
        backgroundMusicTag.setValid(1);
        backgroundMusicTagService.saveBackgroundMusicTag(backgroundMusicTag);
        return backgroundMusicTag;
    }

    @ApiOperation(value = "更新背景音乐分类", notes = "")
    @RequestMapping(value = "/backgroundMusicTags/{id}", method = {RequestMethod.PUT})
    @ResponseBody
    public BackgroundMusicTag updateBackgroundMusicTag(
            @ApiParam("分类ID") @PathVariable int id,
            @ApiParam("分类") @RequestBody BackgroundMusicTag backgroundMusicTag,
            HttpServletRequest request, HttpServletResponse response) {
        if (!checkValidService.isBackgroundMusicTagExist(id)) {
            logger.error("无效的id");
            throw new RuntimeException("无效的id");
        }
        backgroundMusicTag.setId(id);
        backgroundMusicTag.setUpdateTime(new Date());
        return backgroundMusicTagService.updateBackgroundMusicTag(backgroundMusicTag);

    }

    @ApiOperation(value = "删除背景音乐分类", notes = "")
    @RequestMapping(value = "/backgroundMusicTags/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBackgroundMusicTag(
            @ApiParam("分类ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean success = backgroundMusicTagService.deleteBackgroundMusicTag(id);
        if (!success) {
            throw new RuntimeException("删除失败");
        }
    }


    @ApiOperation(value = "根据ID获得背景音乐分类", notes = "")
    @RequestMapping(value = "/backgroundMusicTags/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public BackgroundMusicTag getStoryById(
            @ApiParam("分类ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        BackgroundMusicTag backgroundMusicTag = backgroundMusicTagService.getBackgroundMusicTagById(id);
        if (backgroundMusicTag == null) {
            throw new RuntimeException("无效的ID");
        } else {
            return backgroundMusicTag;
        }
    }

    @ApiOperation(value = "背景音乐分类列表", notes = "")
    @RequestMapping(value = "/backgroundMusicTags", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<BackgroundMusicTag>> getAllBackgroundMusicTags(
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        List<BackgroundMusicTag> tagList = backgroundMusicTagService.getBackgroundMusicTagsByPage(offset, limit);
        ResponseData<List<BackgroundMusicTag>> result=new ResponseData<>();
        if(tagList==null){
            result.jsonFill(2,"获取背景音乐分类列表失败",null);
            return result;
        }
        else{
            result.jsonFill(1,null,tagList);
            result.setCount(backgroundMusicTagService.getBackgroundMusicTagCount());
            return result;
        }
    }
    @ApiOperation(value = "获取背景音乐分类数量", notes = "")
    @RequestMapping(value = "/backgroundMusicTagCount", method = {RequestMethod.GET})
    @ResponseBody
    public Integer getBackgroundMusicTagCount(){
        return backgroundMusicTagService.getBackgroundMusicTagCount();
    }
}
