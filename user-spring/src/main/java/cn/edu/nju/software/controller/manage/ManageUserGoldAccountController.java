package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.entity.UserGoldAccount;
import cn.edu.nju.software.service.user.UserGoldAccountService;
import cn.edu.nju.software.util.TokenConfig;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author zj
 */
@Api(value = "admin", description = "管理用户金币账户")
@Controller
@RequestMapping("/manage/userGoldAccount")
public class ManageUserGoldAccountController {
    private static final Logger logger = LoggerFactory.getLogger(ManageUserGoldAccountController.class);

    @Autowired
    private UserGoldAccountService userGoldAccountService;

    @ApiOperation(value = "更新用户金币账户", notes = "")
    @RequestMapping(value = "updateUserGoldAccount", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> updateUserGoldAccount(
            @ApiParam("") @RequestParam("id") Integer id,

            @ApiParam("") @RequestParam(value = "userId", required = false) Integer userId,
            @ApiParam("金币数量") @RequestParam(value = "amount", required = false) Integer amount,
            @ApiParam("") @RequestParam(value = "createTime", required = false) Date createTime,
            @ApiParam("") @RequestParam(value = "updateTime", required = false) Date updateTime) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        UserGoldAccount userGoldAccount = new UserGoldAccount();

        userGoldAccount.setId(id);

        userGoldAccount.setUserId(userId);
        userGoldAccount.setAmount(amount);
        userGoldAccount.setCreateTime(createTime);
        userGoldAccount.setUpdateTime(updateTime);
        int res = userGoldAccountService.updateUserGoldAccount(userGoldAccount);
        if (res == 0) {
            responseData.jsonFill(2, "更新失败", false);
            return responseData;
        }
        responseData.jsonFill(1, null, true);
        return responseData;
    }


    //管理端用id查询，用户段用userId查询
    @ApiOperation(value = "通过ID获取用户金币账户信息", notes = "")
    @RequestMapping(value = "getUserGoldAccountById", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<UserGoldAccount> getUserGoldAccountById(@ApiParam("") @RequestParam("id") Integer id) {
        ResponseData<UserGoldAccount> responseData = new ResponseData<>();
        UserGoldAccount userGoldAccount = userGoldAccountService.getUserGoldAccountById(id);
        if (userGoldAccount == null) {
            responseData.jsonFill(2, "无效的id", null);
            return responseData;
        }
        responseData.jsonFill(1, null, userGoldAccount);
        return responseData;
    }

    @ApiOperation(value = "删除用户金币账户信息", notes = "")
    @RequestMapping(value = "deleteUserGoldAccount/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    public ResponseData<Boolean> deleteUserGoldAccount(@ApiParam("ID") @PathVariable Integer id) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        int res = userGoldAccountService.deleteUserGoldAccount(id);
        if (res == 0) {
            responseData.jsonFill(2, "删除失败", false);
            return responseData;
        }
        responseData.jsonFill(1, "删除成功", true);
        return responseData;
    }
}
