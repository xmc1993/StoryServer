package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.BackgroundMusic;
import cn.edu.nju.software.entity.BackgroundMusicTag;
import cn.edu.nju.software.service.CheckValidService;
import cn.edu.nju.software.service.BackgroundMusicService;
import cn.edu.nju.software.service.BackgroundMusicTagRelationService;
import cn.edu.nju.software.service.BackgroundMusicTagService;
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
public class UserBackgroundMusicEffectTagController extends BaseController {
    @Autowired
    private BackgroundMusicTagService backgroundMusicTagService;
    @Autowired
    private BackgroundMusicTagRelationService backgroundMusicTagRelationService;
    @Autowired
    private CheckValidService checkValidService;
    @Autowired
    private BackgroundMusicService backgroundMusicService;

    @ApiOperation(value = "获得所有背景音乐分类", notes = "")
    @RequestMapping(value = "/getAllBackgroundMusicTags", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<BackgroundMusicTag>> getAllBackgroundMusicTags(
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<BackgroundMusicTag>> responseData = new ResponseData<>();
        List<BackgroundMusicTag> backgroundMusicTagList = backgroundMusicTagService.getBackgroundMusicTagsByPage(offset, limit);
        responseData.jsonFill(1, null, backgroundMusicTagList);
        return responseData;
    }


    @ApiOperation(value = "获得一个背景音乐的所属分类", notes = "")
    @RequestMapping(value = "/getBackgroundMusicTagListByBackgroundMusicId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<BackgroundMusicTag>> getBackgroundMusicTagListByBackgroundMusicId(
            @ApiParam("背景音乐ID") @RequestParam("backgroundMusicId") int backgroundMusicId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<BackgroundMusicTag>> responseData = new ResponseData<>();
        if (!checkValidService.isBackgroundMusicExist(backgroundMusicId)) {
            responseData.jsonFill(2, "背景音乐不存在", null);
            return responseData;
        }
        List<Integer> idList = backgroundMusicTagRelationService.getTagIdListByBackgroundMusicId(backgroundMusicId);
        List<BackgroundMusicTag> backgroundMusicTagList = backgroundMusicTagService.getBackgroundMusicTagListByIdList(idList);
        responseData.jsonFill(1, null, backgroundMusicTagList);
        return responseData;
    }


    @ApiOperation(value = "获得一个分类下的所有背景音乐", notes = "")
    @RequestMapping(value = "/getBackgroundMusicListByTagId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<BackgroundMusic>> getBackgroundMusicListByTagId(
            @ApiParam("分类ID") @RequestParam("tagId") int tagId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<BackgroundMusic>> responseData = new ResponseData<>();
        if (!checkValidService.isBackgroundMusicTagExist(tagId)) {
            responseData.jsonFill(2, "背景音乐分类不存在", null);
            return responseData;
        }
        List<Integer> idList = backgroundMusicTagRelationService.getBackgroundMusicIdListByTagId(tagId);
        List<BackgroundMusic> result = backgroundMusicService.getBackgroundMusicListByIdList(idList);
        responseData.jsonFill(1, null, result);
        return responseData;
    }

}
