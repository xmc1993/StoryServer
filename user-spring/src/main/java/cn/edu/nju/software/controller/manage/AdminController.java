package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.Admin;
import cn.edu.nju.software.service.AdminService;
import cn.edu.nju.software.util.*;
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

@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "登录", notes = "")
    @RequestMapping(value = "/auth", method = {RequestMethod.POST})
    @ResponseBody
    public String login(
            @ApiParam("用户名") @RequestParam("username") String username,
            @ApiParam("密码(用md5加密)") @RequestParam("password") String password,
            HttpServletRequest request, HttpServletResponse response) {
        Admin admin = adminService.getAdminByUsername(username);
        if (admin == null) {
            throw new RuntimeException("用户不存在");
        }
        //todo 更严格的安全校验
        if (!admin.getPassword().equals(password)) {
            throw new RuntimeException("密码错误");
        }
        admin.setAccessToken(Util.getToken());
        long currentTime = System.currentTimeMillis();
        currentTime += 1000 * 60 * 60 * 6;//设置为6小时后失效
        admin.setExpireTime(new Date(currentTime));
        boolean res = adminService.updateAccessToken(admin);
        if (!res) {
            throw new RuntimeException("登录失败，服务器错误。");


        }

        //在缓存中存入登录信息
        Jedis jedis = JedisUtil.getJedis();
        jedis.set(admin.getAccessToken().getBytes(), ObjectAndByte.toByteArray(admin));
        jedis.expire(admin.getAccessToken().getBytes(), 60 * 60 * 6);//缓存用户信息6小时
        jedis.close();

        return admin.getAccessToken();
    }

    @ApiOperation(value = "登出", notes = "")
    @RequestMapping(value = "/auth", method = {RequestMethod.DELETE})
    @ResponseBody
    public void logout(
            HttpServletRequest request, HttpServletResponse response) {
        String accessToken = request.getHeader(TokenConfig.DEFAULT_ACCESS_TOKEN_HEADER_NAME);
        if (!StringUtil.isEmpty(accessToken)){
            //注销管理员的session信息
            Jedis jedis = JedisUtil.getJedis();
            jedis.del(accessToken.getBytes());
            jedis.close();
        }
    }
}
