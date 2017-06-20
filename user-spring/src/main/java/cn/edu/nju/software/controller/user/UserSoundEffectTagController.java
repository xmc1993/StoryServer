package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.SoundEffect;
import cn.edu.nju.software.entity.SoundEffectTag;
import cn.edu.nju.software.service.CheckValidService;
import cn.edu.nju.software.service.SoundEffectService;
import cn.edu.nju.software.service.SoundEffectTagRelationService;
import cn.edu.nju.software.service.SoundEffectTagService;
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
public class UserSoundEffectTagController extends BaseController {
    @Autowired
    private SoundEffectTagService soundEffectTagService;
    @Autowired
    private SoundEffectTagRelationService soundEffectTagRelationService;
    @Autowired
    private CheckValidService checkValidService;
    @Autowired
    private SoundEffectService soundEffectService;

    @ApiOperation(value = "获得所有音效分类", notes = "")
    @RequestMapping(value = "/getAllSoundEffectTags", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<SoundEffectTag>> getAllSoundEffectTags(
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<SoundEffectTag>> responseData = new ResponseData<>();
        List<SoundEffectTag> soundEffectTagList = soundEffectTagService.getSoundEffectTagsByPage(offset, limit);
        responseData.jsonFill(1, null, soundEffectTagList);
        responseData.setCount(soundEffectTagService.getSoundEffectTagCount());
        return responseData;
    }


    @ApiOperation(value = "获得一个音效的所属分类", notes = "")
    @RequestMapping(value = "/getSoundEffectTagListBySoundEffectId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<SoundEffectTag>> getSoundEffectTagListBySoundEffectId(
            @ApiParam("音效ID") @RequestParam("soundEffectId") int soundEffectId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<SoundEffectTag>> responseData = new ResponseData<>();
        if (!checkValidService.isSoundEffectExist(soundEffectId)) {
            responseData.jsonFill(2, "音效不存在", null);
            return responseData;
        }
        List<Integer> idList = soundEffectTagRelationService.getTagIdListBySoundEffectId(soundEffectId);
        List<SoundEffectTag> soundEffectTagList = soundEffectTagService.getSoundEffectTagListByIdList(idList);
        responseData.jsonFill(1, null, soundEffectTagList);
        responseData.setCount(soundEffectTagList.size());
        return responseData;
    }


    @ApiOperation(value = "获得一个分类下的所有音效", notes = "")
    @RequestMapping(value = "/getSoundEffectListByTagId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<SoundEffect>> getSoundEffectListByTagId(
            @ApiParam("分类ID") @RequestParam("tagId") int tagId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<SoundEffect>> responseData = new ResponseData<>();
        if (!checkValidService.isSoundEffectTagExist(tagId)) {
            responseData.jsonFill(2, "音效分类不存在", null);
            return responseData;
        }
        List<Integer> idList = soundEffectTagRelationService.getSoundEffectIdListByTagId(tagId);
        List<SoundEffect> result = soundEffectService.getSoundEffectListByIdList(idList);
        responseData.jsonFill(1, null, result);
        responseData.setCount(result.size());
        return responseData;
    }

}
