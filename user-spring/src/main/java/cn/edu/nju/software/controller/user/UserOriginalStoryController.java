package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.dto.MsgVo;
import cn.edu.nju.software.entity.*;
import cn.edu.nju.software.entity.feed.MessageType;
import cn.edu.nju.software.enums.Visibility;
import cn.edu.nju.software.feed.service.MessageFeedService;
import cn.edu.nju.software.service.BadgeCheckService;
import cn.edu.nju.software.service.FollowService;
import cn.edu.nju.software.service.TagRelationService;
import cn.edu.nju.software.service.UserStoryService;
import cn.edu.nju.software.util.TokenConfig;
import com.google.gson.Gson;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Api("userStory controller")
@Controller
@RequestMapping("/user/originalStory")
public class UserOriginalStoryController extends BaseController {
    @Autowired
    private UserStoryService userStoryService;
    @Autowired
    private TagRelationService tagRelationService;
    @Autowired
    private FollowService followService;
    @Autowired
    private MessageFeedService messageFeedService;
    @Autowired
    private BadgeCheckService badgeCheckService;

    @ApiOperation(value = "获得一个用户的所有原创故事", notes = "")
    @RequestMapping(value = "/getAllUserStoryByUserIdByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<UserStory>> getUserStoryListByParentId(
            @ApiParam("用户ID") @RequestParam("userId") int userId,
            @ApiParam("page") @RequestParam int page,
            @ApiParam("pageSize") @RequestParam int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<UserStory>> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "用户尚未登录。", null);
            return responseData;
        }
        int relation = followService.getRelation(userId, user.getId());
        List<UserStory> userStoryList = userStoryService.getAllUserStoryByUserIdByPage(userId, relation, page, pageSize);
        responseData.jsonFill(1, null, userStoryList);
        responseData.setCount(userStoryService.getUserStoryCountByUserId(userId, relation));
        return responseData;
    }

    @ApiOperation(value = "添加原创故事", notes = "Auth")
    @RequestMapping(value = "/addUserStory", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<UserStory> addUserStory(
            @ApiParam("故事标题") @RequestParam(value = "title", required = false) String title,
            @ApiParam("内容") @RequestParam(value = "content", required = false) String content,
            @ApiParam("封面内容") @RequestParam(value = "coverUrl", required = false) String coverUrl,
            @ApiParam("可见性") @RequestParam(value = "visibility") Integer visibility,
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseData<UserStory> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "用户尚未登录。", null);
            return responseData;
        }
        Integer userId=user.getId();
        UserStory userStory = new UserStory();
        userStory.setUserId(userId);
        userStory.setCreateTime(new Date());
        userStory.setUpdateTime(new Date());
        userStory.setAuthor(user.getNickname());
        userStory.setTitle(title);
        userStory.setContent(content);
        userStory.setCoverUrl(coverUrl);
        userStory.setVisibility(visibility);
        UserStory result = userStoryService.saveUserStory(userStory);
        if (result == null) {
            responseData.jsonFill(2, "发布失败", null);
            return responseData;
        }
        MsgVo msgVo = new MsgVo();
        msgVo.setUserId(userId);
        msgVo.setHeadImgUrl(user.getHeadImgUrl());
        msgVo.setUserName(user.getNickname());
        Feed feed = new Feed();
        feed.setCreateTime(new Date());
        feed.setUpdateTime(new Date());
        feed.setFid(userId);
        feed.setContent(new Gson().toJson(msgVo));
        feed.setMid(result.getId());
        feed.setType(MessageType.NEW_FRIEND_STORY);
        TagRelation tagRelation = new TagRelation();
        tagRelation.setStoryId(result.getId());
        feed.setTid(userId);
        messageFeedService.feed(feed);
        if ((userStory.getVisibility() & Visibility.FOLLOWER) > 0){
            messageFeedService.feedFollowers(feed, userId, false);
        }
        //添加原创故事的标签
        tagRelation.setTagId(100124);
        tagRelation.setCreateTime(new Date());
        tagRelation.setUpdateTime(new Date());
        tagRelationService.saveTagRelation(tagRelation);

        //添加第一次添加原创故事徽章
        Badge badge =badgeCheckService.judgeAddFirstAddOriginalStoryBadge(userId);
        List<Badge> list=new ArrayList<>();
        list.add(badge);
        responseData.setBadgeList(list);

        responseData.jsonFill(1, null, userStory);
        return responseData;
    }

    @ApiOperation(value = "更新原创故事信息", notes = "Auth")
    @RequestMapping(value = "/updateUserStory", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> updateUserStory(
            @ApiParam("故事ID") @RequestParam(value = "id") Integer id,
            @ApiParam("故事标题") @RequestParam(value = "title", required = false) String title,
            @ApiParam("内容") @RequestParam(value = "content", required = false) String content,
            @ApiParam("封面内容") @RequestParam(value = "coverUrl", required = false) String coverUrl,
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseData<Boolean> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "用户尚未登录。", false);
            return responseData;
        }

        UserStory userStory = userStoryService.getUserStoryById(id);
        if (userStory == null) {
            responseData.jsonFill(2, "故事不存在。", false);
            return responseData;
        }
        if (userStory.getUserId().compareTo(user.getId()) != 0) {
            responseData.jsonFill(2, "无效的请求。", false);
            response.setStatus(401);
            return responseData;
        }

        userStory.setUpdateTime(new Date());
        userStory.setTitle(title);
        userStory.setContent(content);
        userStory.setCoverUrl(coverUrl);

        boolean result = userStoryService.updateUserStory(userStory);
        responseData.jsonFill(result ? 1 : 2, null, result);
        return responseData;
    }


    @ApiOperation(value = "删除原创故事", notes = "Auth")
    @RequestMapping(value = "/deleteUserStory", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> deleteUserStory(
            @ApiParam("故事ID") @RequestParam("userStoryId") int userStoryId,
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseData<Boolean> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "用户尚未登录。", false);
            return responseData;
        }

        UserStory userStory = userStoryService.getUserStoryById(userStoryId);
        if (userStory == null) {
            responseData.jsonFill(2, "故事不存在。", false);
            return responseData;
        }
        if (!userStory.getUserId().equals(user.getId())) {
            responseData.jsonFill(2, "无效的请求。", false);
            response.setStatus(401);
            return responseData;
        }
        boolean result = userStoryService.deleteUserStory(userStoryId);
        if(result){
            messageFeedService.unfeedFollowers(userStoryId, user.getId(), true);
        }
        responseData.jsonFill(result ? 1 : 2, null, result);
        return responseData;
    }

    @ApiOperation(value = "获得一个原创故事的信息", notes = "")
    @RequestMapping(value = "/getUserStoryById", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<UserStory> getUserStoryById(
            @ApiParam("故事ID") @RequestParam("userStoryId") int userStoryId,
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseData<UserStory> responseData = new ResponseData<>();
        UserStory userStory = userStoryService.getUserStoryById(userStoryId);
        if (userStory == null) {
            responseData.jsonFill(2, "故事不存在。", null);
            return responseData;
        }
        responseData.jsonFill(1, null, userStory);
        return responseData;
    }
    
    @ApiOperation(value = "原创故事的阅读次数加一", notes = "")
    @RequestMapping(value = "/roseUserStoryTellCount", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> roseUserStoryTellCount(
            @ApiParam("故事ID") @RequestParam("id") int id,
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean res=userStoryService.roseUserStoryTellCount(id);
        if (res==false) {
            responseData.jsonFill(2, "修改失败", false);
            return responseData;
        }
        responseData.jsonFill(1, null, true);
        return responseData;
    }

    @ApiOperation(value = "分享原创故事")
    @RequestMapping(value = "/shareUserStory", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<UserStory> shareUserStory(
            @ApiParam("原创故事ID") @RequestParam("userStoryId") int userStoryId){
        ResponseData<UserStory> responseData = new ResponseData<>();
        UserStory userStory = userStoryService.getUserStoryById(userStoryId);
        if (userStory == null) {
            responseData.jsonFill(2, "故事不存在。", null);
            return responseData;
        }
        responseData.jsonFill(1, null, userStory);
        return responseData;
    }

    @ApiOperation(value = "判断是否需要添加第一次分享原创故事徽章", notes = "分享原创故事时调用")
    @RequestMapping(value = "/judgeAddFirstShareOriginalStoryBadge", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Badge> judgeAddFirstShareOriginalStoryBadge(HttpServletRequest request){
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        Badge badge =badgeCheckService.judgeAddFirstShareOriginalStoryBadge(user.getId());
        ResponseData<Badge> responseData=new ResponseData<>();
        responseData.jsonFill(1,null,badge);
        return responseData;
    }
    
}
