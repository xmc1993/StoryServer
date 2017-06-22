package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.BackgroundMusic;
import cn.edu.nju.software.entity.BackgroundMusicTag;
import cn.edu.nju.software.entity.BackgroundMusicTagRelation;
import cn.edu.nju.software.service.CheckValidService;
import cn.edu.nju.software.service.BackgroundMusicService;
import cn.edu.nju.software.service.BackgroundMusicTagRelationService;
import cn.edu.nju.software.service.BackgroundMusicTagService;
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
public class ManageBackgroundMusicTagRelationController {
    private static final Logger logger = LoggerFactory.getLogger(ManageBackgroundMusicTagRelationController.class);
    @Autowired
    private BackgroundMusicTagRelationService backgroundMusicTagRelationService;
    @Autowired
    private CheckValidService checkValidService;
    @Autowired
    private BackgroundMusicTagService backgroundMusicTagService;
    @Autowired
    private BackgroundMusicService backgroundMusicService;


    @ApiOperation(value = "给背景音乐添加分类", notes = "")
    @RequestMapping(value = "/backgroundMusics/{backgroundMusicId}/backgroundMusicTags/{tagId}", method = {RequestMethod.POST})
    @ResponseBody
    public Boolean addTagToBackgroundMusic(
            @ApiParam("背景音乐ID") @PathVariable Integer backgroundMusicId,
            @ApiParam("分类ID") @PathVariable Integer tagId,
            HttpServletRequest request, HttpServletResponse response) {
        if (!checkValidService.isBackgroundMusicTagExist(tagId)) {
            logger.error("无效的tagId");
            throw new RuntimeException("无效的tagId");
        }
        if (!checkValidService.isBackgroundMusicExist(backgroundMusicId)) {
            logger.error("无效的backgroundMusicId");
            throw new RuntimeException("无效的backgroundMusicId");
        }

        BackgroundMusicTagRelation backgroundMusicTagRelation = new BackgroundMusicTagRelation();
        backgroundMusicTagRelation.setTagId(tagId);
        backgroundMusicTagRelation.setBackgroundMusicId(backgroundMusicId);
        backgroundMusicTagRelation.setCreateTime(new Date());
        backgroundMusicTagRelation.setUpdateTime(new Date());
        boolean success = backgroundMusicTagRelationService.saveTagRelation(backgroundMusicTagRelation);
        return success;
    }

    @ApiOperation(value = "删除背景音乐的一个分类", notes = "")
    @RequestMapping(value = "/backgroundMusics/{backgroundMusicId}/backgroundMusicTags/{tagId}", method = {RequestMethod.DELETE})
    @ResponseBody
    public Boolean removeTagFromBackgroundMusic(
            @ApiParam("背景音乐ID") @PathVariable Integer backgroundMusicId,
            @ApiParam("分类ID") @PathVariable Integer tagId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        if (!checkValidService.isBackgroundMusicTagExist(tagId)) {
            throw new RuntimeException("无效的tagId");
        }
        if (!checkValidService.isBackgroundMusicExist(backgroundMusicId)) {

            throw new RuntimeException("无效的backgroundMusicId");
        }
        boolean success = backgroundMusicTagRelationService.deleteTagRelationByBackgroundMusicIdAndTagId(backgroundMusicId, tagId);
        return success;
    }

    @ApiOperation(value = "获得一个背景音乐的所有分类", notes = "")
    @RequestMapping(value = "/backgroundMusics/{id}/backgroundMusicTags", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<BackgroundMusicTag>> getTagListOfBackgroundMusic(
            @ApiParam("背景音乐ID") @PathVariable Integer id,
            HttpServletRequest request, HttpServletResponse response) {
        if (!checkValidService.isBackgroundMusicExist(id)) {
            logger.error("无效的背景音乐Id");
            throw new RuntimeException("无效的背景音乐ID");
        }
        List<Integer> idList = backgroundMusicTagRelationService.getTagIdListByBackgroundMusicId(id);
        List<BackgroundMusicTag> backgroundMusicTagList = backgroundMusicTagService.getBackgroundMusicTagListByIdList(idList);
        ResponseData<List<BackgroundMusicTag>> result=new ResponseData<>();
        if(backgroundMusicTagList==null){
            result.jsonFill(2,"获取背景音乐所有分类失败",null);
            return result;
        }
        else{
            result.jsonFill(1,null,backgroundMusicTagList);
            result.setCount(backgroundMusicTagList.size());
            return result;
        }
    }

    @ApiOperation(value = "获得一个分类下的所有背景音乐", notes = "")
    @RequestMapping(value = "/backgroundMusicTags/{id}/backgroundMusics", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<BackgroundMusic>> getBackgroundMusicListOfTag(
            @ApiParam("分类ID") @PathVariable Integer id,
            HttpServletRequest request, HttpServletResponse response) {
        /*if (!checkValidService.isBackgroundMusicExist(id)) {
            logger.error("无效的背景音乐Id");
            throw new RuntimeException("无效的背景音乐ID");
        }*/
        if (!checkValidService.isBackgroundMusicTagExist(id)) {
            logger.error("无效的背景音乐分类Id");
            throw new RuntimeException("无效的背景音乐分类ID");
        }
        List<Integer> idList = backgroundMusicTagRelationService.getBackgroundMusicIdListByTagId(id);
        List<BackgroundMusic> backgroundMusicList = backgroundMusicService.getBackgroundMusicListByIdList(idList);
        ResponseData<List<BackgroundMusic>> result=new ResponseData<>();
        if(backgroundMusicList==null){
            result.jsonFill(2,"获取一个分类下的所有背景音乐失败",null);
            return result;
        }
        else{
            result.jsonFill(1,null,backgroundMusicList);
            result.setCount(backgroundMusicList.size());
            return result;
        }
    }
}
