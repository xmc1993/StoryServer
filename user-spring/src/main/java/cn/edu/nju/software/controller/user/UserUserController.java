package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.Badge;
import cn.edu.nju.software.entity.Business;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.TwoTuple;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.entity.UserBase;
import cn.edu.nju.software.enums.GrantType;
import cn.edu.nju.software.service.BadgeService;
import cn.edu.nju.software.service.user.AppUserService;
import cn.edu.nju.software.service.user.UserWeChatLoginService;
import cn.edu.nju.software.service.wxpay.util.RandCharsUtils;
import cn.edu.nju.software.util.*;
import cn.edu.nju.software.vo.WeChatOAuthVo;
import cn.edu.nju.software.vo.WeChatUserInfoVo;
import cn.edu.nju.software.vo.response.LoginResponseVo;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api("user controller")
@Controller
@RequestMapping("/user")
public class UserUserController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(UserUserController.class);
	private static final String IMAGE_ROOT = "/head/";
	private final String default_avatar = "default_avatar.jpg";
	@Autowired
	private AppUserService userService;
	@Autowired
	private UserWeChatLoginService weChatLoginService;
	@Autowired
	private Business business;
	@Autowired
	private BadgeService badgeService;

	@ApiOperation(value = "获取用户信息", notes = "获取用户信息")
	@RequestMapping(value = "/getUserBaseInfo", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<User> getUserInfo(@ApiParam("用户ID") @RequestParam("userId") int userId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResponseData responseData = new ResponseData();
		UserBase userBase = userService.getUserBaseById(userId);
		if (userBase == null) {
			responseData.jsonFill(2, "用户不存在。", null);
			return responseData;
		}
		responseData.jsonFill(1, null, userBase);
		return responseData;
	}

	@ApiOperation(value = "更新token信息", notes = "")
	@RequestMapping(value = "/refreshToken", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> refreshToken(@ApiParam("Token") @RequestParam("accessToken") String accessToken,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData responseData = new ResponseData();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "用户尚未登录。", false);
			return responseData;
		}
		Jedis jedis = null;
		try {
			jedis = JedisUtil.getJedis();
			jedis.expire(accessToken.getBytes(), 60 * 60 * 24 * 30);
			responseData.jsonFill(1, null, true);
		} catch (Exception e) {
			responseData.jsonFill(2, "失败", null);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return responseData;
	}

	@ApiOperation(value = "得到当前用户信息", notes = "获取用户信息")
	@RequestMapping(value = "/getUserSelfInfo", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<User> getUserSelfInfo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResponseData responseData = new ResponseData();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "用户尚未登录。", false);
			return responseData;
		}
		user = userService.getUserById(user.getId());
		Jedis jedis = JedisUtil.getJedis();
		jedis.set(user.getAccessToken().getBytes(), ObjectAndByte.toByteArray(user));
		jedis.close();
		user.setVerifyCode(null);
		user.setExpireTime(null);
		user.setWxUnionId(null);
		user.setPassword(null);

		responseData.jsonFill(1, null, user);
		return responseData;
	}

	@ApiOperation(value = "修改当前用户信息", notes = "修改用户信息")
	@RequestMapping(value = "/updateUserSelfInfo", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> getUserSelfInfo(HttpServletRequest request, HttpServletResponse response,
			@ApiParam("昵称") @RequestParam(value = "nickname", required = false) String nickname,
			@ApiParam("性别") @RequestParam(value = "sex", required = false) String sex,
			@ApiParam("公司") @RequestParam(value = "company", required = false) String company,
			@ApiParam("城市") @RequestParam(value = "city", required = false) String city) throws Exception {
		ResponseData<Boolean> responseData = new ResponseData<>();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "用户尚未登录。", false);
			return responseData;
		}
		if (nickname != null)
			user.setNickname(nickname);
		if (sex != null)
			user.setSex(sex);
		if (company != null)
			user.setCompany(company);
		if (city != null)
			user.setCity(city);
		user.setUpdateTime(new Date());
		userService.updateUser(user);
		String AccessToken = request.getHeader(TokenConfig.DEFAULT_ACCESS_TOKEN_HEADER_NAME);
		Jedis jedis = JedisUtil.getJedis();
		jedis.set(AccessToken.getBytes(), ObjectAndByte.toByteArray(user));
		jedis.close();
		responseData.jsonFill(1, null, true);
		return responseData;
	}

	@ApiOperation(value = "根据手机号获取短信验证码", notes = "获取短信")
	@RequestMapping(value = "/getVerifyCodeByMobile", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<User> getVerifyCodeByMobile(@ApiParam("手机号码") @RequestParam("mobile") String mobile,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResponseData<User> responseData = new ResponseData();
		String code = RandCharsUtils.getRandomNumber(4);
		SendSmsResponse smsResponse = SmsUtils.sendMessage("SMS_101110009", mobile, code);
		User user = userService.getUserByMobile(mobile);
		if (user == null) {
			User user2 = new User();
			user2.setMobile(mobile);
			Util.setNewAccessToken(user2);
			user2.setVerifyCode(code);
			user2.setNickname(mobile);
			user2.setCreateTime(new Date());
			user2.setUpdateTime(new Date());
			user2.setValid(0);
			user2 = userService.addOrUpdateUser(user2);
			//先软删除，如果验证码正确，再恢复。
			userService.deleteUserById(user2.getId());
			responseData.jsonFill(1, null, user2);
		} else {
			Util.setNewAccessToken(user);
			user.setVerifyCode(code);
			user = userService.addOrUpdateUser(user);
			responseData.jsonFill(1, null, user);
		}

		return responseData;
	}

	@ApiOperation(value = "用户登录", notes = "用户登录")
	@RequestMapping(value = "/loginByWeChat", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<LoginResponseVo> loginByWeChat(@ApiParam("code 授权码") @RequestParam("code") String code,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResponseData responseData = new ResponseData();
		WeChatOAuthVo weChatOAuthVo = weChatLoginService.getAccessToken(business.getWxAppId(), business.getWxSecret(),
				GrantType.AUTHORIZATION_CODE, code);
		if (null == weChatOAuthVo) {
			responseData.jsonFill(2, "微信OAuth失败", null);
			return responseData;
		}

		User user = userService.loginByUnionId(weChatOAuthVo.getUnionId());
		Boolean isNewUser = false;
		// 如果数据库没有相应的用户那么新建一个用户
		if (null == user) {
			// 获取微信用户信息
			WeChatUserInfoVo userInfo = weChatLoginService.getUserInfo(weChatOAuthVo.getAccessToken(),
					weChatOAuthVo.getOpenId());
			if (null == userInfo) {
				responseData.jsonFill(2, "获取微信用户信息失败", false);
				return responseData;
			}

			user = new User();
			user.setNickname(userInfo.getNickName());
			user.setWxUnionId(userInfo.getUnionId());// 设置unionId
			user.setSex(parse(userInfo.getSex()));
			user.setVerifyCode(null);
			user.setCreateTime(new Date());
			user.setCity(userInfo.getCity());

			String realPath = UploadFileUtil.getBaseUrl() + IMAGE_ROOT;
			String avatar = userInfo.getUnionId() + ".jpg";

			try {
				DownloadUtil.download(userInfo.getHeadImgUrl(), avatar, realPath);
			} catch (Exception e) {
				logger.error("微信头像下载失败!");
				e.printStackTrace();
				// 如果微信头像下载失败那么就使用默认头像
				user.setHeadImgUrl(UploadFileUtil.SOURCE_BASE_URL + IMAGE_ROOT + default_avatar);
			}
			user.setHeadImgUrl(UploadFileUtil.SOURCE_BASE_URL + IMAGE_ROOT + avatar);
			Util.setNewAccessToken(user);// 设置token
			long currentTime = System.currentTimeMillis();
			currentTime += 1000 * 60 * 60 * 24 * 30;// 设置为30天后失效
			user.setExpireTime(new Date(currentTime));
			user = userService.addOrUpdateUser(user);
			isNewUser = true;
		}

		// 如果用户创建失败
		if (user == null) {
			responseData.jsonFill(2, "服务器出错", null);
			return responseData;
		}
		// 获取到用户信息
		LoginResponseVo loginResponseVo = new LoginResponseVo();
		loginResponseVo.setAccessToken(user.getAccessToken());
		loginResponseVo.setId(user.getId());
		loginResponseVo.setIsNewUser(isNewUser);

		// 登录信息写入缓存
		JedisUtil.getJedis().set(user.getAccessToken().getBytes(), ObjectAndByte.toByteArray(user));
		JedisUtil.getJedis().expire(user.getAccessToken().getBytes(), 60 * 60 * 24 * 30);// 缓存用户信息30天

		// 对用户连续登陆天数处理
		TwoTuple<Integer, Boolean> tt = userService.addContinusLandDay(user.getId());
		int[] prizeDay = { 1, 3, 7, 15, 21, 30, 50, 100, 200, 365, 500, 1000 };
		Badge badge = null;
		// 今天的第一次登陆，获取badge对象
		if (!tt.getValue()) {
			for (int i = prizeDay.length; i > 0; i--) {
				if (tt.getId() == prizeDay[i - 1]) {
					badge = badgeService.getBadgeByMeasureAndType(prizeDay[i - 1], 2);
					break;
				}
			}
		}

		loginResponseVo.setBadge(badge);
		loginResponseVo.setContinuousLoginCount(tt.getId());

		responseData.jsonFill(1, null, loginResponseVo);
		return responseData;
	}

	@ApiOperation(value = "短信登录", notes = "用户登录")
	@RequestMapping(value = "/loginByShortMessage", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<LoginResponseVo> loginByShortMessage(@ApiParam("用户手机号") @RequestParam("mobile") String mobile,
			@ApiParam("短信验证码") @RequestParam("verifyCode") String verifyCode, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResponseData responseData = new ResponseData();
		User user = userService.getUserByMobile(mobile);
		if (user == null) {
			responseData.jsonFill(2, "用户不存在", null);
			return responseData;
		}
		String code = user.getVerifyCode();
		LoginResponseVo loginResponseVo = new LoginResponseVo();
		// 时间差，900秒5分钟
		long dValue = new Date().getTime() - user.getExpireTime().getTime();
		if (dValue <= 900000L) {
			if (verifyCode.equals(code)) {
				// 0的话就是新用户
				if (user.getValid()==0||user.getValid().equals(0)) {
					loginResponseVo.setIsNewUser(true);
					user.setValid(1);
				}
				//验证成功恢复身份
				userService.recoverUserById(user.getId());
				
				// 获取到用户信息
				loginResponseVo.setAccessToken(user.getAccessToken());
				loginResponseVo.setId(user.getId());


				// 登录信息写入缓存
				JedisUtil.getJedis().set(user.getAccessToken().getBytes(), ObjectAndByte.toByteArray(user));
				JedisUtil.getJedis().expire(user.getAccessToken().getBytes(), 60 * 60 * 24 * 30);// 缓存用户信息30天

				// 对用户连续登陆天数处理
				TwoTuple<Integer, Boolean> tt = userService.addContinusLandDay(user.getId());
				int[] prizeDay = { 1, 3, 7, 15, 21, 30, 50, 100, 200, 365, 500, 1000 };
				Badge badge = null;
				// 今天的第一次登陆，获取badge对象
				if (!tt.getValue()) {
					for (int i = prizeDay.length; i > 0; i--) {
						if (tt.getId() == prizeDay[i - 1]) {
							badge = badgeService.getBadgeByMeasureAndType(prizeDay[i - 1], 2);
							break;
						}
					}
				}

				loginResponseVo.setBadge(badge);
				loginResponseVo.setContinuousLoginCount(tt.getId());

				responseData.jsonFill(1, null, loginResponseVo);
				return responseData;
			}
			responseData.jsonFill(2, "验证码出错", null);
			return responseData;
		}
		responseData.jsonFill(2, "验证码超时,请重新获取", null);
		return responseData;
	}

	@ApiOperation(value = "游客登录", notes = "")
	@RequestMapping(value = "/guestLogin", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<LoginResponseVo> guestLogin(@ApiParam("DeviceId") @RequestParam String deviceId) {
		ResponseData<LoginResponseVo> responseData = new ResponseData<>();
		User user = userService.getUserByDeviceId(deviceId);

		if (user == null) {
			user = new User();
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setNickname("游客");
			user.setDeviceId(deviceId);
			user.setHeadImgUrl(UploadFileUtil.SOURCE_BASE_URL + IMAGE_ROOT + default_avatar);// 设置默认头像
		}

		Util.setNewAccessToken(user);
		long currentTime = System.currentTimeMillis();
		currentTime += 1000 * 60 * 60 * 24 * 30;// 设置为30天后失效
		user.setExpireTime(new Date(currentTime));

		user = userService.addOrUpdateUser(user);
		if (user == null) {
			responseData.jsonFill(2, "获取用户信息失败", null);
			return responseData;
		}
		// 登录信息写入缓存
		Jedis jedis = JedisUtil.getJedis();
		jedis.set(user.getAccessToken().getBytes(), ObjectAndByte.toByteArray(user));
		jedis.expire(user.getAccessToken().getBytes(), 60 * 60 * 24 * 30);// 缓存用户信息30天
		jedis.close();

		LoginResponseVo loginResponseVo = new LoginResponseVo();
		loginResponseVo.setAccessToken(user.getAccessToken());
		loginResponseVo.setId(user.getId());
		responseData.jsonFill(1, null, loginResponseVo);
		return responseData;
	}

	@ApiOperation(value = "测试用直接用ID登录(仅用于测试)", notes = "用户登录")
	@RequestMapping(value = "/mockLogin", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseData<LoginResponseVo> mockLogin(@ApiParam("用户ID") @RequestParam int id) {
		ResponseData<LoginResponseVo> responseData = new ResponseData<>();
		User user = userService.getUserByMobileOrId(String.valueOf(id));
		if (user == null) {
			responseData.jsonFill(2, "错误", null);
			return responseData;
		}

		Util.setNewAccessToken(user);
		// TODO 更新数据库
		// 登录信息写入缓存
		JedisUtil.getJedis().set(user.getAccessToken().getBytes(), ObjectAndByte.toByteArray(user));
		JedisUtil.getJedis().expire(user.getAccessToken().getBytes(), 60 * 60 * 24 * 30);// 缓存用户信息30天

		// 对用户连续登陆天数处理
		TwoTuple<Integer, Boolean> tt = userService.addContinusLandDay(user.getId());
		int[] prizeDay = { 1, 3, 7, 15, 21, 30, 50, 100, 200, 365, 500, 1000 };
		Badge badge = null;
		// 今天的第一次登陆，获取badge对象
		if (!tt.getValue()) {
			for (int i = prizeDay.length; i > 0; i--) {
				if (tt.getId() == prizeDay[i - 1]) {
					badge = badgeService.getBadgeByMeasureAndType(prizeDay[i - 1], 2);
					break;
				}
			}
		}

		LoginResponseVo loginResponseVo = new LoginResponseVo();

		loginResponseVo.setBadge(badge);
		loginResponseVo.setContinuousLoginCount(tt.getId());

		loginResponseVo.setAccessToken(user.getAccessToken());
		loginResponseVo.setId(user.getId());
		responseData.jsonFill(1, null, loginResponseVo);

		return responseData;
	}

	@RequestMapping(value = "/testError", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public boolean testError() {
		logger.error("ERROR");
		return true;
	}

	@RequestMapping(value = "/test", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public User test() {
		User user = new User();
		user.setNickname("xmc");
		user.setCity("南京");
		user.setAccessToken(UploadFileUtil.SOURCE_BASE_URL);
		return user;
	}

	private String parse(int gendar) {
		if (gendar == 1) {
			return "男";
		} else if (gendar == 2) {
			return "女";
		}
		return "未知";
	}
}
