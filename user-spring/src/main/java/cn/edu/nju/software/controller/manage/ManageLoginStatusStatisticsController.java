package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.service.user.ContinuousLoginPromptService;
import cn.edu.nju.software.service.user.LoginStatusStatisticsService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zj
 */
@Api("manage controller")
@Controller
@RequestMapping("/manage/loginStatusStatistics")
public class ManageLoginStatusStatisticsController {
    private static final Logger logger = LoggerFactory.getLogger(ManageLoginStatusStatisticsController.class);
    @Autowired
    private LoginStatusStatisticsService loginStatusStatisticsService;

/*    @ApiOperation(value = "获取用户登录扎", notes = "")
    @RequestMapping(value = "/addPrompt", method = {RequestMethod.POST})
    @ResponseBody*/
}
