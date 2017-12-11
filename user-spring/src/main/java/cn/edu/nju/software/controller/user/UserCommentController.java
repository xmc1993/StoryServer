package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.entity.Comment;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.CommentService;
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

/**
 * Created by zhangsong on 2017/12/11.
 */
@Api("comment")
@Controller
@RequestMapping("/user")
public class UserCommentController {
    @Autowired
    CommentService commentService;

/*    @ApiOperation("发布评论")
    @RequestMapping(value = "/publishComment", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> publishComment(@ApiParam("故事周边的Id") @RequestParam(value = "ambitusId") Integer ambitusId,
                                                @ApiParam("评论的内容") @RequestParam(value = "content") String content,
                                                @ApiParam("图片的Urls") @RequestParam(value = "picUrls",required = false) String picUrls,
                                                HttpServletRequest request) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            return responseData;
        }
        Comment comment=new Comment();
        comment.setAmbitusId(ambitusId);
        comment.setContent(content);
        comment.setCreateTime(new Date());
        comment.setUserId(user.getId());
        List<Badge> badgeList = badgeService.getBadgeOfUser(userId);
        responseData.jsonFill(1,null,badgeList);
        responseData.setCount(badgeList.size());
        return  responseData;
    }*/
}
