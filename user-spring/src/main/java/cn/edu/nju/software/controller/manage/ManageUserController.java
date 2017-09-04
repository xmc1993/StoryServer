package cn.edu.nju.software.controller.manage;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.user.UserMessageService;
import cn.edu.nju.software.util.Util;

/**
* @author zs
* @version 创建时间：2017年9月2日 下午8:06:10
*/

@Api(value = "User", description = "用户管理接口")
@Controller
@RequestMapping("/manageUser")
public class ManageUserController {
	@Autowired
	UserMessageService userMessageService;

	// 删除用户
	@RequestMapping(value = "/manageUser/{id}", method = { RequestMethod.DELETE })
	@ResponseBody
	@ApiOperation(value = "根据用户ID删除用户", notes = "")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseData<Boolean> deleteAdminPower(@ApiParam("id") @PathVariable int id, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData();
		boolean res = userMessageService.deleteUser(id);
		responseData.jsonFill(res ? 1 : 2, null, res);
		if (!res) {
			throw new RuntimeException("删除失败");
		}
		return responseData;
	}

	// 根据用户姓名查找
	@RequestMapping(value = "/getUserByNickname", method = { RequestMethod.GET })
	@ApiOperation(value = "根据用户姓名查找", notes = "")
	@ResponseBody
	public ResponseData<List<User>> getUserByNickname(@ApiParam("nickName") @RequestParam String nickName,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<User>> responseData = new ResponseData<>();
		List<User> userList = userMessageService.getUserByNickname(nickName);
		responseData.jsonFill(1, null, userList);
		return responseData;
	}

	// 根据用户ID查找用户
	@ApiOperation(value = "根据用户ID查找用户", notes = "")
	@RequestMapping(value = "/getUserById", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<User> getUserById(@ApiParam("ID") @RequestParam int id, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<User> responseData = new ResponseData<>();
		User user = userMessageService.getUserById(id);
		responseData.jsonFill(1, null, user);
		return responseData;
	}

	// 添加用户
	@ApiOperation(value = "添加用户", notes = "")
	@RequestMapping(value = "/saveUser", method = { RequestMethod.POST })
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public User getUserById(@ApiParam("表单提交的添加用户信息") @RequestParam User user, HttpServletRequest request,
			HttpServletResponse response) {
		user.setPassword(Util.getMd5(user.getPassword()));
		user.setAccessToken(UUID.randomUUID()+"");
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		userMessageService.saveUser(user);
		return user;
	}

	// 更新用户信息
	@ApiOperation(value = "更新用户信息", notes = "")
	@RequestMapping(value = "/updateUser", method = { RequestMethod.POST })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ResponseBody
	public User updateUser(@ApiParam("表单提交的更新后用户信息") @RequestParam User user, HttpServletRequest request,
			HttpServletResponse response) {
		user.setPassword(Util.getMd5(user.getPassword()));
		user.setUpdateTime(new Date());
		userMessageService.updateUser(user);
		return user;
	}
}
