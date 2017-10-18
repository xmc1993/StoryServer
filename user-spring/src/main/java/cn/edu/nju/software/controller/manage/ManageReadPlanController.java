package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.annotation.RequiredPermissions;
import cn.edu.nju.software.entity.ReadingPlan;
import cn.edu.nju.software.entity.ReadingPlanStoryGroup;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.ReadPlanService;
import cn.edu.nju.software.service.ReadPlanStoryGroupService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author zs1996 E-mail:806949096@qq.com
 * @version 创建时间：2017年9月21日
 */

@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageReadPlanController {
	@Autowired
	ReadPlanService readPlanService;
	@Autowired
	ReadPlanStoryGroupService readPlanStoryGroupService;

	@RequiredPermissions({4, 23})
	@ApiOperation(value = "分页获取所有阅读计划", notes = "")
	@RequestMapping(value = "/selectAllReadPlan", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<ReadingPlan>> selectAllReadPlan(@ApiParam("page") @RequestParam Integer page,
			@ApiParam("pageSize") @RequestParam Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<List<ReadingPlan>> responseData = new ResponseData<>();
		responseData = readPlanService.getAllReadPlan(page, pageSize);
		return responseData;
	}

	@RequiredPermissions({1, 23})
	@ApiOperation(value = "新建阅读计划")
	@RequestMapping(value = "/saveReadPlan", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<ReadingPlan> saveReadPlan(@ApiParam("年龄段(请传该年龄段最大的天数的)") @RequestParam String ageGroup,
			@ApiParam("时间点(格式:yyyy/mm/dd) 备注:这个是字符串类型") @RequestParam String timepoint,
			@ApiParam("题目") @RequestParam String title,
			@ApiParam("封面url") @RequestParam(value = "coverurl", required = false) String coverurl,
			@ApiParam("内容") @RequestParam String content, HttpServletRequest request, HttpServletResponse response) {
		ResponseData<ReadingPlan> responseData = new ResponseData<>();
		ReadingPlan readingPlan = new ReadingPlan();
		readingPlan.setAgegroup(ageGroup);
		readingPlan.setTimepoint(timepoint);
		readingPlan.setContent(content);
		readingPlan.setCoverurl(coverurl);
		readingPlan.setCreatetime(new Date());
		readingPlan.setUpdatetime(new Date());
		readingPlan.setTitle(title);
		readingPlan.setValid(1);
		int res = readPlanService.saveReadPlan(readingPlan);
		if (res == 1) {
			responseData.jsonFill(1, null, readingPlan);
			return responseData;
		}
		responseData.jsonFill(2, "添加失败", null);
		return responseData;
	}

	@RequiredPermissions({1, 23})
	@ApiOperation(value = "为阅读计划添加故事", notes = "添加可能有些慢因为得向数据库插入21条数据")
	@RequestMapping(value = "/addStoryForReadPlan", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> addStoryForReadPlan(@ApiParam("阅读计划id") @RequestParam Integer ReadingPlanId,
			@ApiParam("故事ID(21个故事ID) 备注:请按顺序放入,后台会按照存放顺序进行排序") @RequestParam Integer[] storyIds,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData<>();
		int order = 1;
		for (Integer storyId : storyIds) {
			ReadingPlanStoryGroup readingPlanStoryGroup = new ReadingPlanStoryGroup();
			readingPlanStoryGroup.setCreatetime(new Date());
			readingPlanStoryGroup.setUpdatetime(new Date());
			readingPlanStoryGroup.setStoryid(storyId);
			readingPlanStoryGroup.setMyorder(order);
			readingPlanStoryGroup.setReadingplanid(ReadingPlanId);
			readPlanStoryGroupService.saveReadPlanStory(readingPlanStoryGroup);
			order++;
		}
		responseData.jsonFill(1, null, true);
		return responseData;
	}

	@RequiredPermissions({2, 23})
	@ApiOperation(value = "删除阅读计划(包括添加的故事)", notes = "")
	@RequestMapping(value = "/deleteReadPlan/{id}", method = { RequestMethod.DELETE })
	@ResponseBody
	public ResponseData<Boolean> deleteReadPlan(@ApiParam("阅读计划id") @PathVariable Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData<>();
		readPlanStoryGroupService.deleteReadPlanStoryByReadPlanId(id);
		int res = readPlanService.deleteReadPlan(id);
		if (res == 1) {
			responseData.jsonFill(1, null, true);
			return responseData;
		}
		responseData.jsonFill(2, null, false);
		return responseData;
	}

	@RequiredPermissions({3, 23})
	@ApiOperation(value = "修改阅读计划", notes = "")
	@RequestMapping(value = "/updateReadPlanById", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> updataReadPlan(@ApiParam("阅读计划id") @RequestParam Integer id,
			@ApiParam("年龄段") @RequestParam(value = "ageGroup", required = false) String ageGroup,
			@ApiParam("时间点(格式:yyyy/MM) 备注:这个是字符串类型") @RequestParam(value = "timepoint", required = false) String timepoint,
			@ApiParam("题目") @RequestParam(value = "title", required = false) String title,
			@ApiParam("封面url") @RequestParam(value = "coverurl", required = false) String coverurl,
			@ApiParam("内容") @RequestParam(value = "content", required = false) String content,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData<>();
		ReadingPlan readingPlan = readPlanService.selectReadPlanById(id);
		if (readingPlan == null) {
			responseData.jsonFill(2, "没有此阅读计划", null);
			return responseData;
		}
		if (ageGroup != null)
			readingPlan.setAgegroup(ageGroup);
		if (timepoint != null)
			readingPlan.setTimepoint(timepoint);
		if (title != null)
			readingPlan.setTitle(title);
		if (coverurl != null)
			readingPlan.setCoverurl(coverurl);
		if (content != null)
			readingPlan.setContent(content);

		int res = readPlanService.updateReadPlan(readingPlan);
		if (res == 1) {
			responseData.jsonFill(1, null, true);
			return responseData;
		}
		responseData.jsonFill(2, null, false);
		return responseData;
	}

	// 这些接口都写得好烂啊啊啊，不是我写的，以后回来优化
	@RequiredPermissions({3, 23})
	@ApiOperation(value = "为阅读计划修改故事", notes = "这个反应可能也会有点慢")
	@RequestMapping(value = "/updateStoryForReadPlan", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> updateStoryForReadPlan(@ApiParam("阅读计划id") @RequestParam Integer ReadingPlanId,
			@ApiParam("故事ID(21个故事ID) 备注:请按顺序放入,后台会按照存放顺序进行排序") @RequestParam Integer[] storyIds,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData<>();
		readPlanStoryGroupService.deleteReadPlanStoryByReadPlanId(ReadingPlanId);
		int order = 1;
		for (Integer storyId : storyIds) {
			ReadingPlanStoryGroup readingPlanStoryGroup = new ReadingPlanStoryGroup();
			readingPlanStoryGroup.setCreatetime(new Date());
			readingPlanStoryGroup.setUpdatetime(new Date());
			readingPlanStoryGroup.setStoryid(storyId);
			readingPlanStoryGroup.setMyorder(order);
			readingPlanStoryGroup.setReadingplanid(ReadingPlanId);
			readPlanStoryGroupService.saveReadPlanStory(readingPlanStoryGroup);
			order++;
		}
		responseData.jsonFill(1, null, true);
		return responseData;
	}

	@RequiredPermissions({4, 23})
	@ApiOperation(value = "根据阅读计划id分页查询故事组")
	@RequestMapping(value = "/getStoryGroupByPlanId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<ReadingPlanStoryGroup>> getStoryGroupByPlanId(
			@ApiParam("阅读计划id") @RequestParam Integer ReadingPlanId, @ApiParam("page") @RequestParam Integer page,
			@ApiParam("pageSize") @RequestParam Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<List<ReadingPlanStoryGroup>> responseData = new ResponseData<>();
		responseData = readPlanStoryGroupService.getReadPlanStoryGroupByPlanId(ReadingPlanId, page, pageSize);
		return responseData;
	}

	@RequiredPermissions({4, 23})
	@ApiOperation(value = "根据阅读计划id查询阅读计划详情")
	@RequestMapping(value = "/getReadPlanById", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<ReadingPlan> getStoryGroupByPlanId(@ApiParam("阅读计划id") @RequestParam Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<ReadingPlan> responseData = new ResponseData<>();
		ReadingPlan readingPlan = readPlanService.selectReadPlanById(id);
		responseData.jsonFill(1, null, readingPlan);
		return responseData;
	}
}
