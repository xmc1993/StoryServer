package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.entity.FollowRelation;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.entity.UserBase;
import cn.edu.nju.software.service.FollowService;
import cn.edu.nju.software.service.user.AppUserService;
import cn.edu.nju.software.util.TokenConfig;
import cn.edu.nju.software.vo.UserBaseFollowVo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Api("follow controller")
@Controller
@RequestMapping("/user")
public class UserFollowController {

    @Autowired
    private FollowService followService;
    @Autowired
    private AppUserService appUserService;

    @ApiOperation(value = "关注某人", notes = "需登录")
    @RequestMapping(value = "/follow", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> follow(
            @ApiParam("被关注者ID") @RequestParam("followeeId") int followeeId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "login fail", null);
            response.setStatus(401);
            return responseData;
        }
        if (appUserService.getUserByMobileOrId(String.valueOf(followeeId)) == null) {
            responseData.jsonFill(2, "该用户不存在", null);
            response.setStatus(404);
            return responseData;
        }
        FollowRelation followRelation = new FollowRelation();
        followRelation.setFolloweeId(followeeId);
        followRelation.setFollowerId(user.getId());
        followRelation.setCreateTime(new Date());
        followRelation.setUpdateTime(new Date());
        boolean res = followService.saveFollowRelation(followRelation);

        responseData.jsonFill(res ? 1 : 2, null, res);
        return responseData;
    }

    @ApiOperation(value = "取消关注某人", notes = "需登录")
    @RequestMapping(value = "/unfollow", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> unfollow(
            @ApiParam("需要取消关注者ID") @RequestParam("followeeId") int followeeId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "login fail", null);
            response.setStatus(401);
            return responseData;
        }
        if (appUserService.getUserByMobileOrId(String.valueOf(followeeId)) == null) {
            responseData.jsonFill(2, "该用户不存在", null);
            response.setStatus(404);
            return responseData;
        }

        boolean res = followService.deleteFollowRelation(user.getId(), followeeId);
        responseData.jsonFill(res ? 1 : 2, null, res);
        return responseData;
    }

    @ApiOperation(value = "得到某个用户的粉丝列表", notes = "")
    @RequestMapping(value = "/getFollowerListByUserId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<UserBaseFollowVo>> getFollowerListByUserId(
            @ApiParam("用户ID") @RequestParam("userId") Integer userId,
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<UserBaseFollowVo>> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "用户尚未登录。", null);
            return responseData;
        }
        if (appUserService.getUserByMobileOrId(String.valueOf(userId)) == null) {
            responseData.jsonFill(2, "该用户不存在", null);
            response.setStatus(404);
            return responseData;
        }
        List<Integer> idList = followService.getUserFollowerList(userId, offset, limit);
        List<UserBase> userBaseList = appUserService.getUserBaseListByIdList(idList);
        ArrayList<UserBaseFollowVo> result = new ArrayList<>();
        for (UserBase userBase : userBaseList) {
            UserBaseFollowVo userBaseFollowVo = new UserBaseFollowVo();
            BeanUtils.copyProperties(userBase, userBaseFollowVo);
            userBaseFollowVo.setStatus(followService.getStatusBetween(user.getId(), userBase.getId()));
            result.add(userBaseFollowVo);
        }
        responseData.jsonFill(1, null, result);
        responseData.setCount(followService.getUserFollowerCountByUserId(userId));
        return responseData;
    }

    @ApiOperation(value = "得到某个用户的关注列表", notes = "需登录")
    @RequestMapping(value = "/getFolloweeListByUserId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<UserBaseFollowVo>> getFolloweeListByUserId(
            @ApiParam("用户ID") @RequestParam("userId") int userId,
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<UserBaseFollowVo>> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "用户尚未登录。", null);
            return responseData;
        }
        if (appUserService.getUserByMobileOrId(String.valueOf(userId)) == null) {
            responseData.jsonFill(2, "该用户不存在", null);
            response.setStatus(404);
            return responseData;
        }
        List<Integer> idList = followService.getUserFolloweeList(userId, offset, limit);
        List<UserBase> userBaseList = appUserService.getUserBaseListByIdList(idList);
        ArrayList<UserBaseFollowVo> result = new ArrayList<>();
        for (UserBase userBase : userBaseList) {
            UserBaseFollowVo userBaseFollowVo = new UserBaseFollowVo();
            BeanUtils.copyProperties(userBase, userBaseFollowVo);
            userBaseFollowVo.setStatus(followService.getStatusBetween(user.getId(), userBase.getId()));
            result.add(userBaseFollowVo);
        }
        responseData.jsonFill(1, null, result);
        responseData.setCount(followService.getUserFolloweeCountByUserId(userId));
        return responseData;
    }

    @ApiOperation(value = "关注关系", notes = "需登录(0无关系 1单方面关注 2单方面被关注 3相互关注)")
    @RequestMapping(value = "/getFollowingStatus", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Integer> getFollowingStatus(
            @ApiParam("用户ID") @RequestParam("userId") int userId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Integer> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "用户尚未登录。", null);
            return responseData;
        }
        int status = followService.getStatusBetween(user.getId(), userId);
        responseData.jsonFill(1, null, status);
        return responseData;
    }

}
