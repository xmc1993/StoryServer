package cn.edu.nju.software.controller.user;

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

import com.google.gson.Gson;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.dto.MsgVo;
import cn.edu.nju.software.entity.BabyRead;
import cn.edu.nju.software.entity.BabyReadInfo;
import cn.edu.nju.software.entity.Feed;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.entity.feed.MessageType;
import cn.edu.nju.software.feed.service.MessageFeedService;
import cn.edu.nju.software.service.BabyReadService;
import cn.edu.nju.software.service.wxpay.util.RandCharsUtils;
import cn.edu.nju.software.util.TokenConfig;
import cn.edu.nju.software.util.UploadFileUtil;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年9月23日 
*/

@Api("baby controller")
@Controller
@RequestMapping("/user")
public class UserBabyReadController extends BaseController {
	
	//宝宝读的基础路径
	private static final String READ_ROOT = "/babyRead/";
	
	@Autowired
	BabyReadService babyReadService;
	@Autowired
	MessageFeedService messageFeedService; 
	
	@ApiOperation(value = "根据宝宝读的Id获取宝宝读内容", notes = "")
	@RequestMapping(value = "/getBabyReadInfotById", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<BabyReadInfo> getBabyReadtById(@ApiParam("id") @RequestParam Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<BabyReadInfo> responseData = new ResponseData<>();
		BabyReadInfo babyReadInfo = babyReadService.selectReadInfoById(id);
		responseData.jsonFill(1, null, babyReadInfo);
		return responseData;
	}
	
	@ApiOperation(value = "根据故事Id获取宝宝读内容", notes = "")
	@RequestMapping(value = "/getBabyReadInfotByStoryId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<BabyReadInfo>> getBabyReadInfotByStoryId(@ApiParam("storyId") @RequestParam Integer storyId,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<BabyReadInfo>> responseData = new ResponseData<>();
		List<BabyReadInfo> babyReadInfo = babyReadService.selectReadInfoByStoryId(storyId);
		responseData.jsonFill(1, null, babyReadInfo);
		return responseData;
	}
	
	@ApiOperation(value = "添加宝宝读的音频", notes = "需登录")
	@RequestMapping(value = "/saveBabyRead", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<BabyRead> saveBabyRead(@ApiParam("babyReadInfoId") @RequestParam Integer babyReadInfoId,
			@ApiParam("宝贝Id") @RequestParam Integer babyId,
			@ApiParam("音频Url") @RequestParam String url,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<BabyRead> responseData = new ResponseData<>();
		User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
		BabyRead babyRead=new BabyRead();
		babyRead.setValid(1);
		babyRead.setBabyreadinfoid(babyReadInfoId);
		babyRead.setCreattime(new Date());
		babyRead.setUpdatetime(new Date());
		babyRead.setUrl(url);
		babyRead.setBabyid(babyId);
		BabyRead res =babyReadService.saveBabyRead(babyRead);
		if(res!=null){
			  	MsgVo msgVo = new MsgVo();
	            msgVo.setUserId(user.getId());
	            msgVo.setHeadImgUrl(user.getHeadImgUrl());
	            msgVo.setUserName(user.getNickname());
	            Feed feed = new Feed();
	            feed.setCreateTime(new Date());
	            feed.setUpdateTime(new Date());
	            feed.setFid(user.getId());
	            feed.setContent(new Gson().toJson(msgVo));
	            feed.setMid(res.getId());
	            feed.setType(MessageType.NEW_BABYREAD);
	            messageFeedService.feedFollowers(feed, user.getId(), false);
		}
		responseData.jsonFill(1, null, babyRead);
		return responseData;
	}
	
	@ApiOperation(value = "上传音频文件，返回Url", notes = "需登录")
	@RequestMapping(value = "/uploadBabyReadAudioFile", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<String> uploadBabyReadAudioFile(@ApiParam("音频文件") @RequestParam MultipartFile uploadFile,
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
	/**
	 * 上传作品的音频文件
	 *
	 * @param file
	 * @return
	 */
	private String uploadFile(MultipartFile file, int userId) {
		String realPath = UploadFileUtil.getBaseUrl() + READ_ROOT + userId + "/";
		String fileName = RandCharsUtils.getRandomString(16) + "."
				+ UploadFileUtil.getSuffix(file.getOriginalFilename());
		boolean success = UploadFileUtil.mvFile(file, realPath, fileName);
		if (!success) {
			throw new RuntimeException("文件上传失败");
		}
		String url = UploadFileUtil.SOURCE_BASE_URL + READ_ROOT + userId + "/" + fileName;
		return url;
	}
}
