package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.service.StoryUserLogService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Kt on 2017/6/27.
 */
@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageLogController {
    @Autowired
    private StoryUserLogService storyUserLogService;

}
