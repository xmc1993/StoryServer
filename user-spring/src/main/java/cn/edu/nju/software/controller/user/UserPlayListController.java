package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.*;
import cn.edu.nju.software.service.PlayListRelationService;
import cn.edu.nju.software.service.PlayListService;
import cn.edu.nju.software.service.WorksService;
import cn.edu.nju.software.util.TokenConfig;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Api("tag controller")
@Controller
@RequestMapping("/user/playList")
public class UserPlayListController extends BaseController {
    @Autowired
    private PlayListService playListService;
    @Autowired
    private PlayListRelationService playListRelationService;
    @Autowired
    private WorksService worksService;

    @ApiOperation(value = "获得一个用户的文件夹列表（播放列表）", notes = "")
    @RequestMapping(value = "/getPlayListsByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<PlayList>> getPlayListsByPage(
            @ApiParam("页") @RequestParam int page,
            @ApiParam("页大小") @RequestParam int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<PlayList>> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "用户尚未登录。", null);
            return responseData;
        }
        List<PlayList> playLists = playListService.getAllPlayListByUserIdByPage(user.getId(), page, pageSize);
        responseData.jsonFill(1, null, playLists);
        return responseData;
    }

    @ApiOperation(value = "分页获得用户的一个PlayList下的作品", notes = "")
    @RequestMapping(value = "/getWorksListByPlayListIdByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Works>> getWorksListByPlayListIdByPage(
            @ApiParam("PlayList的ID") @RequestParam int playListId,
            @ApiParam("页") @RequestParam int page,
            @ApiParam("页大小") @RequestParam int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Works>> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "用户尚未登录。", null);
            return responseData;
        }
        List<Integer> idList = playListRelationService.getWorksIdListByPlayListIdAndUserIdByPage(playListId, user.getId(), page, pageSize);
        List<Works> worksList = worksService.getWorksListByIdList(idList);
        responseData.jsonFill(1, null, worksList);
        return responseData;
    }

    @ApiOperation(value = "向一个播放列表中添加作品", notes = "")
    @RequestMapping(value = "/addWorksToPlayList", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> addWorksToPlayList(
            @ApiParam("PlayList的ID") @RequestParam int playListId,
            @ApiParam("作品的编号") @RequestParam int worksId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "用户尚未登录。", null);
            return responseData;
        }
        //TODO 做一些校验
        PlayListRelation playListRelation = new PlayListRelation();
        playListRelation.setCreateTime(new Date());
        playListRelation.setUpdateTime(new Date());
        playListRelation.setPlayListId(playListId);
        playListRelation.setWorksId(worksId);
        playListRelation.setUserId(user.getId());
        boolean res = playListRelationService.savePlayListRelation(playListRelation);
        responseData.jsonFill(res ? 1 : 2, null, res);
        return responseData;
    }

    @ApiOperation(value = "从一个作品中删除作品", notes = "")
    @RequestMapping(value = "/removeWorksFromPlayList", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> removeWorksFromPlayList(
            @ApiParam("PlayList的ID") @RequestParam int playListId,
            @ApiParam("作品的编号") @RequestParam int worksId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "用户尚未登录。", null);
            return responseData;
        }
        //TODO 简单的校验
        boolean res = playListRelationService.deletePlayListRelationByPrimaryKey(worksId, playListId, user.getId());
        responseData.jsonFill(res ? 1 : 2, null, res);
        return responseData;
    }


    @ApiOperation(value = "新增播放列表", notes = "")
    @RequestMapping(value = "/newPlayList", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<PlayList> newPlayList(
            @ApiParam("播放列表的名字") @RequestParam String name,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<PlayList> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "用户尚未登录。", null);
            return responseData;
        }
        //TODO 简单的校验
        PlayList playList = new PlayList();
        playList.setName(name);
        playList.setUserId(user.getId());
        playList.setCreateTime(new Date());
        playList.setUpdateTime(new Date());
        PlayList res = playListService.savePlayList(playList);
        if (res == null) {
            responseData.jsonFill(2, "创建失败", null);
        } else {
            responseData.jsonFill(1, null, res);
        }
        return responseData;
    }

    @ApiOperation(value = "删除播放列表", notes = "")
    @RequestMapping(value = "/removePlayList", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> removePlayList(
            @ApiParam("PlayList的ID") @RequestParam int playListId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "用户尚未登录。", null);
            return responseData;
        }
        PlayList playList = playListService.getPlayListById(playListId);
        if (playList == null) {
            responseData.jsonFill(2, "列表不存在。", null);
            return responseData;
        }
        if (user.getId() != playList.getUserId()) {
            responseData.jsonFill(2, "非法请求。", null);
            return responseData;
        }

        boolean res = playListService.deletePlayList(playListId);
        responseData.jsonFill(res ? 1 : 2, null, res);
        return responseData;
    }


}
