package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.TagRelation;
import cn.edu.nju.software.service.CheckValidService;
import cn.edu.nju.software.service.StoryService;
import cn.edu.nju.software.service.TagRelationService;
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
    private TagRelationService tagRelationService;
    @Autowired
    private CheckValidService checkValidService;

    @ApiOperation(value = "新增故事", notes = "草稿状态1为草稿0为完成")
    @RequestMapping(value = "/stories", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<Story> publicStory(
            @ApiParam("故事标题") @RequestParam(value = "title" ,required = false) String title,
            @ApiParam("作者") @RequestParam(value = "author" ,required = false) String author,
            @ApiParam("内容") @RequestParam(value = "content" ,required = false) String content,
            @ApiParam("出版社") @RequestParam(value = "press" ,required = false) String press,
            @ApiParam("阅读指导") @RequestParam(value = "guide" ,required = false) String guide,
            @ApiParam("价格") @RequestParam(value = "price" ,required = false) String price,
            @ApiParam("草稿状态") @RequestParam("draft")int draft,
            @ApiParam("朗读指导") @RequestParam(value = "readGuide",required = false) String readGuide,
            @ApiParam("默认背景音ID") @RequestParam Integer defaultBackGroundMusicId,
            @ApiParam("封面") @RequestParam(value = "coverFile", required = false) MultipartFile coverFile,
            @ApiParam("预览封面") @RequestParam(value = "preCoverFile", required = false) MultipartFile preCoverFile,
            @ApiParam("录制背景") @RequestParam(value = "backgroundFile", required = false) MultipartFile backgroundFile,
            @ApiParam("原音") @RequestParam(value = "originSoundFile", required = false) MultipartFile originSoundFile,
            @ApiParam("标签列表") @RequestParam(value = "tagList", required = false) String tagList,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Story> result = new ResponseData<>();
        Story dbStory=storyService.getExactStoryByTitle(title);
        if(dbStory!=null) {
            result.jsonFill(2,"重复的标题名称",dbStory);
        }
        Story story = new Story();
        if (!coverFile.isEmpty()) {
            story.setCoverUrl(uploadFile(coverFile));
        }
        if (!preCoverFile.isEmpty()) {
            story.setPreCoverUrl(uploadFile(preCoverFile));
        }
        if (!backgroundFile.isEmpty()) {
            story.setBackgroundUrl(uploadFile(backgroundFile));
        }
        if (!originSoundFile.isEmpty()) {
            story.setOriginSoundUrl(uploadFile(originSoundFile));
            String duration=storyService.getOriginSoundLength(new File(UploadFileUtil.getRealPathFromUrl(story.getOriginSoundUrl())));
            story.setDuration(duration);
        }
        if(title!=null)story.setTitle(title);
        if(author!=null)story.setAuthor(author);
        if(content!=null)story.setContent(content);
        if(press!=null)story.setPress(press);
        if(guide!=null)story.setGuide(guide);
        if(price!=null)story.setPrice(price);
        if(readGuide!=null) story.setReadGuide(readGuide);
        story.setValid(1);
        story.setDefaultBackGroundMusicId(defaultBackGroundMusicId);
        story.setCreateTime(new Date());
        story.setUpdateTime(new Date());
        story.setDraft(draft);
        story.setLikeCount(0);
        Story res = storyService.saveStory(story);
        if (res == null) {
            throw new RuntimeException("发布故事失败");
        }
        if (tagList != null) {
            String[] tags = tagList.split(",");
            for (String tag : tags) {
                TagRelation tagRelation = new TagRelation();
                tagRelation.setStoryId(story.getId());
                tagRelation.setTagId(Integer.parseInt(tag));
                tagRelation.setCreateTime(new Date());
                tagRelation.setUpdateTime(new Date());
                tagRelationService.saveTagRelation(tagRelation);
            }
        }
        result.jsonFill(1,null,story);
        return result;
    }


    @ApiOperation(value = "更新故事", notes = "")
    @RequestMapping(value = "/stories/{id}", method = {RequestMethod.POST})
    @ResponseBody
    public Story updateStoryTag(
            @ApiParam("故事ID") @PathVariable int id,
            @ApiParam("故事标题") @RequestParam(value = "title",required = false) String title,
            @ApiParam("作者") @RequestParam(value = "author" ,required = false) String author,
            @ApiParam("内容") @RequestParam(value = "content" ,required = false) String content,
            @ApiParam("出版社") @RequestParam(value = "press" ,required = false) String press,
            @ApiParam("阅读指导") @RequestParam(value = "guide" ,required = false) String guide,
            @ApiParam("价格") @RequestParam(value = "price" ,required = false) String price,
            @ApiParam("草稿状态") @RequestParam("draft")int draft,
            @ApiParam("朗读指导") @RequestParam(value = "readGuide",required = false) String readGuide,
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
            if(story.getCoverUrl()!=null)UploadFileUtil.deleteFileByUrl(story.getCoverUrl());
            story.setCoverUrl(uploadFile(coverFile));
        }
        if (!preCoverFile.isEmpty()) {
            //删除旧
            if(story.getPreCoverUrl()!=null)UploadFileUtil.deleteFileByUrl(story.getPreCoverUrl());
            story.setPreCoverUrl(uploadFile(preCoverFile));
        }
        if (!backgroundFile.isEmpty()) {
            //删除旧
            if(story.getBackgroundUrl()!=null)UploadFileUtil.deleteFileByUrl(story.getBackgroundUrl());
            story.setBackgroundUrl(uploadFile(backgroundFile));
        }
        if (!originSoundFile.isEmpty()) {
            if(story.getOriginSoundUrl()!=null)UploadFileUtil.deleteFileByUrl(story.getOriginSoundUrl());
            story.setOriginSoundUrl(uploadFile(originSoundFile));
            String duration=storyService.getOriginSoundLength(new File(UploadFileUtil.getRealPathFromUrl(story.getOriginSoundUrl())));
            story.setDuration(duration);
        }
        if(title!=null)story.setTitle(title);
        if(content!=null)story.setContent(content);
        if(author!=null)story.setAuthor(author);
        if(press!=null)story.setPress(press);
        if(guide!=null)story.setGuide(guide);
        if(price!=null)story.setPrice(price);
        if(readGuide!=null)story.setReadGuide(readGuide);
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
