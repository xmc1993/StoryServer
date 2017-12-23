package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.entity.*;
import cn.edu.nju.software.service.CommentService;
import cn.edu.nju.software.service.user.AppUserService;
import cn.edu.nju.software.util.SensitiveWordsUtil;
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
import java.util.Date;
import java.util.List;

/**
 * Created by zhangsong on 2017/12/11.
 */
@Api("comment")
@Controller
@RequestMapping("/user")
public class UserCommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    AppUserService appUserService;

    @ApiOperation("发布评论")
    @RequestMapping(value = "/publishComment", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> publishComment(@ApiParam("故事周边的Id") @RequestParam(value = "ambitusId") Integer ambitusId,
                                                @ApiParam("评论的内容") @RequestParam(value = "content") String content,
                                                @ApiParam("图片的Urls") @RequestParam(value = "picUrls", required = false) String picUrls,
                                                HttpServletRequest request) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            return responseData;
        }

        boolean res = SensitiveWordsUtil.isContaintSensitiveWord(content, 2);
        Comment comment = new Comment();
        if (res) {
            comment.setState(2);
        } else {
            comment.setState(1);
        }
        comment.setAmbitusId(ambitusId);
        comment.setContent(content);
        comment.setCreateTime(new Date());
        comment.setUserId(user.getId());
        UserBase userBase = appUserService.getUserBaseById(user.getId());
        comment.setUserName(userBase.getNickname());
        comment.setUserHeadImgUrl(userBase.getHeadImgUrl());

        if (picUrls != null)
            comment.setPicUrls(picUrls);

        int success = commentService.saveComment(comment);
        if (success == 1) {
            responseData.jsonFill(1, null, true);
            return responseData;
        }
        responseData.jsonFill(2, "评论失败", false);
        return responseData;
    }

    @ApiOperation("根据故事周边id获取评论信息")
    @RequestMapping(value = "/getCommentsByAmbitusId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Comment>> getCommentsByAmbitusId(@ApiParam("故事周边的Id") @RequestParam(value = "ambitusId") Integer ambitusId,
                                                              @ApiParam("page") @RequestParam int page,
                                                              @ApiParam("pageSize") @RequestParam int pageSize,
                                                              HttpServletRequest request) {
        ResponseData<List<Comment>> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        List<Comment> list = commentService.getCommentsByAmbitusId(ambitusId, page, pageSize).getObj();
        if (user != null) {
            List<Integer> idList = commentService.getAllLikeByUserId(user.getId());
                for (Comment comment : list) {
                    for (Integer commentId : idList) {
                        if (commentId.equals(comment.getId())) {
                            comment.setLike(true);
                            break;
                        }
                    }
                }
            }
        responseData.jsonFill(1, null, list);
        return responseData;
    }

    @ApiOperation("用户评论点赞")
    @RequestMapping(value = "/newLikeToComment", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> newLikeToComment(@ApiParam("评论id") @RequestParam(value = "commentId") Integer commentId,
                                                  HttpServletRequest request) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            return responseData;
        }
        boolean res = commentService.newLike(commentId, user.getId());
        if (res) {
            responseData.jsonFill(1, null, res);
            return responseData;
        }
        responseData.jsonFill(2, "点赞失败", null);
        return responseData;
    }

    @ApiOperation("用户取消点赞")
    @RequestMapping(value = "/deleteLikeToComment", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> deleteLikeToComment(@ApiParam("评论id") @RequestParam(value = "commentId") Integer commentId,
                                                     HttpServletRequest request) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            return responseData;
        }
        boolean res = commentService.deleteLike(commentId, user.getId());
        if (res) {
            responseData.jsonFill(1, null, res);
            return responseData;
        }
        responseData.jsonFill(2, "取消点赞失败", null);
        return responseData;
    }

    @ApiOperation("用户删除自己的评论")
    @RequestMapping(value = "/deleteComment", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> deleteComment(@ApiParam("评论id") @RequestParam(value = "commentId") Integer commentId,
                                                     HttpServletRequest request) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            return responseData;
        }

        boolean res = commentService.deleteCommentByUser(commentId,user.getId());
        if (res) {
            responseData.jsonFill(1, null, res);
            return responseData;
        }
        responseData.jsonFill(2, "删除失败", null);
        return responseData;
    }
}
