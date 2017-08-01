package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.AlbumRelation;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.AlbumRelationService;
import cn.edu.nju.software.service.AlbumService;
import cn.edu.nju.software.service.CheckValidService;
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


    @ApiOperation(value = "添加标签", notes = "")
    @RequestMapping(value = "/stories/{storyId}/storyAlbums/{albumId}", method = {RequestMethod.POST})
    @ResponseBody
    public Boolean addAlbumToStory(
            @ApiParam("故事ID") @PathVariable Integer storyId,
            @ApiParam("标签ID") @PathVariable Integer albumId,
            HttpServletRequest request, HttpServletResponse response) {
//        if (!checkValidService.isAlbumExist(albumId)) {
//            logger.error("无效的albumId");
//            throw new RuntimeException("无效的albumId");
//        }
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

    @ApiOperation(value = "删除标签", notes = "")
    @RequestMapping(value = "/stories/{storyId}/storyAlbums/{albumId}", method = {RequestMethod.DELETE})
    @ResponseBody
    public ResponseData<Boolean> removeAlbumFromStory(
            @ApiParam("故事ID") @PathVariable Integer storyId,
            @ApiParam("标签ID") @PathVariable Integer albumId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
//        if (!checkValidService.isAlbumExist(albumId)) {
//            logger.error("无效的albumId");
//            responseData.jsonFill(2, "无效的albumId", null);
//            return responseData;
//        }
        if (!checkValidService.isStoryExist(storyId)) {
            logger.error("无效的storyId");
            responseData.jsonFill(2, "无效的storyId", null);
            return responseData;
        }
        boolean success = albumRelationService.deleteAlbumRelationByStoryIdAndAlbumId(storyId, albumId);
        responseData.jsonFill(success ? 1 : 2, null, success);
        return responseData;
    }

//    @ApiOperation(value = "获得一个故事的所有标签", notes = "")
//    @RequestMapping(value = "/stories/{id}/storyAlbums", method = {RequestMethod.GET})
//    @ResponseBody
//    public ResponseData<List<Album>> getAlbumListOfStory(
//            @ApiParam("故事ID") @PathVariable Integer id,
//            HttpServletRequest request, HttpServletResponse response) {
//        if (!checkValidService.isStoryExist(id)) {
//            logger.error("无效的故事Id");
//            throw new RuntimeException("无效的故事ID");
//        }
//        List<Integer> idList = albumRelationService.getAlbumIdListByStoryId(id);
//        List<Album> storyAlbumList = albumService.getStoryAlbumListByIdList(idList);
//        ResponseData<List<Album>> result=new ResponseData<>();
//        if(storyAlbumList==null){
//            result.jsonFill(2,"获得一个故事的所有标签失败",null);
//            return result;
//        }
//        else{
//            result.jsonFill(1,null,storyAlbumList);
//            result.setCount(storyAlbumList.size());
//            return result;
//        }
//    }

}
