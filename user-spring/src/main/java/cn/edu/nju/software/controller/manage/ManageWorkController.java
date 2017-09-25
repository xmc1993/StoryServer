package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.Works;
import cn.edu.nju.software.service.WorksService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 管理作品接口
 *
 * @author liuyu
 * @create 2017-08-30 上午10:43
 */
@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageWorkController {
	@Autowired
	private WorksService worksService;

	@ApiOperation(value = "根据id获取故事", notes = "")
	@RequestMapping(value = "/getWorkById", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<Works> getWorkById(@ApiParam("故事标题") @RequestParam(value = "id") Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Works> responseData = new ResponseData<>();
		Works works = worksService.getWorksById(id);
		String errorMessage = null;
		if (works == null) {
			errorMessage = "专辑不存在";
		}
		responseData.jsonFill(1, errorMessage, works);
		return responseData;
	}

	@ApiOperation(value = "根据用户id分页获取用户的所有作品", notes = "")
	@RequestMapping(value = "/getWorksListByUserId", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseData<List<Works>> getWorksListByUserId(@ApiParam("用戶id") @RequestParam(value = "id") Integer id,
			@ApiParam("PAGE") @RequestParam int page, @ApiParam("SIZE") @RequestParam int pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<List<Works>> responseData = new ResponseData<>();
		List<Works> worksList = worksService.getWorksListByUserId(id, page, pageSize);
		String errorMessage = null;
		if (worksList == null) {
			errorMessage = "用户无作品或者用户不存在";
		}
		responseData.jsonFill(1, errorMessage, worksList);
		responseData.setCount(worksService.getWorksCountByUserId(id));
		return responseData;
	}

	@ApiOperation(value = "根据作品id删除作品", notes = "")
	@RequestMapping(value = "/deleteWorksById/{id}", method = { RequestMethod.DELETE })
	@ResponseBody
	public ResponseData<Boolean> deleteWorksById(@ApiParam("worksId") @PathVariable int id, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData();
		boolean res = worksService.deleteWorksById(id);
		responseData.jsonFill(res ? 1 : 2, null, res);
		if (!res) {
			throw new RuntimeException("删除失败");
		}
		return responseData;
	}

	@ApiOperation(value = "根据作品id修改作品信息", notes = "")
	@RequestMapping(value = "/updateWorksById", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseData<Boolean> updateWorksById(@ApiParam("作品id") @RequestParam(value = "id") Integer id,
			@ApiParam("故事标题") @RequestParam(value = "storyTitle", required = false) String storyTitle,
			@ApiParam("音频url") @RequestParam(value = "audioFile", required = false) String url,
			@ApiParam("故事标题图url") @RequestParam(value = "headImgUrl", required = false) String headImgUrl,
			@ApiParam("封面图url") @RequestParam(value = "coverUrl", required = false) String coverUrl,
			@ApiParam("音频时长") @RequestParam(value = "duration", required = false) String duration,
			@ApiParam("浏览次数") @RequestParam(value = "reviewCount", required = false) Integer reviewCount,
			@ApiParam("收听数") @RequestParam(value = "listenCount", required = false) Integer listenCount,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData<Boolean> responseData = new ResponseData<>();

		Works works = worksService.getWorksById(id);
		if (works == null) {
			responseData.jsonFill(2, "作品不存在", false);
			return responseData;
		}

		if (headImgUrl != null) {
			works.setHeadImgUrl(headImgUrl);
		}
		if (coverUrl != null) {
			works.setCoverUrl(coverUrl);
		}
		if (url != null) {
			works.setUrl(url);
		}
		if (duration != null) {
			works.setDuration(duration);
		}
		if (storyTitle != null)
			works.setStoryTitle(storyTitle);
		if (reviewCount != null)
			works.setReviewCount(reviewCount);
		if (listenCount != null)
			works.setListenCount(listenCount);
		boolean res = worksService.updateWorks(works);
		responseData.jsonFill(res ? 1 : 2, null, res);
		return responseData;
	}

	// 根据用户id添加作品
	@ApiOperation(value = "根据用户id为用户添加作品", notes = "")
	@RequestMapping(value = "/saveWorksByUserId", method = { RequestMethod.POST })
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResponseData<Works> saveWorksByUserId(@ApiParam("用户id") @RequestParam(value = "userId") Integer userId,
			@ApiParam("故事id") @RequestParam(value = "storyId") Integer storyId,
			@ApiParam("故事标题") @RequestParam(value = "storyTitle") String storyTitle,
			@ApiParam("用户名") @RequestParam(value = "username") String username,
			@ApiParam("音频时长") @RequestParam(value = "duration") String duration,
			@ApiParam("音频url") @RequestParam(value = "url") String url,
			@ApiParam("故事标题图url") @RequestParam(value = "headImgUrl") String headImgUrl,
			@ApiParam("封面图") @RequestParam(value = "coverUrl") String coverUrl,
			@ApiParam("浏览次数") @RequestParam(value = "reviewCount", required = false) Integer reviewCount,
			@ApiParam("收听数") @RequestParam(value = "listenCount", required = false) Integer listenCount,
			HttpServletRequest request, HttpServletResponse response) throws ParseException {
		Works works = new Works();
		ResponseData<Works> responseData = new ResponseData<>();

		works.setHeadImgUrl(headImgUrl);
		works.setCoverUrl(coverUrl);
		works.setUrl(url);
		works.setDuration(duration);

		works.setUserId(userId);
		works.setStoryId(storyId);
		works.setStoryTitle(storyTitle);
		works.setUsername(username);
		works.setCreateTime(new Date());
		works.setUpdateTime(new Date());
		if (reviewCount != null)
			works.setReviewCount(reviewCount);
		if (listenCount != null)
			works.setListenCount(listenCount);
		Works res = worksService.saveWorks(works);
		if (res == null) {
			responseData.jsonFill(2, "添加失败", null);
			return responseData;
		}
		responseData.jsonFill(1, null, res);
		return responseData;
	}
}
