package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.annotation.RequiredPermissions;
import cn.edu.nju.software.entity.StoryRole;
import cn.edu.nju.software.entity.StoryRoleAudio;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.StoryRoleAudioService;
import cn.edu.nju.software.service.StoryRoleService;
import cn.edu.nju.software.util.UploadFileUtil;
import cn.edu.nju.software.vo.StoryRoleAudioList;
import cn.edu.nju.software.vo.StoryRoleAudioVo;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/15.
 */
@Api(value = "Admin", description = "管理接口")
@Controller()
@RequestMapping("/manage")
public class ManageStoryRoleController {
	private static final Logger logger = LoggerFactory.getLogger(ManageStoryRoleController.class);
	// 该字段标记语音为官方的userId
	private static final Integer adminSign = 2;
	@Autowired
	private StoryRoleService storyRoleService;
	@Autowired
	private StoryRoleAudioService storyRoleAudioService;

	@RequiredPermissions({ 1, 5 })
	@ApiOperation(value = "新增故事角色项", notes = "")
	@RequestMapping(value = "/storyRoles", method = { RequestMethod.POST })
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public StoryRole publishStoryRole(@ApiParam("故事角色项") @RequestBody StoryRole storyRole, HttpServletRequest request,
			HttpServletResponse response) {
		storyRole.setCreateTime(new Date());
		storyRole.setUpdateTime(new Date());
		storyRoleService.saveStoryRole(storyRole);
		return storyRole;
	}

	@RequiredPermissions({ 3, 5 })
	@ApiOperation(value = "更新故事角色项", notes = "")
	@RequestMapping(value = "/storyRoles/{id}", method = { RequestMethod.POST })
	@ResponseBody
	public StoryRole updateStoryRole(@ApiParam("ID") @PathVariable int id,
			@ApiParam("") @RequestBody StoryRole storyRole, HttpServletRequest request, HttpServletResponse response) {
		// storyRole.setId(id);
		storyRole.setUpdateTime(new Date());
		return storyRoleService.updateStoryRole(storyRole) ? storyRole : null;
	}

	@RequiredPermissions({ 2, 5 })
	@ApiOperation(value = "删除故事角色项", notes = "")
	@RequestMapping(value = "/storyRoles/{id}", method = { RequestMethod.DELETE })
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteStoryRole(@ApiParam("ID") @PathVariable int id, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData<>();
		boolean success = storyRoleService.deleteStoryRole(id);
		if (!success) {
			throw new RuntimeException("删除失败");
		}
	}

	@RequiredPermissions({ 4, 5 })
	@ApiOperation(value = "根据ID获得故事角色项", notes = "")
	@RequestMapping(value = "/storyRoles/{id}", method = { RequestMethod.GET })
	@ResponseBody
	public StoryRole getStoryById(@ApiParam("ID") @PathVariable int id, HttpServletRequest request,
			HttpServletResponse response) {
		StoryRole storyRole = storyRoleService.getStoryRoleById(id);
		if (storyRole == null) {
			throw new RuntimeException("无效的ID");
		} else {
			return storyRole;
		}
	}

	@RequiredPermissions({ 4, 5 })
	@ApiOperation(value = "根据故事角色类型故事角色项列表", notes = "")
	@RequestMapping(value = "/getStoryRoleListByStoryId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryRole>> getStoryRoleListByTypeId(@ApiParam("故事ID") @RequestParam int storyId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<StoryRole>> responseData = new ResponseData<>();
		List<StoryRole> storyRoleList = storyRoleService.getStoryRoleListByStoryId(storyId);
		responseData.jsonFill(1, null, storyRoleList);
		return responseData;
	}

	@ApiOperation(value = "添加故事角色音频")
	@RequestMapping(value = "/saveStoryRoleAudio", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<StoryRoleAudioList> saveStoryRoleAudio(@ApiParam("故事音频列表")@RequestBody StoryRoleAudioList storyRoleAudioList,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<StoryRoleAudioList> responseData = new ResponseData<>();
		List<StoryRoleAudioVo> list=storyRoleAudioList.getRoleMsg();
		Integer storyId=storyRoleAudioList.getStoryId();
		for (StoryRoleAudioVo storyRoleAudioVo : list) {
			StoryRoleAudio storyRoleAudio = new StoryRoleAudio();
			storyRoleAudio.setStoryid(storyId);
			storyRoleAudio.setValid(1);
			storyRoleAudio.setRoleid(storyRoleAudioVo.getRoleId());
			storyRoleAudio.setContent(storyRoleAudioVo.getContent());
			storyRoleAudio.setCreatetime(new Date());
			storyRoleAudio.setUpdatetime(new Date());
			storyRoleAudio.setUserid(adminSign);
			storyRoleAudioService.saveRoleAudio(storyRoleAudio);
		}
		responseData.jsonFill(1, null,storyRoleAudioList);
		responseData.setCount(list.size());
		return responseData;
	}

	@ApiOperation(value = "删除故事角色的音频(仅删除音频记录，不删除音频文件)", notes = "如需要在服务器删除音频文件，使用deleteByUrl接口")
	@RequestMapping(value = "/deleteStoryRoleAudioById/{id}", method = { RequestMethod.DELETE })
	@ResponseBody
	public ResponseData<Boolean> deleteStoryRoleAudioById(@ApiParam("ID") @PathVariable Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		StoryRoleAudio storyRoleAudio = storyRoleAudioService.selectById(id);
		ResponseData<Boolean> responseData = new ResponseData<>();
		if (storyRoleAudio == null) {
			responseData.jsonFill(2, "你需要删除的音频不存在", null);
		}
		// 功能尚未完成，拿conten字段内容删除
		// UploadFileUtil.deleteFileByUrl(storyRoleAudio.getUrl());

		int res = storyRoleAudioService.deleteById(id);
		if (res == 1) {
			responseData.jsonFill(res, null, true);
			return responseData;
		}
		responseData.jsonFill(2, "删除失败", null);
		return responseData;
	}

	@ApiOperation(value = "查询故事角色的音频")
	@RequestMapping(value = "/getStoryRoleAudioById", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<StoryRoleAudio> getStoryRoleAudioById(@ApiParam("ID") @RequestParam Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		StoryRoleAudio storyRoleAudio = storyRoleAudioService.selectById(id);
		ResponseData<StoryRoleAudio> responseData = new ResponseData<>();
		if (storyRoleAudio == null) {
			responseData.jsonFill(2, "该音频不存在", null);
		}
		responseData.jsonFill(1, null, storyRoleAudio);
		return responseData;
	}

	@ApiOperation(value = "根据用户id查询角色音频")
	@RequestMapping(value = "/getStoryRoleAudioByUserId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryRoleAudio>> getStoryRoleAudioByUserId(
			@ApiParam(value = "用户ID") @RequestParam Integer userId, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<List<StoryRoleAudio>> responseData = new ResponseData<>();
		List<StoryRoleAudio> storyRoleAudioList = storyRoleAudioService.getStoryRoleAudioByUserId(userId);
		if (storyRoleAudioList == null) {
			responseData.jsonFill(2, "不存在音频", null);
		}
		responseData.jsonFill(1, null, storyRoleAudioList);
		responseData.setCount(storyRoleAudioList.size());
		return responseData;
	}

	@ApiOperation(value = "根据用户id,角色id查询音频")
	@RequestMapping(value = "/getStoryRoleAudioByUserIdAndRoleId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryRoleAudio>> getStoryRoleAudioByUserIdAndRoleId(
			@ApiParam(value = "用戶id") @RequestParam Integer userId,
			@ApiParam(value = "角色id") @RequestParam Integer roleId, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<List<StoryRoleAudio>> responseData = new ResponseData<>();
		List<StoryRoleAudio> storyRoleAudioList = storyRoleAudioService.getStoryRoleAudioByUserIdAndRoleId(userId,
				roleId);
		if (storyRoleAudioList == null) {
			responseData.jsonFill(2, "不存在音频", null);
		}
		responseData.jsonFill(1, null, storyRoleAudioList);
		responseData.setCount(storyRoleAudioList.size());
		return responseData;
	}

	@ApiOperation(value = "根据故事id分页获取所有音频(这个是包括用户上传的音频)")
	@RequestMapping(value = "/getStoryRoleAudioByStoryId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryRoleAudio>> getStoryRoleAudioByUserIdAndRoleId(
			@ApiParam(value = "故事id") @RequestParam Integer storyId,
			@ApiParam(value = "page") @RequestParam Integer page,
			@ApiParam(value = "pageSize") @RequestParam Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<List<StoryRoleAudio>> responseData = new ResponseData<>();
		responseData = storyRoleAudioService.getStoryRoleAuioByStoryId(storyId, page, pageSize);
		return responseData;
	}

	@ApiOperation(value = "根据故事Id获取官方所有音频(这个是获取官方的)")
	@RequestMapping(value = "/getAdminStoryRoleAudioByStory", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<StoryRoleAudioList> getStoryRoleAudioByUserAndStory(
			@ApiParam(value = "故事Id") @RequestParam Integer storyId, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<StoryRoleAudioList> responseData =new ResponseData<>();
		
		List<StoryRoleAudio> list = storyRoleAudioService.getStoryRoleAuioByUserAndStory(2, storyId);
		List<StoryRoleAudioVo> list2=new ArrayList<StoryRoleAudioVo>();
	for (StoryRoleAudio storyRoleAudio : list) {
		StoryRoleAudioVo storyRoleAudioVo=new StoryRoleAudioVo();
		storyRoleAudioVo.setContent(storyRoleAudio.getContent());
		storyRoleAudioVo.setRoleId(storyRoleAudio.getRoleid());
		list2.add(storyRoleAudioVo);
	}	
		StoryRoleAudioList storyRoleAudioList=new StoryRoleAudioList();
		storyRoleAudioList.setRoleMsg(list2);
		storyRoleAudioList.setStoryId(storyId);
		responseData.jsonFill(1, null, storyRoleAudioList);
		responseData.setCount(list2.size());
		return responseData;
	}

	@ApiOperation(value = "删除文件服务器的文件(用于测试时产生的脏数据删除)")
	@RequestMapping(value = "/deleteByUrl")
	@ResponseBody
	public ResponseData<Boolean> deleteByUrl(@ApiParam(value = "URL") @RequestParam String url,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData<>();
		boolean res = UploadFileUtil.deleteFileByUrl(url);
		responseData.jsonFill(1, null, res);
		return responseData;
	}

}