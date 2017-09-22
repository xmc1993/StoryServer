/*package cn.edu.nju.software.controller.manage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryScript;

*//**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月22日 
*//*
@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageBabyReadController {
	
	@ApiOperation(value = "分页获取所有故事剧本", notes = "")
	@RequestMapping(value = "/selectAllStoryScript", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryScript>> selectAllStoryScript(@ApiParam("page") @RequestParam Integer page,
			@ApiParam("pageSize") @RequestParam Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<List<StoryScript>> responseData = new ResponseData<>();
		responseData = storyScriptService.selectAllStoryScript(page, pageSize);
		return responseData;
	}
}*/
