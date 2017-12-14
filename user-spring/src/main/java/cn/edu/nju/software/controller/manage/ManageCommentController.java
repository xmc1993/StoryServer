package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.Comment;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.entity.UserBase;
import cn.edu.nju.software.service.CommentService;
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
@Api(value = "Admin", description = "管理接口")
@Controller()
@RequestMapping("/manage")
public class ManageCommentController {
    @Autowired
    CommentService commentService;


    @ApiOperation("根据故事周边id获取评论")
    @RequestMapping(value = "/getCommentsByAmbitusId", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<List<Comment>> getCommentsByAmbitusId(@ApiParam("故事周边的Id") @RequestParam(value = "ambitusId") Integer ambitusId,
                                                @ApiParam("page") @RequestParam(value = "page") Integer page,
                                                @ApiParam("pageSize") @RequestParam(value = "pageSize") Integer pageSize) {
        ResponseData<List<Comment>> responseData=commentService.getCommentsWithSensitiveByAmbitusId(ambitusId,page,pageSize);
        return responseData;
    }

    @ApiOperation("评论加精")
    @RequestMapping(value = "/commentAddCream", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> commentAddCream(@ApiParam("评论id") @RequestParam(value = "commentId") Integer commentId) {
        ResponseData<Boolean> responseData=new ResponseData<>();
        boolean res=commentService.addCream(commentId);
        responseData.jsonFill(1,null,res);
        return responseData;
    }

    @ApiOperation("评论取消加精")
    @RequestMapping(value = "/cancelCream", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> cancelCream(@ApiParam("评论id") @RequestParam(value = "commentId") Integer commentId) {
        ResponseData<Boolean> responseData=new ResponseData<>();
        boolean res=commentService.deleteCream(commentId);
        responseData.jsonFill(1,null,res);
        return responseData;
    }

    @ApiOperation("非法评论放出小黑屋")
    @RequestMapping(value = "/releaseComment", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> releaseComment(@ApiParam("评论id") @RequestParam(value = "commentId") Integer commentId) {
        ResponseData<Boolean> responseData=new ResponseData<>();
        boolean res=commentService.releaseComment(commentId);
        responseData.jsonFill(1,null,res);
        return responseData;
    }

    @ApiOperation("删除评论")
    @RequestMapping(value = "/deleteComment", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> deleteComment(@ApiParam("评论id") @RequestParam(value = "commentId") Integer commentId,
                                                 HttpServletRequest request) {
        ResponseData<Boolean> responseData=new ResponseData<>();
        Integer res=commentService.deleteComment(commentId);
        if(res==1){
            responseData.jsonFill(1,null,true);
        }else {
            responseData.jsonFill(2,"删除失败",false);
        }
        return responseData;
    }
}
