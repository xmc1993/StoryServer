package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.Baby;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.BabyService;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Api("baby controller")
@Controller
@RequestMapping("/user")
public class UserBabyController extends BaseController {
    @Autowired
    private BabyService babyService;


    @ApiOperation(value = "获得一个用户的所有Baby", notes = "")
    @RequestMapping(value = "/getBabyListByParentId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Baby>> getBabyListByParentId(
            @ApiParam("用户ID") @RequestParam("userId") int userId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Baby>> responseData = new ResponseData<>();
        List<Baby> babyList = babyService.getBabyListByParentId(userId);
        responseData.jsonFill(1, null, babyList);
        responseData.setCount(babyList.size());
        return responseData;
    }


    @ApiOperation(value = "添加Baby", notes = "Auth")
    @RequestMapping(value = "/addBaby", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Baby> addBaby(
            @ApiParam("宝宝名字") @RequestParam("babyName") String babyName,
            @ApiParam("年龄") @RequestParam("age") int age,
            @ApiParam("性别") @RequestParam("sex") String sex,
            @ApiParam("生日(采用yyyy-MM-dd HH:mm:ss的格式)") @RequestParam("birthday") String birthday,
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseData<Baby> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "用户尚未登录。", null);
            return responseData;
        }
        Baby baby = new Baby();
        baby.setParentId(user.getId());
        baby.setCreateTime(new Date());
        baby.setUpdateTime(new Date());
        baby.setAge(age);
        baby.setSex(sex);
        baby.setBabyName(babyName);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        baby.setBirthday(dateFormat.parse(birthday));
        Baby result = babyService.saveBaby(baby);
        if (result == null) {
            responseData.jsonFill(2, "添加失败", null);
            return responseData;
        }
        responseData.jsonFill(1, null, baby);
        return responseData;
    }

    @ApiOperation(value = "更新Baby信息", notes = "Auth")
    @RequestMapping(value = "/updateBaby", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> updateBaby(
            @ApiParam("宝宝ID") @RequestParam("babyId") int babyId,
            @ApiParam("宝宝名字") @RequestParam("babyName") String babyName,
            @ApiParam("年龄") @RequestParam("age") int age,
            @ApiParam("性别") @RequestParam("sex") String sex,
            @ApiParam("生日(采用yyyy-MM-dd HH:mm:ss的格式)") @RequestParam("birthday") String birthday,
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseData<Boolean> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "用户尚未登录。", false);
            return responseData;
        }

        Baby baby = babyService.getBabyById(babyId);
        if (baby == null) {
            responseData.jsonFill(2, "宝宝不存在。", false);
            return responseData;
        }
        if (baby.getParentId().compareTo(user.getId()) != 0) {
            responseData.jsonFill(2, "无效的请求。", false);
            response.setStatus(401);
            return responseData;
        }
        baby.setUpdateTime(new Date());
        baby.setAge(age);
        baby.setSex(sex);
        baby.setBabyName(babyName);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        baby.setBirthday(dateFormat.parse(birthday));
        boolean result = babyService.updateBaby(baby);
        responseData.jsonFill(result ? 1 : 2, null, result);
        return responseData;
    }


    @ApiOperation(value = "删除Baby", notes = "Auth")
    @RequestMapping(value = "/deleteBaby", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> deleteBaby(
            @ApiParam("宝宝ID") @RequestParam("babyId") int babyId,
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseData<Boolean> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "用户尚未登录。", false);
            return responseData;
        }

        Baby baby = babyService.getBabyById(babyId);
        if (baby == null) {
            responseData.jsonFill(2, "宝宝不存在。", false);
            return responseData;
        }
        if (baby.getParentId() != user.getId()) {
            responseData.jsonFill(2, "无效的请求。", false);
            response.setStatus(401);
            return responseData;
        }
        boolean result = babyService.deleteBaby(babyId);
        responseData.jsonFill(result ? 1 : 2, null, result);
        return responseData;
    }

    @ApiOperation(value = "获得一个宝宝的信息", notes = "")
    @RequestMapping(value = "/getBabyById", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Baby> getBabyById(
            @ApiParam("宝宝ID") @RequestParam("babyId") int babyId,
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseData<Baby> responseData = new ResponseData<>();
        Baby baby = babyService.getBabyById(babyId);
        if (baby == null) {
            responseData.jsonFill(2, "宝宝不存在。", null);
            return responseData;
        }
        responseData.jsonFill(1, null, baby);
        return responseData;
    }
}
