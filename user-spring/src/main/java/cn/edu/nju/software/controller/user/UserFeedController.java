package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.dto.MsgVo;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.SystemNotice;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.entity.Feed;
import cn.edu.nju.software.entity.feed.MessageType;
import cn.edu.nju.software.feed.service.MessageFeedService;
import cn.edu.nju.software.service.SystemNoticeService;
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
import redis.clients.jedis.BinaryClient;

import javax.print.attribute.standard.MediaSize;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Api(value = "/feed", description = "和动态有关的接口")
@Controller
@RequestMapping("/user/feed")
public class UserFeedController extends BaseController {
    @Autowired
    MessageFeedService messageFeedService;
    @Autowired
    SystemNoticeService systemNoticeService;

    @ApiOperation(value = "获取某个用户的动态列表", notes = "需要登录")
    @RequestMapping(value = "/getFeedListByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Feed>> getFeedListByPage(
            @ApiParam("page") @RequestParam int page,
            @ApiParam("pageSize") @RequestParam int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Feed>> responseData = new ResponseData();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }

        List<Feed> feedList = messageFeedService.getDisplayFeedsByPage(user.getId(), page, pageSize);
        responseData.jsonFill(1, null, feedList);

        responseData.setCount(messageFeedService.getFeedCountByUserId(user.getId()));
        return responseData;
    }

    @ApiOperation(value = "获取所有系统通知列表")
    @RequestMapping(value = "/getSystemNoticeListByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Feed>> getSystemNoticeListByPage(
            @ApiParam("page") @RequestParam int page,
            @ApiParam("pageSize") @RequestParam int pageSize) {
        //以feed的形式返回给APP
        ResponseData<List<Feed>> responseData = new ResponseData<>();
        List<SystemNotice> systemNoticeList = systemNoticeService.getAllSystemNoticeByPage(page, pageSize);
        System.out.print("*************************************"+systemNoticeList);
        List<Feed> list = new ArrayList<>();
        MsgVo msgVo = new MsgVo();
        //这些数据是写死的，系统通知员的Id是2
        msgVo.setUserId(2);
        msgVo.setUserName("小P和小I");
        //暖音小助手 头像URL
        msgVo.setHeadImgUrl("http://47.93.242.215/source/head/4DYCFUt6eHA7TTvx.jpg");
        Gson gson = new Gson();
        for (SystemNotice notice : systemNoticeList) {
            Feed feed = new Feed();
            feed.setFid(2);
            feed.setType(MessageType.SYSTEM_NOTICE);
            feed.setMid(notice.getId());
            feed.setUpdateTime(notice.getCreateTime());
            msgVo.setData(notice.getContent());
            feed.setContent(gson.toJson(msgVo));
            list.add(feed);
        }
        responseData.jsonFill(1, null, list);
        responseData.setCount(systemNoticeService.getAllSystemNoticeCount());
        return responseData;
    }

    @ApiOperation(value = "模拟返回其他类型的feed类型")
    @RequestMapping(value = "/mockGetFeed", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Feed>> getFeedListByPage() {
        ResponseData<List<Feed>> responseData = new ResponseData();

        List<Feed> list = new ArrayList<>();
        Feed feed = new Feed();
        feed.setType(MessageType.OTHER);
        list.add(feed);
        responseData.jsonFill(1, null, list);
        return responseData;
    }

}
