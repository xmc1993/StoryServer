package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.annotation.RequiredPermissions;
import cn.edu.nju.software.entity.BabyReadInfo;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.BabyReadService;
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
 * @author zs
 * @version 创建时间：2017年9月12日 上午11:24:37
 */
@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageBabyReadController {

	@Autowired
	BabyReadService babyReadService;

	@RequiredPermissions({4, 24})
	@ApiOperation(value = "分页获取所有宝宝读内容", notes = "")
	@RequestMapping(value = "/selectAllBabyRead", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<BabyReadInfo>> selectAllBabyRead(@ApiParam("page") @RequestParam Integer page,
			@ApiParam("pageSize") @RequestParam Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<List<BabyReadInfo>> responseData = new ResponseData<>();
		responseData = babyReadService.readInfoList(page, pageSize);
		return responseData;
	}

	@RequiredPermissions({1, 24})
	@ApiOperation(value = "新建宝宝读内容", notes = "")
	@RequestMapping(value = "/saveBabyReadInfo", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<BabyReadInfo> saveBabyReadInfo(@ApiParam("故事ID") @RequestParam Integer storyId,
			@ApiParam("内容") @RequestParam String content, @ApiParam("iconUrl") @RequestParam String iconUrl,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<BabyReadInfo> responseData = new ResponseData<>();

		BabyReadInfo babyReadInfo = new BabyReadInfo();
		babyReadInfo.setContent(content);
		babyReadInfo.setStoryid(storyId);
		babyReadInfo.setIconurl(iconUrl);
		babyReadInfo.setCreattime(new Date());
		babyReadInfo.setUpdatetime(new Date());
		babyReadInfo.setValid(1);
		babyReadService.saveBabyReadInfo(babyReadInfo);
		responseData.jsonFill(1, null, babyReadInfo);
		return responseData;
	}

	@RequiredPermissions({2, 24})
	@ApiOperation(value = "删除宝宝读内容根据id", notes = "")
	@RequestMapping(value = "/deleteBabyReadById/{id}", method = { RequestMethod.DELETE })
	@ResponseBody
	public ResponseData<Boolean> deleteBabyReadById(@ApiParam("宝宝读内容id") @PathVariable Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData<>();
		int res = babyReadService.deleteBabyReadInfo(id);
		if (res == 1) {
			responseData.jsonFill(1, null, true);
			return responseData;
		}
		responseData.jsonFill(2, "删除失败", false);
		return responseData;
	}

	@RequiredPermissions({3, 24})
	@ApiOperation(value = "更新宝宝读内容根据id")
	@RequestMapping(value = "/updataBabyReadById", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<BabyReadInfo> updataBabyReadById(@ApiParam("宝宝读ID") @RequestParam Integer id,
			@ApiParam("故事ID") @RequestParam(value = "storyId", required = false) Integer storyId,
			@ApiParam("内容") @RequestParam(value = "content", required = false) String content,
			@ApiParam("iconUrl") @RequestParam(value = "iconUrl", required = false) String iconUrl,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<BabyReadInfo> responseData = new ResponseData<>();
		BabyReadInfo babyReadInfo = babyReadService.selectReadInfoById(id);
		if (storyId != null)
			babyReadInfo.setStoryid(storyId);
		if (content != null)
			babyReadInfo.setContent(content);
		if (iconUrl != null)
			babyReadInfo.setIconurl(iconUrl);
		babyReadInfo.setUpdatetime(new Date());
		int res = babyReadService.updateBabyReadInfo(babyReadInfo);
		if (res == 1) {
			responseData.jsonFill(1, null, babyReadInfo);
			return responseData;
		}
		responseData.jsonFill(2, "更新失败", null);
		return responseData;
	}

	@RequiredPermissions({4, 24})
	@ApiOperation(value = "根据宝宝读Id获取宝宝读内容", notes = "")
	@RequestMapping(value = "/getBabyReadtById", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<BabyReadInfo> getBabyReadtById(@ApiParam("id") @RequestParam Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<BabyReadInfo> responseData = new ResponseData<>();
		BabyReadInfo babyReadInfo = babyReadService.selectReadInfoById(id);
		responseData.jsonFill(1, null, babyReadInfo);
		return responseData;
	}
}
