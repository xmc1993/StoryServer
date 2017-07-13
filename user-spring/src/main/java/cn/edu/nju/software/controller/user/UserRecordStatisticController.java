package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.RecordStatisticService;
import cn.edu.nju.software.util.TokenConfig;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Api("recordStatistic controller")
@Controller
@RequestMapping("/user")
public class UserRecordStatisticController extends BaseController {
    @Autowired
    private RecordStatisticService recordStatisticService;


    @ApiOperation(value = "用户每次录制时调用", notes = "")
    @RequestMapping(value = "/newRecord", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<String> newRecord(
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseData<String> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        recordStatisticService.saveRecord(user.getId());
        responseData.jsonFill(1, null, "success");
        return responseData;
    }

    @ApiOperation(value = "获得用户当前连续录制天数", notes = "")
    @RequestMapping(value = "/getCurrentCount", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Integer> getCurrentCount(
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseData<Integer> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        int curCount = recordStatisticService.getCurCount(user.getId());
        responseData.jsonFill(1, null, curCount);
        return responseData;
    }

    @ApiOperation(value = "获得用户历史最大连续录制天数", notes = "")
    @RequestMapping(value = "/getHistoryMaxCount", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Integer> getHistoryMaxCount(
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseData<Integer> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        int curCount = recordStatisticService.getHistoryMaxCount(user.getId());
        responseData.jsonFill(1, null, curCount);
        return responseData;
    }

}
