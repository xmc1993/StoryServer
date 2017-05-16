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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/15.
 */
@Api(value = "Admin", description = "管理接口")
@Controller()
public class SoundEffectController {
    private static final Logger logger = LoggerFactory.getLogger(SoundEffectController.class);
    @Autowired
    private SoundEffectService soundEffectService;
    @Autowired
    private CheckValidService checkValidService;

    private static final String SOUND_EFFECT_ROOT = "/soundEffect/"; //头像的基础路径

    @ApiOperation(value = "增加音效", notes = "")
    @RequestMapping(value = "/manage/publishSoundEffect", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> publishSoundEffect(
            @ApiParam("音效文件") @RequestParam("uploadFile") MultipartFile uploadFile,
            @ApiParam("音效描述") @RequestParam("description") String description,
            HttpServletRequest request, HttpServletResponse response) {

        ResponseData<Boolean> responseData = new ResponseData<>();

        if (uploadFile == null) {
            responseData.jsonFill(2, "请选择文件上传", null);
            return responseData;
        }
        logger.info("开始上传音效文件!");

        String realPath = UploadFileUtil.getBaseUrl() + SOUND_EFFECT_ROOT;
        String fileName = RandCharsUtils.getRandomString(16) + "." + UploadFileUtil.getSuffix(uploadFile.getOriginalFilename());
        boolean success = UploadFileUtil.mvFile(uploadFile, realPath, fileName);

        if (!success) {
            responseData.jsonFill(2, "上传音频文件失败", null);
            return responseData;
        }
        SoundEffect soundEffect = new SoundEffect();
        soundEffect.setDescription(description);
        String url = UploadFileUtil.SOURCE_BASE_URL + SOUND_EFFECT_ROOT + fileName;//拼接音频文件的地址
        soundEffect.setUrl(url);
        soundEffect.setCreateTime(new Date());
        soundEffect.setUpdateTime(new Date());
        boolean res = soundEffectService.saveSoundEffect(soundEffect);
        responseData.jsonFill(res ? 1 : 2, null, res);
        return responseData;
    }

    @ApiOperation(value = "更新音效", notes = "")
    @RequestMapping(value = "/manage/updateSoundEffect", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> updateSoundEffect(
            @ApiParam("音效ID") @RequestParam("id") int id,
            @ApiParam("音效文件") @RequestParam("uploadFile") MultipartFile uploadFile,
            @ApiParam("音效描述") @RequestParam("description") String description,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        if (uploadFile == null) {
            responseData.jsonFill(2, "请选择文件上传", null);
            return responseData;
        }
        if (!checkValidService.isSoundEffectExist(id)) {
            responseData.jsonFill(2, "无效的音效id", null);
            return responseData;
        }
        logger.info("开始上传音效文件!");

        String realPath = UploadFileUtil.getBaseUrl() + SOUND_EFFECT_ROOT;
        String fileName = RandCharsUtils.getRandomString(16) + "." + UploadFileUtil.getSuffix(uploadFile.getOriginalFilename());
        boolean success = UploadFileUtil.mvFile(uploadFile, realPath, fileName);

        if (!success) {
            responseData.jsonFill(2, "上传音频文件失败", null);
            return responseData;
        }
        SoundEffect soundEffect = soundEffectService.getSoundEffectById(id);
        String oldUrl = soundEffect.getUrl();
        //TODO 删除老的音效文件 TODO 原子性的问题


        soundEffect.setDescription(description);
        String url = UploadFileUtil.SOURCE_BASE_URL + SOUND_EFFECT_ROOT + fileName;//拼接音频文件的地址
        soundEffect.setUrl(url);
        soundEffect.setUpdateTime(new Date());
        boolean res = soundEffectService.saveSoundEffect(soundEffect);
        responseData.jsonFill(res ? 1 : 2, null, res);
        return responseData;
    }

    @ApiOperation(value = "删除音效", notes = "")
    @RequestMapping(value = "/manage/deleteSoundEffect", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> deleteSoundEffect(
            @ApiParam("音效ID") @RequestParam("id") int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        if (!checkValidService.isSoundEffectExist(id)) {
            responseData.jsonFill(2, "无效的id", null);
            return responseData;
        }
        boolean success = soundEffectService.deleteSoundEffect(id);
        responseData.jsonFill(success ? 1 : 2, null, success);
        return responseData;
    }

    @ApiOperation(value = "得到所有音效", notes = "")
    @RequestMapping(value = "/manage/getAllSoundEffects", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<SoundEffect>> getAllSoundEffects(
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<SoundEffect>> responseData = new ResponseData<>();
        List<SoundEffect> soundEffectList = soundEffectService.getAllSoundEffect();
        responseData.jsonFill(1, null, soundEffectList); //todo 是否要分页
        return responseData;
    }

}
