package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.user.UserGoldAccountService;
import cn.edu.nju.software.util.TokenConfig;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zj
 */
@Api(value = "User", description = "用户金币数量记录表")
@Controller
@RequestMapping("/user/userGoldAccount")
public class UserGoldAccountController {
    private static final Logger logger = LoggerFactory.getLogger(UserGoldAccountController.class);

    @Autowired
    private UserGoldAccountService userGoldAccountService;

/*    @ApiOperation(value = "新增用户金币账户", notes = "")
    @RequestMapping(value = "addUserGoldAccount", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<UserGoldAccount> addUserGoldAccount(HttpServletRequest request) {
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        ResponseData<UserGoldAccount> responseData = new ResponseData<>();
        UserGoldAccount userGoldAccount = new UserGoldAccount();
        userGoldAccount.setUserId(user.getId());
        userGoldAccount.setCreateTime(new Date());
        userGoldAccount.setUpdateTime(new Date());
        int res = userGoldAccountService.addUserGoldAccount(user.getId());
        if (res == 0) {
            responseData.jsonFill(2, "新增userGoldAccount失败}", null);
            return responseData;
        }
        responseData.jsonFill(1, null, userGoldAccount);
        return responseData;
    }*/

    @ApiOperation(value = "获取用户的金币数量", notes = "")
    @RequestMapping(value = "getGoldAmount", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Integer> getGoldAmount(HttpServletRequest request) {
        ResponseData<Integer> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        Integer goldAmount = userGoldAccountService.getGoldAmountByUserId(user.getId());
        responseData.jsonFill(1, null, goldAmount);
        return responseData;
    }
}
