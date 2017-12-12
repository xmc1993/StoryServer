package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.entity.App;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.AppService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Kt on 2017/6/25.
 */
@Api("user app controller")
@Controller
@RequestMapping("/user")
public class UserAppController {
    @Autowired
    private AppService appService;
    @ApiOperation(value = "获取最新版本信息", notes = "")
    @RequestMapping(value = "/getNewApp", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<App> getNewApp(
            @ApiParam("用户app的ID") @RequestParam(value = "id",required = false) Integer id){
        ResponseData<App> responseData = new ResponseData<>();
        App app = appService.getNewApp(id);
        responseData.jsonFill(1,null,app);
        return responseData;
    }
}
