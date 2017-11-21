package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.entity.OpinionFeedback;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.OpinionFeedbackservice;
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
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by zhangsong on 2017/11/20.
 */
@Api(value = "User", description = "意见反馈")
@Controller
@RequestMapping("/user")
public class UserOpinionController {

    @Autowired
    OpinionFeedbackservice opinionFeedbackservice;

    @ApiOperation(value = "添加意见反馈")
    @RequestMapping(value = "/addOpinionFeedback", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> addOpinionFeedback(
            @ApiParam("机型") @RequestParam(value = "model", required = false) String model,
            @ApiParam("应用版本号") @RequestParam(value = "appVersion") String appVersion,
            @ApiParam("安卓版本号") @RequestParam(value = "androidVersion") String androidVersion,
            @ApiParam("网络环境") @RequestParam(value = "networkEnvironment", required = false) String networkEnvironment,
            @ApiParam("联系方式") @RequestParam(value = "connection", required = false) String connection,
            @ApiParam("描述") @RequestParam(value = "description", required = false) String description,
            @ApiParam("图片Url") @RequestParam(value = "picUrls", required = false) String picUrls,
            @ApiParam("意见类型") @RequestParam(value = "opinionType", required = false) String opinionType,
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseData<Boolean> responseData = new ResponseData<>();
        //看用户存不存在，不存在的话赋值为一
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            user = new User();
            user.setId(-1);
        }

        OpinionFeedback opinionFeedback = new OpinionFeedback();
        if (model != null)
            opinionFeedback.setModel(model);
        opinionFeedback.setappVersion(appVersion);
        opinionFeedback.setandroidVersion(androidVersion);
        if (networkEnvironment != null)
            opinionFeedback.setnetworkEnvironment(networkEnvironment);
        if (connection != null)
            opinionFeedback.setConnection(connection);
        if (description != null)
            opinionFeedback.setDescription(description);
        if (picUrls != null)
            opinionFeedback.setpicUrls(picUrls);
        if (opinionType != null)
            opinionFeedback.setopinionType(opinionType);
        opinionFeedback.setcreateTime(new Date());
        opinionFeedback.setuserId(user.getId());
        int res = opinionFeedbackservice.saveOpinion(opinionFeedback);
        if (res == 1) {
            responseData.jsonFill(1, null, true);
            return responseData;
        }
        responseData.jsonFill(2, "提交失败", false);
        return responseData;
    }
}
