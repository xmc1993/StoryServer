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
import java.io.File;
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

    @ApiOperation(value = "新增故事", notes = "草稿状态1为草稿0为完成")
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
            @ApiParam("草稿状态") @RequestParam("draft")int draft,
            @ApiParam("默认背景音ID") @RequestParam Integer defaultBackGroundMusicId,
            @ApiParam("封面") @RequestParam("coverFile") MultipartFile coverFile,
            @ApiParam("预览封面") @RequestParam("preCoverFile") MultipartFile preCoverFile,
            @ApiParam("录制背景") @RequestParam("backgroundFile") MultipartFile backgroundFile,
            @ApiParam("原音") @RequestParam("originSoundFile") MultipartFile originSoundFile,
            @ApiParam("朗读指导")@RequestParam("guideSoundFIle")MultipartFile guideSoundFile,
            HttpServletRequest request, HttpServletResponse response) {
        if (coverFile.isEmpty() || preCoverFile.isEmpty() || backgroundFile.isEmpty() || originSoundFile.isEmpty()||guideSoundFile.isEmpty()) {
            throw new RuntimeException("请选择文件上传。");
        }
        MultipartFile[] files = {coverFile, preCoverFile, backgroundFile, originSoundFile,guideSoundFile};
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
        story.setDefaultBackGroundMusicId(defaultBackGroundMusicId);
        story.setCreateTime(new Date());
        story.setUpdateTime(new Date());
        story.setCoverUrl(urlList.get(0));
        story.setPreCoverUrl(urlList.get(1));
        story.setBackgroundUrl(urlList.get(2));
        story.setOriginSoundUrl(urlList.get(3));
        story.setGuideSoundUrl(urlList.get(4));
        String duration=storyService.getOriginSoundLength(new File(UploadFileUtil.getRealPathFromUrl(story.getOriginSoundUrl())));
        story.setDuration(duration);
        story.setDraft(draft);
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
            @ApiParam("草稿状态") @RequestParam("draft")int draft,
            @ApiParam("封面") @RequestParam(value = "coverFile", required = false) MultipartFile coverFile,
            @ApiParam("预览封面") @RequestParam(value = "preCoverFile", required = false) MultipartFile preCoverFile,
            @ApiParam("录制背景") @RequestParam(value = "backgroundFile", required = false) MultipartFile backgroundFile,
            @ApiParam("原音") @RequestParam(value = "originSoundFile", required = false) MultipartFile originSoundFile,
            @ApiParam("朗读指导")@RequestParam("guideSoundFIle")MultipartFile guideSoundFile,
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
        if (!guideSoundFile.isEmpty()) {
            //删除旧
            UploadFileUtil.deleteFileByUrl(story.getGuideSoundUrl());
            story.setGuideSoundUrl(uploadFile(guideSoundFile));
        }
        if (!originSoundFile.isEmpty()) {
            UploadFileUtil.deleteFileByUrl(story.getOriginSoundUrl());
            story.setOriginSoundUrl(uploadFile(originSoundFile));
            String duration=storyService.getOriginSoundLength(new File(UploadFileUtil.getRealPathFromUrl(story.getOriginSoundUrl())));
            story.setDuration(duration);
        }
        story.setTitle(title);
        story.setContent(content);
        story.setAuthor(author);
        story.setPress(press);
        story.setGuide(guide);
        story.setPrice(price);
        story.setUpdateTime(new Date());
        story.setDraft(draft);
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
        Story story = storyService.getStoryByIdIncludeDrafts(id);
        if (story == null) {
            throw new RuntimeException("无效的ID");
        } else {
            return story;
        }
    }

    @ApiOperation(value = "故事列表", notes = "")
    @RequestMapping(value = "/stories", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Story>> getAllStories(
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        List<Story> storyList = storyService.getStoryListByPageIncludeDrafts(offset, limit);
        ResponseData<List<Story>> result=new ResponseData<>();
        if(storyList==null){
            result.jsonFill(2,"获取故事列表失败",null);
            return result;
        }
        else{
            result.jsonFill(1,null,storyList);
            result.setCount(storyService.getStoryCountIncludeDrafts());
            return result;
        }
    }

    @ApiOperation(value = "推荐故事", notes = "")
    @RequestMapping(value = "/stories/{id}/recommendations", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void recommendStory(
            @ApiParam("故事ID") @PathVariable int id){
        boolean res = storyService.recommendStory(id);
        if (!res) {
            throw new RuntimeException("推荐失败");
        }
    }

    @ApiOperation(value = "取消推荐故事", notes = "")
    @RequestMapping(value = "/stories/{id}/recommendations", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelRecommendStory(
            @ApiParam("故事ID") @PathVariable int id){
        boolean res = storyService.cancelRecommendStory(id);
        if (!res) {
            throw new RuntimeException("取消推荐失败");
        }
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
    @ApiOperation(value = "获取故事数量", notes = "")
    @RequestMapping(value = "/storyCount", method = {RequestMethod.GET})
    @ResponseBody
    public Integer getStoryCount(){
        return storyService.getStoryCountIncludeDrafts();
    }

    @ApiOperation(value = "模糊查询获取故事", notes = "")
    @RequestMapping(value = "/storiesByFuzzyQuery", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Story>> getStoryByFuzzyQuery(
            @ApiParam("title") @RequestParam(value = "title",required = false) String title,
            @ApiParam("author") @RequestParam(value = "author",required = false) String author,
            @ApiParam("content") @RequestParam(value = "content",required = false) String content,
            @ApiParam("press") @RequestParam(value = "press",required = false) String press,
            @ApiParam("tag") @RequestParam(value = "tag",required = false) String tag,
            @ApiParam("offset") @RequestParam(value = "offset") int offset,
            @ApiParam("limit") @RequestParam(value = "limit") int limit){
        ResponseData<List<Story>> result=new ResponseData<>();
        List<Story> stories= storyService.getStoryByClassifyFuzzyQueryInludeDrafts(title,author,content,press,tag,offset,limit);
        if(stories==null){
            result.jsonFill(2,"模糊查询失败",null);
            return result;
        }
        else{
            result.jsonFill(1,null,stories);
            result.setCount(storyService.getStoryCountByClassifyFuzzyQueryIncludeDrafts(title,author,content,press,tag));
            return result;
        }
    }

    @ApiOperation(value = "获取草稿列表")
    @RequestMapping(value = "/draftStories", method = {RequestMethod.GET})
    @ResponseBody
    public  ResponseData<List<Story>> getStoryByFuzzyQuery(
            @ApiParam("offset") @RequestParam("offset") int offset,
            @ApiParam("limit") @RequestParam("limit") int limit){
        ResponseData<List<Story>> result=new ResponseData<>();
        List<Story> storyList = storyService.getDraftList(offset,limit);
        result.jsonFill(1,null,storyList);
        result.setCount(storyService.getDraftCount());
        return result;
    }
}
