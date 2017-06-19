package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.SoundEffectTag;
import cn.edu.nju.software.service.CheckValidService;
import cn.edu.nju.software.service.SoundEffectTagService;
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
public class ManageSoundEffectTagController {
    private static final Logger logger = LoggerFactory.getLogger(ManageSoundEffectTagController.class);
    @Autowired
    private CheckValidService checkValidService;
    @Autowired
    private SoundEffectTagService soundEffectTagService;

    @ApiOperation(value = "新增音效分类", notes = "")
    @RequestMapping(value = "/soundEffectTags", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public SoundEffectTag publishSoundEffectTag(
            @ApiParam("分类") @RequestBody SoundEffectTag soundEffectTag,
            HttpServletRequest request, HttpServletResponse response) {
        soundEffectTag.setCreateTime(new Date());
        soundEffectTag.setUpdateTime(new Date());
        soundEffectTag.setValid(1);
        soundEffectTagService.saveSoundEffectTag(soundEffectTag);
        return soundEffectTag;
    }

    @ApiOperation(value = "更新音效分类", notes = "")
    @RequestMapping(value = "/soundEffectTags/{id}", method = {RequestMethod.PUT})
    @ResponseBody
    public SoundEffectTag updateSoundEffectTag(
            @ApiParam("分类ID") @PathVariable int id,
            @ApiParam("分类") @RequestBody SoundEffectTag soundEffectTag,
            HttpServletRequest request, HttpServletResponse response) {
        if (!checkValidService.isSoundEffectTagExist(id)) {
            logger.error("无效的id");
            throw new RuntimeException("无效的id");
        }
        soundEffectTag.setId(id);
        soundEffectTag.setUpdateTime(new Date());
        return soundEffectTagService.updateSoundEffectTag(soundEffectTag);

    }

    @ApiOperation(value = "删除音效分类", notes = "")
    @RequestMapping(value = "/soundEffectTags/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSoundEffectTag(
            @ApiParam("分类ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean success = soundEffectTagService.deleteSoundEffectTag(id);
        if (!success) {
            throw new RuntimeException("删除失败");
        }
    }


    @ApiOperation(value = "根据ID获得音效分类", notes = "")
    @RequestMapping(value = "/soundEffectTags/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public SoundEffectTag getStoryById(
            @ApiParam("分类ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        SoundEffectTag soundEffectTag = soundEffectTagService.getSoundEffectTagById(id);
        if (soundEffectTag == null) {
            throw new RuntimeException("无效的ID");
        } else {
            return soundEffectTag;
        }
    }

    @ApiOperation(value = "音效分类列表", notes = "")
    @RequestMapping(value = "/soundEffectTags", method = {RequestMethod.GET})
    @ResponseBody
    public List<SoundEffectTag> getAllSoundEffectTags(
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        List<SoundEffectTag> tagList = soundEffectTagService.getSoundEffectTagsByPage(offset, limit);
        return tagList;
    }

    @ApiOperation(value = "获取背景音乐分类数量", notes = "")
    @RequestMapping(value = "/soundEffectTagCount", method = {RequestMethod.GET})
    @ResponseBody
    public Integer getSoundEffectTagCount(){
        return soundEffectTagService.getSoundEffectTagCount();
    }
}
