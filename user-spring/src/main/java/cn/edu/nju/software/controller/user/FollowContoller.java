package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.vo.UserBaseVo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Api("follow controller")
@Controller
public class FollowContoller {

    @ApiOperation(value = "关注某人", notes = "")
    @RequestMapping(value = "/user/follow", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> follow(
            @ApiParam("用户ID") @RequestParam("userId") String userId,
            @ApiParam("被关注者ID") @RequestParam("followeeId") String followeeId,
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @ApiOperation(value = "取消关注某人", notes = "")
    @RequestMapping(value = "/user/unfollow", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> unfollow(@ApiParam("用户ID") @RequestParam("userId") String userId,
                                          @ApiParam("需要取消关注者ID") @RequestParam("followeeId") String followeeId,
                                          HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @ApiOperation(value = "得到某个用户的粉丝列表", notes = "")
    @RequestMapping(value = "/user/getFollowerListByUserId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<UserBaseVo>> getFollowerListByUserId(@ApiParam("用户ID") @RequestParam("userId") String userId,
                                                                  HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @ApiOperation(value = "得到某个用户的关注列表", notes = "")
    @RequestMapping(value = "/user/getFolloweeListByUserId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<UserBaseVo>> getFolloweeListByUserId(@ApiParam("用户ID") @RequestParam("userId") String userId,
                                                                  HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
