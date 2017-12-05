package cn.edu.nju.software.controller.user;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.UsageStatisticService;
import cn.edu.nju.software.util.TokenConfig;

/**
* @author zs
* @version 创建时间：2017年9月12日 上午11:23:37
*/
@Api("usageStatisticService controller")
@Controller
@RequestMapping("/user")
public class UserAppStatisticController extends BaseController{
	@Autowired
    private UsageStatisticService usageStatisticService;

    @ApiOperation(value = "用户每次打开app时调用", notes = "")
    @RequestMapping(value = "/newUsage", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<String> newUsage(
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseData<String> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        usageStatisticService.saveUsage(user.getId());
        responseData.jsonFill(1, null, "success");
        return responseData;
    }

    @ApiOperation(value = "获得用户连续使用天数", notes = "")
    @RequestMapping(value = "/getUseRunningDays", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Integer> getUseRunningDays(
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseData<Integer> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        int curCount = usageStatisticService.getCurCount(user.getId());
        responseData.jsonFill(1, null, curCount);
        return responseData;
    }

    @ApiOperation(value = "获得用户历史最大连续使用app天数", notes = "")
    @RequestMapping(value = "/getUseHistoryMaxRunningDays", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Integer> getUseHistoryMaxRunningDays(
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseData<Integer> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        int curCount = usageStatisticService.getHistoryMaxCount(user.getId());
        responseData.jsonFill(1, null, curCount);
        return responseData;
    }

}
