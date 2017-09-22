package cn.edu.nju.software.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wordnik.swagger.annotations.Api;

import cn.edu.nju.software.controller.BaseController;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月22日 
*/

@Api(value = "/story", description = "和故事有关的接口")
@Controller
@RequestMapping("/user")
public class UserStoryAmbitusController extends BaseController {
	
}
