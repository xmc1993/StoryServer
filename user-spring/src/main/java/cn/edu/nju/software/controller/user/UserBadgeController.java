package cn.edu.nju.software.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import cn.edu.nju.software.entity.Badge;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.BadgeService;
import cn.edu.nju.software.util.UserChecker;


/**
 * 
 * @author liuyu
 *
 */

@Api("用户徽章控制器")
@Controller
@RequestMapping("/user")
public class UserBadgeController {
	@Autowired 
	BadgeService userBadgeService;
	
	@ApiOperation("获取用户的徽章列表")
    @RequestMapping(value = "/getAllBageOfUser",method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Badge>> getBadgeOfUser(
    		HttpServletRequest request, HttpServletResponse response){
		ResponseData<List<Badge>> responseData = new ResponseData<>();
        User user = UserChecker.checkUser(request);
        List<Badge> badgeList = userBadgeService.getBadgeOfUser(user.getId());
        //Integer count = userBadgeService.getCountBageOfUser(user.getId());
        responseData.jsonFill(1, null, badgeList);
        responseData.setCount(badgeList.size());
		return responseData;
	}
}
