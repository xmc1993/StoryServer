package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.BackgroundMusic;
import cn.edu.nju.software.entity.BackgroundMusicTagRelation;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.BackgroundMusicService;
import cn.edu.nju.software.service.BackgroundMusicTagRelationService;
import cn.edu.nju.software.service.CheckValidService;
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
public class ManageBackgroundMusicController {
    private static final Logger logger = LoggerFactory.getLogger(ManageBackgroundMusicController.class);
    @Autowired
    private BackgroundMusicService backgroundMusicService;
    @Autowired
    private CheckValidService checkValidService;
    @Autowired
    private BackgroundMusicTagRelationService backgroundMusicTagRelationService;

    private static final String SOUND_EFFECT_ROOT = "/backgroundMusic/"; //头像的基础路径

    @ApiOperation(value = "增加背景音乐", notes = "")
    @RequestMapping(value = "/backgroundMusics", method = {RequestMethod.POST})
    @ResponseBody
    public BackgroundMusic publishBackgroundMusic(
            @ApiParam("背景音乐文件") @RequestParam("uploadFile") MultipartFile uploadFile,
            @ApiParam("背景音乐描述") @RequestParam("description") String description,
            @ApiParam("标签列表") @RequestParam(value = "tagList", required = false) String tagList,
            HttpServletRequest request, HttpServletResponse response) {

        if (uploadFile.isEmpty()) {
            throw new RuntimeException("请选择文件上传");
        }
        logger.info("开始上传背景音乐文件!");

        String realPath = UploadFileUtil.getBaseUrl() + SOUND_EFFECT_ROOT;
        String fileName = RandCharsUtils.getRandomString(16) + "." + UploadFileUtil.getSuffix(uploadFile.getOriginalFilename());
        boolean success = UploadFileUtil.mvFile(uploadFile, realPath, fileName);

        if (!success) {
            throw new RuntimeException("文件上传失败");
        }
        BackgroundMusic backgroundMusic = new BackgroundMusic();
        backgroundMusic.setDescription(description);
        String url = UploadFileUtil.SOURCE_BASE_URL + SOUND_EFFECT_ROOT + fileName;//拼接音频文件的地址
        backgroundMusic.setUrl(url);
        backgroundMusic.setCreateTime(new Date());
        backgroundMusic.setUpdateTime(new Date());
        BackgroundMusic res = backgroundMusicService.saveBackgroundMusic(backgroundMusic);
        if (res != null) {
            if (tagList != null) {
                String[] tags = tagList.split(",");
                for (String tag : tags) {
                    BackgroundMusicTagRelation tagRelation = new BackgroundMusicTagRelation();
                    tagRelation.setBackgroundMusicId(res.getId());
                    tagRelation.setTagId(Integer.parseInt(tag));
                    tagRelation.setCreateTime(new Date());
                    tagRelation.setUpdateTime(new Date());
                    backgroundMusicTagRelationService.saveTagRelation(tagRelation);
                }
            }
        }
        if (res != null) {
            return backgroundMusic;
        } else {
            throw new RuntimeException("发布失败。");
        }
    }

    @ApiOperation(value = "更新背景音乐", notes = "")
    @RequestMapping(value = "/backgroundMusics/{id}", method = {RequestMethod.POST})
    @ResponseBody
    public BackgroundMusic updateBackgroundMusic(
            @ApiParam("背景音乐ID") @PathVariable int id,
            @ApiParam("背景音乐文件") @RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile,
            @ApiParam("背景音乐描述") @RequestParam("description") String description,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();

        if (!checkValidService.isBackgroundMusicExist(id)) {
            responseData.jsonFill(2, "无效的背景音乐id", null);
            throw new RuntimeException("无效的背景音乐id");
        }
        BackgroundMusic backgroundMusic = backgroundMusicService.getBackgroundMusicById(id);

        if (!uploadFile.isEmpty()) {

            logger.info("开始上传背景音乐文件!");
            String realPath = UploadFileUtil.getBaseUrl() + SOUND_EFFECT_ROOT;
            String fileName = RandCharsUtils.getRandomString(16) + "." + UploadFileUtil.getSuffix(uploadFile.getOriginalFilename());
            boolean success = UploadFileUtil.mvFile(uploadFile, realPath, fileName);

            if (!success) {
                throw new RuntimeException("上传音频文件失败");
            }
            //TODO 删除老的背景音乐文件 TODO 原子性的问题
            String url = UploadFileUtil.SOURCE_BASE_URL + SOUND_EFFECT_ROOT + fileName;//拼接音频文件的地址
            backgroundMusic.setUrl(url);
        }

        backgroundMusic.setDescription(description);
        backgroundMusic.setUpdateTime(new Date());
        BackgroundMusic result = backgroundMusicService.updateBackgroundMusic(backgroundMusic);
        if (result != null) {
            return result;
        } else {
            throw new RuntimeException("更新失败。");
        }
    }

    @ApiOperation(value = "删除背景音乐", notes = "")
    @RequestMapping(value = "/backgroundMusics/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBackgroundMusic(
            @ApiParam("背景音乐ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        if (!checkValidService.isBackgroundMusicExist(id)) {
            throw new RuntimeException("无效的id");
        }
        boolean success = backgroundMusicService.deleteBackgroundMusic(id);

        if (!success) {
            throw new RuntimeException("删除失败。");
        }
    }


    @ApiOperation(value = "根据ID得到背景音乐", notes = "")
    @RequestMapping(value = "/backgroundMusics/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public BackgroundMusic getBackgroundMusicById(
            @ApiParam("背景音乐ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        BackgroundMusic backgroundMusic = backgroundMusicService.getBackgroundMusicById(id);
        if (null == backgroundMusic) {
            throw new RuntimeException("获取背景音乐失败。");
        } else {
            return backgroundMusic;
        }
    }

    @ApiOperation(value = "得到所有背景音乐", notes = "")
    @RequestMapping(value = "/backgroundMusics", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<BackgroundMusic>> getAllBackgroundMusics(
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        List<BackgroundMusic> backgroundMusicList = backgroundMusicService.getBackgroundMusicListByPage(offset, limit);
        ResponseData<List<BackgroundMusic>> result = new ResponseData<>();
        if (backgroundMusicList == null) {
            result.jsonFill(2, "获取背景音乐列表失败", null);
            return result;
        } else {
            result.jsonFill(1, null, backgroundMusicList);
            result.setCount(backgroundMusicService.getBackgroundMusicCount());
            return result;
        }
    }

    @ApiOperation(value = "获取背景音乐数量", notes = "")
    @RequestMapping(value = "/backgroundMusicCount", method = {RequestMethod.GET})
    @ResponseBody
    public Integer getBackgroundMusicCount() {
        return backgroundMusicService.getBackgroundMusicCount();
    }
}
