package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.SoundEffect;
import cn.edu.nju.software.entity.SoundEffectTagRelation;
import cn.edu.nju.software.service.CheckValidService;
import cn.edu.nju.software.service.SoundEffectService;
import cn.edu.nju.software.service.SoundEffectTagRelationService;
import cn.edu.nju.software.service.SoundEffectTagService;
import cn.edu.nju.software.service.wxpay.util.RandCharsUtils;
import cn.edu.nju.software.util.UploadFileUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
public class ManageSoundEffectController {
    private static final Logger logger = LoggerFactory.getLogger(ManageSoundEffectController.class);
    @Autowired
    private SoundEffectService soundEffectService;
    @Autowired
    private CheckValidService checkValidService;
    @Autowired
    private SoundEffectTagService soundEffectTagService;
    @Autowired
    private SoundEffectTagRelationService soundEffectTagRelationService;

    private static final String SOUND_EFFECT_ROOT = "/soundEffect/"; //头像的基础路径

    @ApiOperation(value = "增加音效", notes = "")
    @RequestMapping(value = "/soundEffects", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> publishSoundEffect(
            @ApiParam("音效标签ID") @RequestParam("tagId") int tagId,
            @ApiParam("音效文件") @RequestParam("uploadFile") MultipartFile[] uploadFiles,
            @ApiParam("音效描述") @RequestParam("description") String description,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> result = new ResponseData<>();
        if(soundEffectTagService.getSoundEffectTagById(tagId)==null){
            throw new RuntimeException("错误的音效标签");
        }
        if (uploadFiles==null||uploadFiles.length==0) {
            throw new RuntimeException("请选择文件上传");
        }
        logger.info("开始上传音效文件!");
        for(MultipartFile uploadFile:uploadFiles) {
            String realPath = UploadFileUtil.getBaseUrl() + SOUND_EFFECT_ROOT;
            String fileName = RandCharsUtils.getRandomString(16) + "." + UploadFileUtil.getSuffix(uploadFile.getOriginalFilename());
            boolean success = UploadFileUtil.mvFile(uploadFile, realPath, fileName);
            if (!success) {
                throw new RuntimeException("文件上传失败");
            }
            SoundEffect soundEffect = new SoundEffect();
            soundEffect.setDescription(description);
            String url = UploadFileUtil.SOURCE_BASE_URL + SOUND_EFFECT_ROOT + fileName;//拼接音频文件的地址
            soundEffect.setUrl(url);
            Date date = new Date();
            soundEffect.setCreateTime(date);
            soundEffect.setUpdateTime(date);
            boolean saveEffectBoolean = soundEffectService.saveSoundEffect(soundEffect);
            if (!saveEffectBoolean) {
                throw new RuntimeException("发布失败。");
            }
        /*boolean res = soundEffectService.saveSoundEffect(soundEffect);
        if (!res){
            throw new RuntimeException("发布失败。");
        }*/
            SoundEffectTagRelation soundEffectTagRelation = new SoundEffectTagRelation();
            soundEffectTagRelation.setCreateTime(date);
            soundEffectTagRelation.setUpdateTime(date);
            soundEffectTagRelation.setSoundEffectId(soundEffect.getId());
            soundEffectTagRelation.setTagId(tagId);
            boolean saveTagRelation = soundEffectTagRelationService.saveTagRelation(soundEffectTagRelation);
            if (!saveTagRelation) {
                throw new RuntimeException("发布失败");
            }
        }
        result.jsonFill(1,null,true);
        return result;
    }

    @RequestMapping(value = "/testSoundEffect",method = {RequestMethod.POST})
    @ResponseBody
    public SoundEffect testSoundEffect( @ApiParam("音效ID") @RequestParam(value = "id") int id,
                                        @ApiParam("音效标签ID") @RequestParam(value = "tagId",required = false) Integer tagId,
                                        @ApiParam("音效描述") @RequestParam("description") String description,
                                        HttpServletRequest request, HttpServletResponse response
    ){
        if (!checkValidService.isSoundEffectExist(id)) {
            throw new RuntimeException("无效的音效id");
        }
        if(soundEffectTagService.getSoundEffectTagById(tagId)==null){
            throw new RuntimeException("错误的音效标签");
        }
        SoundEffect soundEffect = soundEffectService.getSoundEffectById(id);
        soundEffect.setDescription(description);
        Date date = new Date();
        soundEffect.setUpdateTime(date);
        if(tagId!=null){
            boolean temp =soundEffectTagRelationService.updateRelation(id,tagId, date);
            if(temp!=true)  throw new RuntimeException("更新失败。");
        }
        SoundEffect result = soundEffectService.updateSoundEffect(soundEffect);
        if (result != null) {
            return result;
        } else {
            throw new RuntimeException("更新失败。");
        }
    }
    @ApiOperation(value = "更新音效", notes = "")
    @RequestMapping(value = "/soundEffects/{id}", method = {RequestMethod.POST})
    @ResponseBody
    public SoundEffect updateSoundEffect(
            @ApiParam("音效ID") @PathVariable int id,
            @ApiParam("音效标签ID") @RequestParam(value = "tagId",required = false) Integer tagId,
            @ApiParam("音效文件") @RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile,
            @ApiParam("音效描述") @RequestParam("description") String description,
            HttpServletRequest request, HttpServletResponse response) {
        if (!checkValidService.isSoundEffectExist(id)) {
            throw new RuntimeException("无效的音效id");
        }
        if(soundEffectTagService.getSoundEffectTagById(tagId)==null){
            throw new RuntimeException("错误的音效标签");
        }
        SoundEffect soundEffect = soundEffectService.getSoundEffectById(id);
        if (!uploadFile.isEmpty()) {
            logger.info("开始上传音效文件!");
            String realPath = UploadFileUtil.getBaseUrl() + SOUND_EFFECT_ROOT;
            String fileName = RandCharsUtils.getRandomString(16) + "." + UploadFileUtil.getSuffix(uploadFile.getOriginalFilename());
            boolean success = UploadFileUtil.mvFile(uploadFile, realPath, fileName);
            if (!success) {
                throw new RuntimeException("上传音频文件失败");
            }
            //TODO 删除老的音效文件 TODO 原子性的问题
            String url = UploadFileUtil.SOURCE_BASE_URL + SOUND_EFFECT_ROOT + fileName;//拼接音频文件的地址
            soundEffect.setUrl(url);
        }
        soundEffect.setDescription(description);
        Date date = new Date();
        soundEffect.setUpdateTime(date);
        if(tagId!=null){
            boolean temp =soundEffectTagRelationService.updateRelation(id,tagId,date);
            if(temp!=true)  throw new RuntimeException("更新失败。");
        }
        SoundEffect result = soundEffectService.updateSoundEffect(soundEffect);
        if (result != null) {
            return result;
        } else {
            throw new RuntimeException("更新失败。");
        }

    }

    @ApiOperation(value = "删除音效", notes = "")
         @RequestMapping(value = "/soundEffects/{id}", method = {RequestMethod.DELETE})
         @ResponseBody
         @ResponseStatus(HttpStatus.NO_CONTENT)
         public void deleteSoundEffect(
            @ApiParam("音效ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        if (!checkValidService.isSoundEffectExist(id)) {
            throw new RuntimeException("无效的id");
        }
        boolean success = soundEffectService.deleteSoundEffect(id);

        if (!success){
            throw new RuntimeException("删除失败。");
        }
    }


    @ApiOperation(value = "根据ID得到音效", notes = "")
    @RequestMapping(value = "/soundEffects/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public SoundEffect getSoundEffectById(
            @ApiParam("音效ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        SoundEffect soundEffect = soundEffectService.getSoundEffectById(id);
        if (null == soundEffect ){
            throw new RuntimeException("获取音效失败。");
        }else {
            return soundEffect;
        }
    }

    @ApiOperation(value = "得到所有音效", notes = "")
    @RequestMapping(value = "/soundEffects", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<SoundEffect>> getAllSoundEffects(
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        List<SoundEffect> soundEffectList = soundEffectService.getSoundEffectListByPage(offset, limit);
        ResponseData<List<SoundEffect>> result=new ResponseData<>();
        if(soundEffectList==null){
            result.jsonFill(2,"获取音效列表失败",null);
            return result;
        }
        else{
            result.jsonFill(1,null,soundEffectList);
            result.setCount(soundEffectService.getSoundEffectCount());
            return result;
        }
    }

    @ApiOperation(value = "获取背景音效数量", notes = "")
    @RequestMapping(value = "/soundEffectCount", method = {RequestMethod.GET})
    @ResponseBody
    public Integer getSoundEffectCount(){
        return soundEffectService.getSoundEffectCount();
    }
}
