package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.service.CheckValidService;
import cn.edu.nju.software.service.StoryService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/15.
 */
@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageStoryController {
    private static final Logger logger = LoggerFactory.getLogger(ManageStoryController.class);
    private static final String COVER_ROOT = "/cover/"; //头像的基础路径
    @Autowired
    private StoryService storyService;
    @Autowired
    private CheckValidService checkValidService;

    @ApiOperation(value = "新增故事", notes = "")
    @RequestMapping(value = "/stories", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Story publicStory(
            @ApiParam("故事标题") @RequestParam String title,
            @ApiParam("作者") @RequestParam String author,
            @ApiParam("内容") @RequestParam String content,
            @ApiParam("出版社") @RequestParam String press,
            @ApiParam("阅读指导") @RequestParam String guide,
            @ApiParam("价格") @RequestParam String price,
            @ApiParam("封面") @RequestParam("coverFile") MultipartFile coverFile,
            @ApiParam("预览封面") @RequestParam("preCoverFile") MultipartFile preCoverFile,
            @ApiParam("录制背景") @RequestParam("backgroundFile") MultipartFile backgroundFile,
            @ApiParam("原音") @RequestParam("originSoundFile") MultipartFile originSoundFile,
            HttpServletRequest request, HttpServletResponse response) {
        if (coverFile.isEmpty() || preCoverFile.isEmpty() || backgroundFile.isEmpty() || originSoundFile.isEmpty()) {
            throw new RuntimeException("请选择文件上传。");
        }
        MultipartFile[] files = {coverFile, preCoverFile, backgroundFile, originSoundFile};
        List<String> urlList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            String url = uploadFile(files[i]);
            urlList.add(url);
        }
        Story story = new Story();
        story.setTitle(title);
        story.setAuthor(author);
        story.setContent(content);
        story.setPress(press);
        story.setGuide(guide);
        story.setPrice(price);
        story.setValid(1);
        story.setCreateTime(new Date());
        story.setUpdateTime(new Date());
        story.setCoverUrl(urlList.get(0));
        story.setPreCoverUrl(urlList.get(1));
        story.setBackgroundUrl(urlList.get(2));
        story.setOriginSoundUrl(urlList.get(3));

        boolean res = storyService.saveStory(story);
        if (!res) {
            throw new RuntimeException("发布故事失败");
        }
        return story;
    }


    @ApiOperation(value = "更新故事", notes = "")
    @RequestMapping(value = "/stories/{id}", method = {RequestMethod.POST})
    @ResponseBody
    public Story updateStoryTag(
            @ApiParam("故事ID") @PathVariable int id,
            @ApiParam("故事标题") @RequestParam String title,
            @ApiParam("作者") @RequestParam String author,
            @ApiParam("内容") @RequestParam String content,
            @ApiParam("出版社") @RequestParam String press,
            @ApiParam("阅读指导") @RequestParam String guide,
            @ApiParam("价格") @RequestParam String price,
            @ApiParam("封面") @RequestParam(value = "coverFile", required = false) MultipartFile coverFile,
            @ApiParam("预览封面") @RequestParam(value = "preCoverFile", required = false) MultipartFile preCoverFile,
            @ApiParam("录制背景") @RequestParam(value = "backgroundFile", required = false) MultipartFile backgroundFile,
            @ApiParam("原音") @RequestParam(value = "originSoundFile", required = false) MultipartFile originSoundFile,
            HttpServletRequest request, HttpServletResponse response) {
        Story story = storyService.getStoryById(id);
        if (story == null) {
            throw new RuntimeException("无效的故事id");
        }
        if (!coverFile.isEmpty()) {
            //删除旧的封面
            UploadFileUtil.deleteFileByUrl(story.getCoverUrl());
            story.setCoverUrl(uploadFile(coverFile));
        }
        if (!preCoverFile.isEmpty()) {
            //删除旧
            UploadFileUtil.deleteFileByUrl(story.getPreCoverUrl());
            story.setPreCoverUrl(uploadFile(preCoverFile));
        }
        if (!backgroundFile.isEmpty()) {
            //删除旧
            UploadFileUtil.deleteFileByUrl(story.getBackgroundUrl());
            story.setBackgroundUrl(uploadFile(backgroundFile));
        }
        if (!originSoundFile.isEmpty()) {
            UploadFileUtil.deleteFileByUrl(story.getOriginSoundUrl());
            story.setOriginSoundUrl(uploadFile(originSoundFile));
        }
        story.setTitle(title);
        story.setContent(content);
        story.setAuthor(author);
        story.setPress(press);
        story.setGuide(guide);
        story.setPrice(price);
        story.setUpdateTime(new Date());

        Story result = storyService.updateStory(story);
        if (result == null) {
            throw new RuntimeException("更新失败");
        }
        return result;
    }


    @ApiOperation(value = "删除故事", notes = "")
    @RequestMapping(value = "/stories/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStory(
            @ApiParam("故事ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean success = storyService.deleteStoryById(id);
        if (!success) {
            throw new RuntimeException("删除失败");
        }
    }

    @ApiOperation(value = "根据ID获得故事", notes = "")
    @RequestMapping(value = "/stories/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public Story getStoryId(
            @ApiParam("故事ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        Story story = storyService.getStoryById(id);
        if (story == null) {
            throw new RuntimeException("无效的ID");
        } else {
            return story;
        }
    }

    @ApiOperation(value = "故事列表", notes = "")
    @RequestMapping(value = "/stories", method = {RequestMethod.GET})
    @ResponseBody
    public List<Story> getAllStories(
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        List<Story> result = storyService.getStoryListByPage(offset, limit);
        return result;
    }

    /**
     * 上传封面文件
     *
     * @param file
     * @return
     */
    private String uploadFile(MultipartFile file) {
        String realPath = UploadFileUtil.getBaseUrl() + COVER_ROOT;
        String fileName = RandCharsUtils.getRandomString(16) + "." + UploadFileUtil.getSuffix(file.getOriginalFilename());
        boolean success = UploadFileUtil.mvFile(file, realPath, fileName);
        if (!success) {
            throw new RuntimeException("文件上传失败");
        }
        String url = UploadFileUtil.SOURCE_BASE_URL + COVER_ROOT + fileName;
        return url;
    }

    @RequestMapping(value = "/testPut", method = {RequestMethod.PUT})
    @ResponseBody
    public String testPut(
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit) {

        return "" + offset + limit;
    }

}
