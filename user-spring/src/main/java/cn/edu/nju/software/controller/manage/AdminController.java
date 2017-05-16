package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.Admin;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.AdminService;
import cn.edu.nju.software.util.JedisUtil;
import cn.edu.nju.software.util.ObjectAndByte;
import cn.edu.nju.software.util.Util;
import cn.edu.nju.software.vo.response.LoginResponseVo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by xmc1993 on 2017/5/15.
 */

@Api(value = "/story", description = "和故事有关的接口")
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "登录", notes = "")
    @RequestMapping(value = "/manage/login", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<LoginResponseVo> login(
            @ApiParam("用户名") @RequestParam("username") String username,
            @ApiParam("密码(用md5加密)") @RequestParam("password") String password,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<LoginResponseVo> responseData = new ResponseData<>();
        Admin admin = adminService.getAdminByUsername(username);
        if (admin == null) {
            responseData.jsonFill(2, "用户不存在", null);
            return responseData;
        }
        //todo 更严格的安全校验
        if (!admin.getPassword().equals(password)) {
            responseData.jsonFill(2, "密码错误", null);
            return responseData;
        }
        admin.setAccessToken(Util.getToken());
        long currentTime = System.currentTimeMillis();
        currentTime += 1000 * 60 * 60 * 6;//设置为6小时后失效
        admin.setExpireTime(new Date(currentTime));
        boolean res = adminService.updateAccessToken(admin);
        if (!res) {
            responseData.jsonFill(2, "登录失败，服务器错误。", null);
            return responseData;
        }
        LoginResponseVo loginResponseVo = new LoginResponseVo();
        loginResponseVo.setId(admin.getId());
        loginResponseVo.setAccessToken(admin.getAccessToken());
        responseData.jsonFill(1, null, loginResponseVo);

        //在缓存中存入登录信息
        Jedis jedis = JedisUtil.getJedis();
        jedis.set(admin.getAccessToken().getBytes(), ObjectAndByte.toByteArray(admin));
        jedis.expire(admin.getAccessToken().getBytes(), 60 * 60 * 6);//缓存用户信息6小时
        jedis.close();

        return responseData;
    }

    @ApiOperation(value = "登出", notes = "")
    @RequestMapping(value = "/manage/logout", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<LoginResponseVo> logout(
            HttpServletRequest request, HttpServletResponse response) {

        return null;
    }
}
