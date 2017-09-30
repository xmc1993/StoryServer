package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.annotation.RequiredPermissions;
import cn.edu.nju.software.entity.Admin;
import cn.edu.nju.software.entity.AdminPower;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.AdminPowerService;
import cn.edu.nju.software.service.AdminService;
import cn.edu.nju.software.util.*;
import cn.edu.nju.software.vo.response.LoginResponseVo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/15.
 */

@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private AdminPowerService adminPowerService;

	@ApiOperation(value = "登录", notes = "")
	@RequestMapping(value = "/auth", method = { RequestMethod.POST })
	@ResponseBody
	public LoginResponseVo login(@ApiParam("用户名") @RequestParam("username") String username,
			@ApiParam("密码(用md5加密)") @RequestParam("password") String password, HttpServletRequest request,
			HttpServletResponse response) {
		Admin admin = adminService.getAdminByUsername(username);
		if (admin == null) {
			throw new RuntimeException("用户不存在");
		}
		// todo 更严格的安全校验
		if (!admin.getPassword().equals(password)) {
			throw new RuntimeException("密码错误");
		}
		admin.setAccessToken(Util.getToken());
		long currentTime = System.currentTimeMillis();
		currentTime += 1000 * 60 * 60 * 6;// 设置为6小时后失效
		admin.setExpireTime(new Date(currentTime));
		boolean res = adminService.updateAccessToken(admin);
		if (!res) {
			throw new RuntimeException("登录失败，服务器错误。");
		}

		// 在缓存中存入登录信息
		Jedis jedis = JedisUtil.getJedis();
		jedis.set(admin.getAccessToken().getBytes(), ObjectAndByte.toByteArray(admin));
		jedis.expire(admin.getAccessToken().getBytes(), 60 * 60 * 6);// 缓存用户信息6小时

		String key = "PowerCodes-" + admin.getId();
		byte[] bytes = jedis.get(key.getBytes());
		List<Integer> powerCodeList;
		if (bytes == null) {
			powerCodeList = adminPowerService.getAdminPowerCodeListByAdminId(admin.getId());
			jedis.set(key.getBytes(), ObjectAndByte.toByteArray(powerCodeList));
		} else {
			powerCodeList = (List<Integer>) ObjectAndByte.toObject(bytes);
		}
		jedis.close();

		LoginResponseVo loginResponseVo = new LoginResponseVo();
		loginResponseVo.setPowerCodeList(powerCodeList);
		loginResponseVo.setId(admin.getId());
		loginResponseVo.setAccessToken(admin.getAccessToken());
		return loginResponseVo;
	}

	@ApiOperation(value = "获得用户所有权限", notes = "")
	@RequestMapping(value = "/getSelfPowerList", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<AdminPower>> getSelfPowerList(HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<AdminPower>> responseData = new ResponseData<>();
		Admin admin = (Admin) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		List<AdminPower> adminPowerList = adminPowerService.getAdminPowerListByAdminId(admin.getId());
		responseData.jsonFill(1, null, adminPowerList);
		return responseData;
	}

	@ApiOperation(value = "登出", notes = "")
	@RequestMapping(value = "/auth", method = { RequestMethod.DELETE })
	@ResponseBody
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		String accessToken = request.getHeader("Authorization");
		if (!StringUtil.isEmpty(accessToken)) {
			// 注销管理员的session信息
			Jedis jedis = JedisUtil.getJedis();
			jedis.del(accessToken.getBytes());
			jedis.close();
		}
	}

	@RequiredPermissions({ 1, 5 })
	@ApiOperation(value = "测试权限接口", notes = "")
	@RequestMapping(value = "/test", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<String> test() {
		ResponseData<String> responseData = new ResponseData<>();
		responseData.jsonFill(1, null, "测试数据");
		return responseData;
	}

	@RequiredPermissions({ 1, 15 })
	@ApiOperation(value = "新增后台用户", notes = "")
	@RequestMapping(value = "/admins", method = { RequestMethod.POST })
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public Admin publishAdmin(@ApiParam("徽章类型项") @RequestBody Admin admin, HttpServletRequest request,
			HttpServletResponse response) {
		admin.setPassword(Util.getMd5(admin.getPassword()));
		admin.setCreateTime(new Date());
		admin.setUpdateTime(new Date());
		adminService.saveAdmin(admin);
		return admin;
	}

	@RequiredPermissions({ 2, 15 })
	@ApiOperation(value = "删除后台用户", notes = "")
	@RequestMapping(value = "/admins/{id}", method = { RequestMethod.DELETE })
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAdmin(@ApiParam("ID") @PathVariable int id, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData<>();
		boolean success = adminService.deleteAdmin(id);
		if (!success) {
			throw new RuntimeException("删除失败");
		}
	}

	@RequiredPermissions({ 4, 15 })
	@ApiOperation(value = "分页获得后台用户", notes = "")
	@RequestMapping(value = "/getAdminListByPage", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<Admin>> getAdminListByPage(@ApiParam("PAGE") @RequestParam int page,
			@ApiParam("SIZE") @RequestParam int pageSize, HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<Admin>> responseData = new ResponseData<>();
		List<Admin> adminList = adminService.getAdminListByPage(page, pageSize);
		responseData.jsonFill(1, null, adminList);
		responseData.setCount(adminService.getAdminCount());
		return responseData;
	}
}
