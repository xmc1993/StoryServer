package cn.edu.nju.software.controller.user;

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
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryAmbitus;
import cn.edu.nju.software.service.StoryAmbitusService;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月22日 
*/

@Api(value = "/story", description = "和故事有关的接口")
@Controller
@RequestMapping("/user")
public class UserStoryAmbitusController extends BaseController {
	@Autowired
	StoryAmbitusService storyAmbitusService;
	
	@ApiOperation(value = "根据故事Id获取故事周边", notes = "")
	@RequestMapping(value = "/getStoryAmbitusByStoryId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryAmbitus>> getStoryAmbitusByStoryId(@ApiParam("故事id") @RequestParam Integer storyId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<StoryAmbitus>> responseData = new ResponseData<>();
		List<StoryAmbitus> list = storyAmbitusService.getStoryAmbitusByStoryId(storyId,0,10).getObj();
		if(null==list){
			responseData.jsonFill(2, "该故事没有周边", null);
			return responseData;
		}
		responseData.jsonFill(1, null, list);
		responseData.setCount(list.size());
		return responseData;
	}
}
