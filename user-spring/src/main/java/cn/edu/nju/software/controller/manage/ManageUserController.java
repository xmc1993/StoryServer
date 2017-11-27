package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.annotation.RequiredPermissions;
import cn.edu.nju.software.entity.Baby;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.BabyService;
import cn.edu.nju.software.service.user.UserMessageService;
import cn.edu.nju.software.util.Util;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author zs
 * @version 创建时间：2017年9月2日 下午8:06:10
 */

@Api(value = "User", description = "用户管理接口")
@Controller
@RequestMapping("/manage/user")
public class ManageUserController {
    @Autowired
    UserMessageService userMessageService;
    @Autowired
    BabyService babyService;

    // 删除用户
    @RequiredPermissions({2, 19})
    @RequestMapping(value = "/deleteUserById/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ApiOperation(value = "根据用户ID删除用户", notes = "")
    public ResponseData<Boolean> deleteUserById(@ApiParam("id") @PathVariable int id, HttpServletRequest request,
                                                HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData();
        boolean res = userMessageService.deleteUserById(id);
        responseData.jsonFill(res ? 1 : 2, null, res);
        if (!res) {
            throw new RuntimeException("删除失败");
        }
        return responseData;
    }

    // 根据用户姓名模糊查找
    @RequiredPermissions({4, 19})
    @RequestMapping(value = "/getUserByFuzzyQuery", method = {RequestMethod.GET})
    @ApiOperation(value = "根据用户姓名模糊查找")
    @ResponseBody
    public ResponseData<List<User>> getUserByFuzzyQuery(
            @ApiParam("PAGE") @RequestParam int page,
            @ApiParam("SIZE") @RequestParam int pageSize,
            @ApiParam("query") @RequestParam String query,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<User>> responseData = new ResponseData<>();
        //如果传过来的搜索条件为空的话，就分页返回所有的用户
        if (query == null) {
            List<User> userList = userMessageService.getUserListByPage(page, pageSize);
            responseData.jsonFill(1, null, userList);
            responseData.setCount(userMessageService.getUserCount());
            return responseData;
        }
        List<User> userList = userMessageService.getUserByFuzzyQuery(query);
        if (userList == null || userList.isEmpty()) {
            responseData.jsonFill(2, "用户不存在", null);
            return responseData;
        }
        responseData.jsonFill(1, null, userList);
        return responseData;
    }

    // 根据用户ID查找用户
    @RequiredPermissions({4, 19})
    @ApiOperation(value = "根据用户ID查找用户", notes = "")
    @RequestMapping(value = "/getUserById", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<User> getUserById(@ApiParam("ID") @RequestParam int id, HttpServletRequest request,
                                          HttpServletResponse response) {
        ResponseData<User> responseData = new ResponseData<>();
        User user = userMessageService.getUserById(id);
        if (user == null) {
            responseData.jsonFill(2, "用户不存在", null);
            return responseData;
        }
        responseData.jsonFill(1, null, user);
        return responseData;
    }

    // 添加用户
    @RequiredPermissions({1, 19})
    @ApiOperation(value = "添加用户", notes = "")
    @RequestMapping(value = "/saveUser", method = {RequestMethod.POST})
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User saveUser(@ApiParam("用户名") @RequestParam(value = "nickname") String nickname,
                         @ApiParam("密码") @RequestParam(value = "password") String password,
                         @ApiParam("性别") @RequestParam(value = "sex", required = false) String sex,
                         @ApiParam("城市") @RequestParam(value = "city", required = false) String city,
                         @ApiParam("公司") @RequestParam(value = "company", required = false) String company,
                         @ApiParam("邮箱") @RequestParam(value = "email", required = false) String email,
                         @ApiParam("手机号码") @RequestParam(value = "mobile", required = false) String mobile,
                         @ApiParam("用户头像url") @RequestParam(value = "headImgUrl", required = false) String headImgUrl,
                         HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setNickname(nickname);
        user.setPassword(Util.getMd5(password));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setAccessToken(Util.getToken());
        if (city != null)
            user.setCity(city);
        if (sex != null)
            user.setSex(sex);
        if (mobile != null)
            user.setMobile(mobile);
        if (company != null)
            user.setCompany(company);
        if (headImgUrl != null)
            user.setHeadImgUrl(headImgUrl);
        if (email != null)
            user.setEmail(email);
        user = userMessageService.saveUser(user);
        return user;
    }

    // 更新用户信息
    @RequiredPermissions({3, 19})
    @ApiOperation(value = "更新用户信息", notes = "")
    @RequestMapping(value = "/updateUser", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> updateUser(
            @ApiParam("用户id") @RequestParam(value = "id") Integer id,
            @ApiParam("用户名") @RequestParam(value = "nickname", required = false) String nickname,
            @ApiParam("密码") @RequestParam(value = "password", required = false) String password,
            @ApiParam("性别") @RequestParam(value = "sex", required = false) String sex,
            @ApiParam("城市") @RequestParam(value = "city", required = false) String city,
            @ApiParam("公司") @RequestParam(value = "company", required = false) String company,
            @ApiParam("邮箱") @RequestParam(value = "email", required = false) String email,
            @ApiParam("手机号码") @RequestParam(value = "mobile", required = false) String mobile,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        User user = userMessageService.getUserById(id);
        if (user == null) {
            responseData.jsonFill(2, "用户不存在", false);
            return responseData;
        }
        if (nickname != null) user.setNickname(nickname);
        if (password != null) user.setPassword(Util.getMd5(password));
        user.setUpdateTime(new Date());
        if (city != null)
            user.setCity(city);
        if (sex != null)
            user.setSex(sex);
        if (mobile != null)
            user.setMobile(mobile);
        if (company != null)
            user.setCompany(company);
        if (email != null)
            user.setEmail(email);
        boolean res = userMessageService.updateUser(user);
        responseData.jsonFill(res ? 1 : 2, null, res);
        return responseData;
    }

    // 根据页数获取所有用户的列表
    @RequiredPermissions({4, 19})
    @ApiOperation(value = "分页获得用户", notes = "")
    @RequestMapping(value = "/getUserListByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<User>> getUserListByPage(@ApiParam("PAGE") @RequestParam int page,
                                                      @ApiParam("SIZE") @RequestParam int pageSize, HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<User>> responseData = new ResponseData<>();
        List<User> userList = userMessageService.getUserListByPage(page, pageSize);
        responseData.jsonFill(1, null, userList);
        responseData.setCount(userMessageService.getUserCount());
        return responseData;
    }


    // 批量添加用户（系统创建的僵尸粉）
    @RequiredPermissions({1, 19})
    @ApiOperation(value = "批量创建僵尸粉", notes = "")
    @RequestMapping(value = "/saveDummy", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> saveDummy(@ApiParam("需要创建的僵尸粉数") @RequestParam int count,
                                           @ApiParam("用户名字取自这段话") @RequestParam String userNameField,
                                           @ApiParam("宝宝名字取自这段话") @RequestParam String babyNameField) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        User user = new User();
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

        Baby baby = new Baby();
        baby.setCreateTime(new Date());
        baby.setUpdateTime(new Date());

        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            //僵尸粉名字
            StringBuilder userName = new StringBuilder();
            //名字的字数个数(1-6)
            int nameCount = rand.nextInt(6) + 1;
            //随机追加字
            for (int j = 0; j < nameCount; j++) {
                userName.append(userNameField.charAt(rand.nextInt(userNameField.length())));
            }
            user.setNickname(userName.toString());
            //随机一个性别
            user.setSex(nameCount % 2 == 0 ? "男" : "女");
            //用户头像url 用的是一个第三方的图库
            StringBuilder headImgUrl = new StringBuilder();
            headImgUrl.append("https://picsum.photos/80?image=").append(rand.nextInt(400) + 100);
            user.setHeadImgUrl(headImgUrl.toString());
            //僵尸粉的valid设置为2
            user.setValid(2);
            user = userMessageService.saveUser(user);

            //为该用户添加宝宝
            baby.setParentId(user.getId());
            //给用户id赋空,否则会起冲突。
            user.setId(null);
            baby.setAge(rand.nextInt(4));
            Integer index = rand.nextInt(babyNameField.length());
            char babyName1 = babyNameField.charAt(index);
            baby.setBabyName(new StringBuilder().append(babyName1).append(babyName1).toString());
            baby.setSex(index % 2 == 0 ? "男" : "女");
            baby.setSelected(0);
            baby = babyService.saveBaby(baby);

            //宝宝id需要赋空，否则冲突
            baby.setId(null);
        }
        responseData.jsonFill(1, null, true);
        return responseData;
    }

}
