package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.SoundEffectTag;
import cn.edu.nju.software.entity.SoundEffectTagRelation;
import cn.edu.nju.software.service.CheckValidService;
import cn.edu.nju.software.service.SoundEffectTagService;
import cn.edu.nju.software.service.SoundEffectTagRelationService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/15.
 */
@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageSoundEffectTagRelationController {
    private static final Logger logger = LoggerFactory.getLogger(ManageSoundEffectTagRelationController.class);
    @Autowired
    private SoundEffectTagRelationService soundEffectTagRelationService;
    @Autowired
    private CheckValidService checkValidService;
    @Autowired
    private SoundEffectTagService soundEffectTagService;


    @ApiOperation(value = "给音效添加分类", notes = "")
    @RequestMapping(value = "/soundEffects/{soundEffectId}/soundEffectTags/{tagId}", method = {RequestMethod.POST})
    @ResponseBody
    public Boolean addTagToSoundEffect(
            @ApiParam("音效ID") @PathVariable Integer soundEffectId,
            @ApiParam("分类ID") @PathVariable Integer tagId,
            HttpServletRequest request, HttpServletResponse response) {
        if (!checkValidService.isSoundEffectTagExist(tagId)) {
            logger.error("无效的tagId");
            throw new RuntimeException("无效的tagId");
        }
        if (!checkValidService.isSoundEffectExist(soundEffectId)) {
            logger.error("无效的soundEffectId");
            throw new RuntimeException("无效的soundEffectId");
        }

        SoundEffectTagRelation soundEffectTagRelation = new SoundEffectTagRelation();
        soundEffectTagRelation.setTagId(tagId);
        soundEffectTagRelation.setSoundEffectId(soundEffectId);
        soundEffectTagRelation.setCreateTime(new Date());
        soundEffectTagRelation.setUpdateTime(new Date());
        boolean success = soundEffectTagRelationService.saveTagRelation(soundEffectTagRelation);
        return success;
    }

    @ApiOperation(value = "删除音效的一个分类", notes = "")
    @RequestMapping(value = "/soundEffects/{soundEffectId}/soundEffectTags/{tagId}", method = {RequestMethod.DELETE})
    @ResponseBody
    public ResponseData<Boolean> removeTagFromSoundEffect(
            @ApiParam("音效ID") @PathVariable Integer soundEffectId,
            @ApiParam("分类ID") @PathVariable Integer tagId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        if (!checkValidService.isSoundEffectTagExist(tagId)) {
            logger.error("无效的tagId");
            responseData.jsonFill(2, "无效的tagId", null);
            return responseData;
        }
        if (!checkValidService.isSoundEffectExist(soundEffectId)) {
            logger.error("无效的soundEffectId");
            responseData.jsonFill(2, "无效的soundEffectId", null);
            return responseData;
        }
        boolean success = soundEffectTagRelationService.deleteTagRelationBySoundEffectIdAndTagId(soundEffectId, tagId);
        responseData.jsonFill(success ? 1 : 2, null, success);
        return responseData;
    }

    @ApiOperation(value = "获得一个音效的所有分类", notes = "")
    @RequestMapping(value = "/soundEffects/{id}/soundEffectTags", method = {RequestMethod.GET})
    @ResponseBody
    public List<SoundEffectTag> getTagListOfSoundEffect(
            @ApiParam("音效ID") @PathVariable Integer id,
            HttpServletRequest request, HttpServletResponse response) {
        if (!checkValidService.isSoundEffectExist(id)) {
            logger.error("无效的音效Id");
            throw new RuntimeException("无效的音效ID");
        }
        List<Integer> idList = soundEffectTagRelationService.getTagIdListBySoundEffectId(id);
        List<SoundEffectTag> soundEffectTagList = soundEffectTagService.getSoundEffectTagListByIdList(idList);
        return soundEffectTagList;
    }

}
