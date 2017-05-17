package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.SoundEffect;
import cn.edu.nju.software.service.CheckValidService;
import cn.edu.nju.software.service.SoundEffectService;
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
public class SoundEffectController {
    private static final Logger logger = LoggerFactory.getLogger(SoundEffectController.class);
    @Autowired
    private SoundEffectService soundEffectService;
    @Autowired
    private CheckValidService checkValidService;

    private static final String SOUND_EFFECT_ROOT = "/soundEffect/"; //头像的基础路径

    @ApiOperation(value = "增加音效", notes = "")
    @RequestMapping(value = "/soundEffects", method = {RequestMethod.POST})
    @ResponseBody
    public SoundEffect publishSoundEffect(
            @ApiParam("音效文件") @RequestParam("uploadFile") MultipartFile uploadFile,
            @ApiParam("音效描述") @RequestParam("description") String description,
            HttpServletRequest request, HttpServletResponse response) {

        if (uploadFile == null) {
            throw new RuntimeException("请选择文件上传");
        }
        logger.info("开始上传音效文件!");

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
        soundEffect.setCreateTime(new Date());
        soundEffect.setUpdateTime(new Date());
        boolean res = soundEffectService.saveSoundEffect(soundEffect);
        if (res) {
            return soundEffect;
        } else {
            throw new RuntimeException("发布失败。");
        }
    }

    @ApiOperation(value = "更新音效", notes = "")
    @RequestMapping(value = "/soundEffects/{id}", method = {RequestMethod.PUT})
    @ResponseBody
    public SoundEffect updateSoundEffect(
            @ApiParam("音效ID") @PathVariable int id,
            @ApiParam("音效文件") @RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile,
            @ApiParam("音效描述") @RequestParam("description") String description,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();

        if (!checkValidService.isSoundEffectExist(id)) {
            responseData.jsonFill(2, "无效的音效id", null);
            throw new RuntimeException("无效的音效id");
        }
        SoundEffect soundEffect = soundEffectService.getSoundEffectById(id);

        if (uploadFile != null) {

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
        soundEffect.setUpdateTime(new Date());
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
    public List<SoundEffect> getAllSoundEffects(
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        List<SoundEffect> soundEffectList = soundEffectService.getSoundEffectListByPage(offset, limit);
        return soundEffectList;
    }

}
