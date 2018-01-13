package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.user.ContinuousLoginPromptService;
import cn.edu.nju.software.service.user.LoginStatusStatisticsService;
import cn.edu.nju.software.util.TokenConfig;
import cn.edu.nju.software.vo.ContinuousLoginPromptVo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zj
 */
@Api("user controller")
@Controller
@RequestMapping("/user/continuousLoginPrompt")
public class UserContinuousLoginPromptController {
    private static final Logger logger = LoggerFactory.getLogger(UserContinuousLoginPromptController.class);
    @Autowired
    private ContinuousLoginPromptService continuousLoginPromptService;
    @Autowired
    private LoginStatusStatisticsService loginStatusStatisticsService;

    @ApiOperation(value = "获取连续登录提示", notes = "当用户没登录时，访问不了这个接口，也不需要访问这个接口")
    @RequestMapping(value = "/selectPrompt", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<ContinuousLoginPromptVo> selectPrompt(HttpServletRequest request) {

        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        Integer userId = user.getId();

        String prompt = continuousLoginPromptService.selectPromptByTime();

        ContinuousLoginPromptVo continuousLoginPromptVo = new ContinuousLoginPromptVo();
        continuousLoginPromptVo.setPrompt(prompt);

        Integer continuousLoginDays = loginStatusStatisticsService.getContinuousLoginDays(userId);
        continuousLoginPromptVo.setContinuousLoginDays(continuousLoginDays);

        ResponseData<ContinuousLoginPromptVo> responseData=new ResponseData<>();
        responseData.jsonFill(1,null,continuousLoginPromptVo);
        return responseData;
    }


}
