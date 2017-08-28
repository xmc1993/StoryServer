package cn.edu.nju.software.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.software.service.UserBadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import cn.edu.nju.software.entity.Badge;
import cn.edu.nju.software.entity.BadgeType;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.BadgeService;
import cn.edu.nju.software.service.BadgeTypeService;
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
	BadgeService badgeService;
	@Autowired 
	BadgeTypeService badgeTypeService;

	@ApiOperation("获取用户的徽章列表")
    @RequestMapping(value = "/getAllBageOfUser",method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Badge>> getBadgeOfUser(
    		HttpServletRequest request, HttpServletResponse response){
		ResponseData<List<Badge>> responseData = new ResponseData<>();
        User user = UserChecker.checkUser(request);
        List<Badge> badgeList = badgeService.getBadgeOfUser(user.getId());
        //Integer count = userBadgeService.getCountBageOfUser(user.getId());
        responseData.jsonFill(1, null, badgeList);
        responseData.setCount(badgeList.size());
		return responseData;
	}
	
	@ApiOperation("根据徽章获取所在徽章系统")
    @RequestMapping(value = "/getBadgeTypeByBadgeId",method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<BadgeType> getBageTypeByBageId(
    		@ApiParam("徽章id") @RequestParam(value="bageId") Integer bageId,
    		HttpServletRequest request, HttpServletResponse response){
		ResponseData<BadgeType> responseData = new ResponseData<>();
        BadgeType badgeType = badgeTypeService.getBadgeTypeByBadgeId(bageId);
        responseData.jsonFill(1, null, badgeType);
		return responseData;
	}
	
	@ApiOperation("获取徽章系统列表")
    @RequestMapping(value = "/getBadgeTypeListByPage",method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<BadgeType>> getBadgeTypeListByPage(
    		@ApiParam("页码") @RequestParam(value="page") Integer page,
    		@ApiParam("页面大小") @RequestParam(value="pageSize") Integer pageSize,
    		HttpServletRequest request, HttpServletResponse response){
		ResponseData<List<BadgeType>> responseData = new ResponseData<>();
		PageInfo<BadgeType> pageInfo = badgeTypeService.getBadgeTypeListByPage(page,pageSize);
		responseData.jsonFill(1, null,pageInfo.getList());
		responseData.setCount((int)pageInfo.getTotal());
		return responseData;
	}
	
	@ApiOperation("获取徽章系统下的徽章列表")
    @RequestMapping(value = "/getBadgeListByTypeId",method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Badge>> getBadgeListByTypeId(
    		@ApiParam("徽章类型ID") @RequestParam(value="typeId") Integer typeId,
    		HttpServletRequest request, HttpServletResponse response){
		ResponseData<List<Badge>> responseData = new ResponseData<>();
		List<Badge> badgeList = badgeService.getBadgeListByTypeId(typeId);
        responseData.jsonFill(1, null, badgeList);
        return responseData;
	}
}
