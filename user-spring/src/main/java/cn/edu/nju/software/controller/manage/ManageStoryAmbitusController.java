package cn.edu.nju.software.controller.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.nju.software.annotation.RequiredPermissions;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryAmbitus;
import cn.edu.nju.software.service.StoryAmbitusService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.service.StoryService;
import cn.edu.nju.software.vo.StoryAmbitusVo;

/**
 * @author zs1996 E-mail:806949096@qq.com
 * @version 创建时间：2017年9月19日
 */

@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageStoryAmbitusController {
	@Autowired
	StoryAmbitusService storyAmbitusService;

	@Autowired
	StoryService storyService;

	@RequiredPermissions({ 4, 17 })
	@ApiOperation(value = "分页获取所有故事周边(包括故事名)", notes = "")
	@RequestMapping(value = "/selectAllStoryAmbitus", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryAmbitusVo>> selectAllStoryAmbitus(@ApiParam("page") @RequestParam Integer page,
			@ApiParam("pageSize") @RequestParam Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<List<StoryAmbitus>> responseData = new ResponseData<>();
		responseData = storyAmbitusService.selectAllStoryAmbitus(page, pageSize);

		// 需求修改 需要发返回故事名(这里纠结是把压力放在服务器还是数据库上，暂时先把压力放在数据库上，已做优化)
		List<StoryAmbitus> list = responseData.getObj();
		List<StoryAmbitusVo> storyAmbitusVoList = new ArrayList<>();
		for (StoryAmbitus storyAmbitus : list) {
			StoryAmbitusVo storyAmbitusVo = new StoryAmbitusVo();
			String storyName = storyService.getStoryNameById(storyAmbitus.getStoryid());
			BeanUtils.copyProperties(storyAmbitus, storyAmbitusVo);

			storyAmbitusVo.setStoryName(storyName);
			storyAmbitusVoList.add(storyAmbitusVo);
		}
		ResponseData<List<StoryAmbitusVo>> responseData2 = new ResponseData<>();
		responseData2.jsonFill(1, null, storyAmbitusVoList);
		responseData2.setCount(responseData.getCount());
		return responseData2;
	}

	@RequiredPermissions({ 4, 17 })
	@ApiOperation(value = "根据故事名字获取故事周边", notes = "分页，模糊查询")
	@RequestMapping(value = "/selectStoryAmbitusByStoryTitle", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryAmbitus>> selectStoryAmbitusByStoryTitle(@ApiParam("OFFSET") @RequestParam int offset,
			@ApiParam("LIMIT") @RequestParam int limit, @ApiParam("storyTitle") @RequestParam String storyTitle,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<StoryAmbitus>> responseData = new ResponseData<>();
		List<Story> stories = storyService.getStoryByFuzzyQuery(storyTitle, offset, limit);
		if (stories == null || stories.isEmpty()) {
			responseData.jsonFill(2, "该故事没有故事周边", null);
			return responseData;
		}
		List<StoryAmbitus> storyAmbitusList = new ArrayList<>();
		for (Story story : stories) {
			List<StoryAmbitus> storyAmbitusList2 = storyAmbitusService.getStoryAmbitusByStoryId(story.getId(), 0, 1)
					.getObj();
			if (storyAmbitusList2 == null || storyAmbitusList2.isEmpty()) {
				continue;
			}
			storyAmbitusList.add(storyAmbitusList2.get(0));
		}
		responseData.jsonFill(1, null, storyAmbitusList);
		responseData.setCount(responseData.getCount());
		return responseData;
	}

	@RequiredPermissions({ 1, 17 })
	@ApiOperation(value = "新建故事的周边", notes = "")
	@RequestMapping(value = "/saveStoryAmbitus", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<StoryAmbitus> saveStoryAmbitus(@ApiParam("故事ID") @RequestParam Integer storyId,
			@ApiParam("题目") @RequestParam String title, @ApiParam("徽章url") @RequestParam String icon,
			@ApiParam("视频url") @RequestParam(value = "videoUrl", required = false) String videoUrl,
			@ApiParam("内容") @RequestParam String content, HttpServletRequest request, HttpServletResponse response) {
		ResponseData<StoryAmbitus> responseData = new ResponseData<>();
		StoryAmbitus storyAmbitus = new StoryAmbitus();
		storyAmbitus.setStoryid(storyId);
		storyAmbitus.setContent(content);
		storyAmbitus.setCreatetime(new Date());
		storyAmbitus.setUpdatetime(new Date());
		storyAmbitus.setTitle(title);
		if (videoUrl != null) {
			storyAmbitus.setVideourl(videoUrl);
		}
		storyAmbitus.setValid(1);
		storyAmbitus.setIcon(icon);
		int res = storyAmbitusService.saveStoryAmbitus(storyAmbitus);
		if (res == 1) {
			responseData.jsonFill(1, null, storyAmbitus);
			return responseData;
		}
		responseData.jsonFill(2, "添加失败", null);
		return responseData;
	}

	@RequiredPermissions({ 2, 17 })
	@ApiOperation(value = "删除故事周边根据id", notes = "")
	@RequestMapping(value = "/deleteStoryAmbitusById/{id}", method = { RequestMethod.DELETE })
	@ResponseBody
	public ResponseData<Boolean> deleteById(@ApiParam("故事周边id") @PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData<>();
		int res = storyAmbitusService.deleteById(id);
		if (res == 1) {
			responseData.jsonFill(1, null, true);
			return responseData;
		}
		responseData.jsonFill(2, "删除失败", false);
		return responseData;
	}

	@RequiredPermissions({ 3, 17 })
	@ApiOperation(value = "更新故事周边根据周边id")
	@RequestMapping(value = "/updataStoryAmbitusById", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<StoryAmbitus> updataStoryAmbitusById(@ApiParam("故事周边id") @RequestParam Integer id,
			@ApiParam("故事ID") @RequestParam(value = "storyId", required = false) Integer storyId,
			@ApiParam("题目") @RequestParam(value = "title", required = false) String title,
			@ApiParam("徽章url") @RequestParam(value = "icon", required = false) String icon,
			@ApiParam("视频url") @RequestParam(value = "videoUrl", required = false) String videoUrl,
			@ApiParam("内容") @RequestParam(value = "content", required = false) String content,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<StoryAmbitus> responseData = new ResponseData<>();
		StoryAmbitus storyAmbitus = storyAmbitusService.getStoryAmbitusById(id);
		if (storyAmbitus == null) {
			responseData.jsonFill(2, "故事周边不存在", null);
			return responseData;
		}
		if (storyId != null)
			storyAmbitus.setStoryid(storyId);
		if (title != null)
			storyAmbitus.setTitle(title);
		if (icon != null)
			storyAmbitus.setIcon(icon);
		if (videoUrl != null)
			storyAmbitus.setVideourl(videoUrl);
		if (content != null)
			storyAmbitus.setContent(content);
		storyAmbitus.setUpdatetime(new Date());
		int res = storyAmbitusService.updataById(storyAmbitus);
		if (res == 1) {
			responseData.jsonFill(1, null, storyAmbitus);
			return responseData;
		}
		responseData.jsonFill(2, "更新失败", null);
		return responseData;
	}

	@RequiredPermissions({ 4, 17 })
	@ApiOperation(value = "根据故事Id获取故事周边(当故事id为空时，返回全部的故事周边)", notes = "")
	@RequestMapping(value = "/getStoryAmbitusByStoryId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryAmbitus>> getStoryAmbitusByStoryId(
			@ApiParam("故事id") @RequestParam(value = "storyId", required = false) Integer storyId,
			@ApiParam("page") @RequestParam Integer page, @ApiParam("pageSize") @RequestParam Integer pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<StoryAmbitus>> responseData = new ResponseData<>();
		if (storyId == null) {
			responseData = storyAmbitusService.selectAllStoryAmbitus(page, pageSize);
			return responseData;
		}
		responseData = storyAmbitusService.getStoryAmbitusByStoryId(storyId, page, pageSize);
		return responseData;
	}

	@RequiredPermissions({ 4, 17 })
	@ApiOperation(value = "根据故事周边Id获取故事周边", notes = "")
	@RequestMapping(value = "/getStoryAmbitusById", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<StoryAmbitus> getStoryAmbitusById(@ApiParam("id") @RequestParam Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<StoryAmbitus> responseData = new ResponseData<>();
		StoryAmbitus storyAmbitus = storyAmbitusService.getStoryAmbitusById(id);
		if (null == storyAmbitus) {
			responseData.jsonFill(2, "没有该周边", null);
			return responseData;
		}
		responseData.jsonFill(1, null, storyAmbitus);
		return responseData;
	}

}
