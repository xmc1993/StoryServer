package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.*;
import cn.edu.nju.software.service.*;
import cn.edu.nju.software.vo.StoryNewVo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
public class ManageAlbumRelationController {
    private static final Logger logger = LoggerFactory.getLogger(ManageAlbumRelationController.class);
    @Autowired
    private AlbumRelationService albumRelationService;
    @Autowired
    private CheckValidService checkValidService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private TagRelationService tagRelationService;
    @Autowired
    private StoryTagService storyTagService;
    @Autowired
    private StoryService storyService;

    @ApiOperation(value = "把故事添加进专辑", notes = "")
    @RequestMapping(value = "/stories/{storyId}/storyAlbums/{albumId}", method = {RequestMethod.POST})
    @ResponseBody
    public Boolean addAlbumToStory(
            @ApiParam("故事ID") @PathVariable Integer storyId,
            @ApiParam("专辑ID") @PathVariable Integer albumId,
            HttpServletRequest request, HttpServletResponse response) {
        if (!checkValidService.isStoryExist(storyId)) {
            logger.error("无效的storyId");
            throw new RuntimeException("无效的storyId");
        }

        AlbumRelation albumRelation = new AlbumRelation();
        albumRelation.setAlbumId(albumId);
        albumRelation.setStoryId(storyId);
        albumRelation.setCreateTime(new Date());
        albumRelation.setUpdateTime(new Date());
        boolean success = albumRelationService.saveAlbumRelation(albumRelation);
        return success;
    }

    @ApiOperation(value = "从专辑中移除故事", notes = "")
    @RequestMapping(value = "/stories/{storyId}/storyAlbums/{albumId}", method = {RequestMethod.DELETE})
    @ResponseBody
    public ResponseData<Boolean> removeAlbumFromStory(
            @ApiParam("故事ID") @PathVariable Integer storyId,
            @ApiParam("专辑ID") @PathVariable Integer albumId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        if (!checkValidService.isStoryExist(storyId)) {
            logger.error("无效的storyId");
            responseData.jsonFill(2, "无效的storyId", null);
            return responseData;
        }
        boolean success = albumRelationService.deleteAlbumRelationByStoryIdAndAlbumId(storyId, albumId);
        responseData.jsonFill(success ? 1 : 2, null, success);
        return responseData;
    }

    @ApiOperation(value = "获得一个故事的所属的所有专辑", notes = "")
    @RequestMapping(value = "/stories/{id}/storyAlbums", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Album>> getAlbumListOfStory(
            @ApiParam("故事ID") @PathVariable Integer id,
            HttpServletRequest request, HttpServletResponse response) {
        if (!checkValidService.isStoryExist(id)) {
            logger.error("无效的故事Id");
            throw new RuntimeException("无效的故事ID");
        }
        List<Integer> idList = albumRelationService.getAlbumIdListByStoryId(id);
        List<Album> storyAlbumList = albumService.getAlbumListByIdList(idList);
        ResponseData<List<Album>> result=new ResponseData<>();
        if(storyAlbumList==null){
            result.jsonFill(2,"获得一个故事的所有专辑失败",null);
            return result;
        }
        else{
            result.jsonFill(1,null,storyAlbumList);
            result.setCount(storyAlbumList.size());
            return result;
        }
    }

    @ApiOperation(value = "获得一个专辑下的所有故事", notes = "")
    @RequestMapping(value = "/storyAlbums/{id}/stories", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<StoryNewVo>> getStoryListOfAlbum(
            @ApiParam("专辑ID") @PathVariable Integer id,
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        if (!checkValidService.isStoryExist(id)) {
            logger.error("无效的故事Id");
            throw new RuntimeException("无效的故事ID");
        }
        List<Integer> idList = albumRelationService.getStoryIdListByAlbumId(id);
        List<Story> storyList = storyService.getStoryListByIdList(idList, offset, limit);
        ResponseData<List<StoryNewVo>> result=new ResponseData<>();
        if(storyList==null){
            result.jsonFill(2,"获得一个故事的所有专辑失败",null);
            return result;
        }
        else{
            List<StoryNewVo> storyNewVoList = storyList2VoList(storyList);
            result.jsonFill(1,null, storyNewVoList);
            result.setCount(idList.size());
            return result;
        }
    }


    /**
     * Story列表转Vo列表
     * @param list
     * @return
     */
    private List<StoryNewVo> storyList2VoList(List<Story> list) {
        List<StoryNewVo> voList = new ArrayList<>();
        if (list == null) {
            return voList;
        }
        for (Story story : list) {
            voList.add(story2vo(story));
        }
        return voList;
    }

    /**
     * Story转Vo
     * @param story
     * @return
     */
    private StoryNewVo story2vo(Story story) {
        if (story == null) {
            return null;
        }
        StoryNewVo storyVo = new StoryNewVo();
        BeanUtils.copyProperties(story, storyVo);
        List<Integer> idList = tagRelationService.getTagIdListByStoryId(storyVo.getId());
        List<StoryTag> storyTagList = storyTagService.getStoryTagListByIdList(idList);
        storyVo.setTagList(storyTagList);
        return storyVo;
    }

}
