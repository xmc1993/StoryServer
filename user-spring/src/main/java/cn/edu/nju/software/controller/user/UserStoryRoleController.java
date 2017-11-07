package cn.edu.nju.software.controller.user;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryRole;
import cn.edu.nju.software.entity.StoryRoleAudio;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.StoryRoleAudioService;
import cn.edu.nju.software.service.StoryRoleService;
import cn.edu.nju.software.service.wxpay.util.RandCharsUtils;
import cn.edu.nju.software.util.TokenConfig;
import cn.edu.nju.software.util.UploadFileUtil;

/**
 * @author zs1996 E-mail:806949096@qq.com
 * @version 创建时间：2017年9月19日
 */

@Api("storyRole")
@Controller
@RequestMapping("/user")
public class UserStoryRoleController {
	private static final String ROLE_ROOT = "/role/"; // 角色的基础路径
	@Autowired
	private StoryRoleService storyRoleService;
	@Autowired
	private StoryRoleAudioService storyRoleAudioService;

	@ApiOperation(value = "根据故事id获取故事角色项列表")
	@RequestMapping(value = "/getStoryRoleListByStoryId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryRole>> getStoryRoleListByTypeId(@ApiParam("故事ID") @RequestParam int storyId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<StoryRole>> responseData = new ResponseData<>();
		List<StoryRole> storyRoleList = storyRoleService.getStoryRoleListByStoryId(storyId);
		responseData.jsonFill(1, null, storyRoleList);
		return responseData;
	}

	@ApiOperation(value = "上传音频文件，返回Url", notes = "需登录")
	@RequestMapping(value = "/uploadRoleAudioFile", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<String> uploadRoleAudioFile(@ApiParam("音频文件") @RequestParam MultipartFile uploadFile,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<String> responseData=new ResponseData<>();  
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return responseData;
		}
		if (uploadFile == null) {
			responseData.jsonFill(2, "请选择音频文件上传。", null);
			return responseData;
		}
		String url = uploadFile(uploadFile, user.getId());
		if(url==null){
			responseData.jsonFill(2, "文件上传失败", null);
			return responseData;
		}else {
			responseData.jsonFill(1, null, url);
			return responseData;
		}
	}
	
	@ApiOperation(value = "上传多个音频文件返回UrlList", notes = "需登录")
	@RequestMapping(value = "/uploadRoleAudioFileList", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<List<String>> uploadRoleAudioFileList(@ApiParam("音频文件列表") @RequestParam MultipartFile[] uploadFileList,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<String>> responseData=new ResponseData<>();  
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return responseData;
		}
		if (uploadFileList == null) {
			responseData.jsonFill(2, "请选择音频文件上传。", null);
			return responseData;
		}
		List<String> urlList=new ArrayList<>();
		for (MultipartFile multipartFile : uploadFileList) {
			String url=uploadFile(multipartFile,user.getId());
			if (url==null) {
				responseData.jsonFill(2, "文件上传失败", urlList);
				return responseData;
			}
			urlList.add(url);
		}
		responseData.jsonFill(1, null, urlList);
		return responseData;
	}

	@ApiOperation(value = "添加故事角色音频", notes = "需登录")
	@RequestMapping(value = "/saveStoryRoleAudio", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<StoryRoleAudio> saveStoryRoleAudio(@ApiParam("角色ID") @RequestParam Integer roleId,
			@ApiParam("故事id") @RequestParam Integer storyId, @ApiParam("content") @RequestParam String content,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<StoryRoleAudio> responseData = new ResponseData<>();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
		if (user == null) {
			responseData.jsonFill(2, "请先登录", null);
			response.setStatus(401);
			return responseData;
		}

		StoryRoleAudio storyRoleAudio = new StoryRoleAudio();
		storyRoleAudio.setStoryid(storyId);
		storyRoleAudio.setContent(content);
		storyRoleAudio.setRoleid(roleId);
		storyRoleAudio.setCreatetime(new Date());
		storyRoleAudio.setUpdatetime(new Date());
		storyRoleAudio.setUserid(user.getId());
		storyRoleAudio.setValid(1);
		int res = storyRoleAudioService.saveRoleAudio(storyRoleAudio);
		if (res == 1) {
			responseData.jsonFill(res, null, storyRoleAudio);
			return responseData;
		}
		responseData.jsonFill(2, "更新失败", null);
		return responseData;
	}

	@ApiOperation(value = "删除故事角色的音频(仅删除音频记录，不删除音频文件)")
	@RequestMapping(value = "/deleteStoryRoleAudioById/{id}", method = {RequestMethod.DELETE })
	@ResponseBody
	public ResponseData<Boolean> deleteStoryRoleAudioById(@ApiParam("ID") @RequestParam Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		StoryRoleAudio storyRoleAudio = storyRoleAudioService.selectById(id);
		ResponseData<Boolean> responseData = new ResponseData<>();
		if (storyRoleAudio == null) {
			responseData.jsonFill(2, "你需要删除的音频不存在", null);
		}
		//功能尚未完成，拿contenz字段内容删除
		//UploadFileUtil.deleteFileByUrl(storyRoleAudio.getUrl());

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
			@ApiParam(value="用户ID")@RequestParam Integer userId,
			HttpServletRequest request,
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

	@ApiOperation(value = "根据用户id,角色id下载音频")
	@RequestMapping(value = "/getStoryRoleAudioByUserIdAndRoleId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryRoleAudio>> getStoryRoleAudioByUserIdAndRoleId(
			@ApiParam(value = "用戶id") @RequestParam Integer userId,
			@ApiParam(value = "角色id") @RequestParam Integer roleId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<StoryRoleAudio>> responseData = new ResponseData<>();
		List<StoryRoleAudio> storyRoleAudioList = storyRoleAudioService.getStoryRoleAudioByUserIdAndRoleId(userId, roleId);
		if (storyRoleAudioList == null) {
			responseData.jsonFill(2, "不存在音频", null);
		}
		responseData.jsonFill(1, null, storyRoleAudioList);
		responseData.setCount(storyRoleAudioList.size());
		return responseData;
	}

	@ApiOperation(value = "根据故事id获取官方音频")
	@RequestMapping(value = "/getAdminStoryRoleAudioByStoryId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<StoryRoleAudio>> getStoryRoleAudioByUserIdAndRoleId(
			@ApiParam(value = "故事id") @RequestParam Integer storyId,
			HttpServletResponse response) {
		ResponseData<List<StoryRoleAudio>> responseData = new ResponseData<>();
		List<StoryRoleAudio> list = storyRoleAudioService.getStoryRoleAuioByUserAndStory(2,storyId);
		responseData.jsonFill(1,null, list);
		return responseData;
	}
	/**
	 * 上传作品的音频文件
	 *
	 * @param file
	 * @return
	 */
	private String uploadFile(MultipartFile file, int userId) {
		String realPath = UploadFileUtil.getBaseUrl() + ROLE_ROOT + userId + "/";
		String fileName = RandCharsUtils.getRandomString(16) + "."
				+ UploadFileUtil.getSuffix(file.getOriginalFilename());
		boolean success = UploadFileUtil.mvFile(file, realPath, fileName);
		if (!success) {
			throw new RuntimeException("文件上传失败");
		}
		String url = UploadFileUtil.SOURCE_BASE_URL + ROLE_ROOT + userId + "/" + fileName;
		return url;
	}
}
