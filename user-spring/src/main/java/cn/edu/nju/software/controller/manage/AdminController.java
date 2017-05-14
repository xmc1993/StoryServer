package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.vo.response.LoginResponseVo;
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

/**
 * Created by xmc1993 on 2017/5/15.
 */

@Api(value = "/story", description = "和故事有关的接口")
@Controller
public class AdminController {

    @ApiOperation(value = "登录", notes = "")
    @RequestMapping(value = "/manage/login", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<LoginResponseVo> login(
            @ApiParam("用户名") @RequestParam("name") String name,
            @ApiParam("密码") @RequestParam("password") String password,
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @ApiOperation(value = "登出", notes = "")
    @RequestMapping(value = "/manage/logout", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<LoginResponseVo> logout(
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
