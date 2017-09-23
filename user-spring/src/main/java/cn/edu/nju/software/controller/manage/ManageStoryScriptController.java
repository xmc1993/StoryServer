package cn.edu.nju.software.controller.manage;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryScript;
import cn.edu.nju.software.service.StoryScriptService;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月20日 
*/

@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageStoryScriptController {
	
	@Autowired
	StoryScriptService storyScriptService;

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

	@ApiOperation(value = "新建故事的剧本", notes = "")
	@RequestMapping(value = "/saveStoryScript", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<StoryScript> saveStoryScript(@ApiParam("故事ID") @RequestParam Integer storyId,
			@ApiParam("内容") @RequestParam String content,
			@ApiParam("角色Id") @RequestParam(value="roleId",required=false) Integer roleId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<StoryScript> responseData = new ResponseData<>();
		
			storyScriptService.deleteByStoryId(storyId);
			StoryScript storyScript2 = new StoryScript();
			storyScript2.setContent(content);
			storyScript2.setStoryid(storyId);
			storyScript2.setCreatetime(new Date());
			storyScript2.setUpdatetime(new Date());
			if(roleId!=null)storyScript2.setRoleid(roleId);
			storyScript2.setValid(1);
			int res=storyScriptService.saveStoryScript(storyScript2);
			responseData.jsonFill(res, null, storyScript2);
			return responseData;
	}

	@ApiOperation(value = "删除故事剧本根据id", notes = "")
	@RequestMapping(value = "/deleteStoryScriptById/{id}", method = { RequestMethod.DELETE })
	@ResponseBody
	public ResponseData<Boolean> deleteById(@ApiParam("故事剧本id") @PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData<>();
		int res = storyScriptService.deleteById(id);
		if (res == 1) {
			responseData.jsonFill(1, null, true);
			return responseData;
		}
		responseData.jsonFill(2, "删除失败", false);
		return responseData;
	}

	@ApiOperation(value = "更新故事剧本根据故事id")
	@RequestMapping(value = "/updataStoryScriptByStoryId", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<StoryScript> updataStoryScriptById(
			@ApiParam("故事ID") @RequestParam Integer storyId,
			@ApiParam("内容") @RequestParam(value = "content", required = false) String content,
			@ApiParam("角色ID") @RequestParam(value = "roleId", required = false) Integer roleId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<StoryScript> responseData = new ResponseData<>();
		List<StoryScript> list = storyScriptService.getStoryScriptByStoryId(storyId);
		if (list == null||list.isEmpty()) {
			responseData.jsonFill(2, "故事剧本不存在", null);
			return responseData;
		}
		StoryScript storyScript=list.get(0);
		if(content!=null)
			storyScript.setContent(content);
		if (roleId != null)
			storyScript.setRoleid(roleId);
		storyScript.setUpdatetime(new Date());
		int res = storyScriptService.updataById(storyScript);
		if (res == 1) {
			responseData.jsonFill(1, null, storyScript);
			return responseData;
		}
		responseData.jsonFill(2, "更新失败", null);
		return responseData;
	}

	@ApiOperation(value = "根据故事Id获取故事剧本", notes = "")
	@RequestMapping(value = "/getStoryScriptByStoryId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryScript>> getStoryScriptByStoryId(@ApiParam("故事id") @RequestParam Integer storyId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<StoryScript>> responseData = new ResponseData<>();
		List<StoryScript> storyScript = storyScriptService.getStoryScriptByStoryId(storyId);
		if(null==storyScript){
			responseData.jsonFill(2, "该故事没有剧本", null);
			return responseData;
		}
		responseData.jsonFill(1, null, storyScript);
		return responseData;
	}
	
	@ApiOperation(value = "根据故事剧本Id获取故事剧本", notes = "")
	@RequestMapping(value = "/getStoryScriptById", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<StoryScript> getStoryScriptById(@ApiParam("id") @RequestParam Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<StoryScript> responseData = new ResponseData<>();
		StoryScript storyScript = storyScriptService.getStoryScriptById(id);
		if(null==storyScript){
			responseData.jsonFill(2, "没有该剧本", null);
			return responseData;
		}
		responseData.jsonFill(1, null, storyScript);
		return responseData;
	}
}
