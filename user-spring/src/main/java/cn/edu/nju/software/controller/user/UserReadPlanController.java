/*package cn.edu.nju.software.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.Baby;
import cn.edu.nju.software.entity.ReadingPlan;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.BabyService;
import cn.edu.nju.software.service.ReadPlanService;
import cn.edu.nju.software.service.ReadPlanStoryGroupService;
import cn.edu.nju.software.util.TokenConfig;

*//**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月21日 
*//*

@Api("ReadPlan controller")
@Controller
@RequestMapping("/user")
public class UserReadPlanController extends BaseController {
	@Autowired
	ReadPlanService readPlanService;
	@Autowired
	ReadPlanStoryGroupService readPlanStoryGroupService;
    @Autowired
    private BabyService babyService;
	
	@ApiOperation(value = "获取用户的阅读计划", notes = "需要登录")
	@RequestMapping(value = "/getReadPlanByUser", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<ReadingPlan>> getReadPlanByUser(HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<List<ReadingPlan>> responseData = new ResponseData<>();
		 User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
	        if (user == null) {
	            responseData.jsonFill(2, "请先登录", null);
	            response.setStatus(401);
	            return responseData;
	        }
	        //根据用户id获取用户选中的宝宝
	        Baby baby = babyService.getSelectedBaby(user.getId());
	        if (baby == null) {
	        	List<Baby> babyList = babyService.getBabyListByParentId(user.getId());
	        	if(babyList == null){
	        		responseData.jsonFill(2, "用户没有宝宝或者用户不存在", null);
	        		return responseData;
	        	}
	        	babyList.get(0).getBirthday()
	        }
	        responseData.jsonFill(1, null, baby);
	        return responseData;
		return responseData;
	}
	
	
	
	
	//方法抽取：根据baby生日
	
}*/
