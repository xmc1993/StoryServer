package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.entity.Feed;
import cn.edu.nju.software.entity.feed.MessageType;
import cn.edu.nju.software.feed.service.MessageFeedService;
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
import redis.clients.jedis.BinaryClient;

import javax.print.attribute.standard.MediaSize;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
