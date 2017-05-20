package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.*;
import cn.edu.nju.software.service.AgreeService;
import cn.edu.nju.software.service.CheckValidService;
import cn.edu.nju.software.service.StoryService;
import cn.edu.nju.software.service.WorksService;
import cn.edu.nju.software.service.wxpay.util.RandCharsUtils;
import cn.edu.nju.software.util.TokenConfig;
import cn.edu.nju.software.util.UploadFileUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Api(value = "/work", description = "和作品有关的接口")
@Controller
@RequestMapping("/user")
public class UserWorksController extends BaseController {

    private static final String WORKS_ROOT = "/works/"; //头像的基础路径
    @Autowired
    private WorksService worksService;
    @Autowired
    private AgreeService agreeService;
    @Autowired
    private CheckValidService checkValidService;
    @Autowired
    private StoryService storyService;

    @ApiOperation(value = "获取某个用户的作品列表", notes = "")
    @RequestMapping(value = "/getWorksByUserId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Works>> getWorksById(
            @ApiParam("用户ID") @RequestParam("userId") int userId,
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Works>> responseData = new ResponseData();
        List<Works> worksList = worksService.getWorksListByUserId(userId, offset, limit);
        responseData.jsonFill(1, null, worksList);
        return responseData;
    }

    @ApiOperation(value = "获取一个故事的所有作品列表(按照点赞数降序)", notes = "")
    @RequestMapping(value = "/getWorksListByStoryId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Works>> getWorksListByStoryId(
            @ApiParam("故事ID") @RequestParam("storyId") int storyId,
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Works>> responseData = new ResponseData();
        List<Works> worksList = worksService.getWorksListByStoryId(storyId, offset, limit);
        responseData.jsonFill(1, null, worksList);
        return responseData;
    }

    @ApiOperation(value = "获得一个用户所有喜欢的作品列表", notes = "")
    @RequestMapping(value = "/getAgreeWorksListByUserId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Works>> getWorksListByUserId(
            @ApiParam("用户ID") @RequestParam("userId") int userId,
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Works>> responseData = new ResponseData();

        List<Integer> idList = agreeService.getAgreeWorksIdListByUserId(userId, offset, limit);
        List<Works> worksList = worksService.getWorksListByIdList(idList);
        responseData.jsonFill(1, null, worksList);
        return responseData;
    }

    @ApiOperation(value = "喜欢一个作品", notes = "")
    @RequestMapping(value = "/likeWorks", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> likeWorks(
            @ApiParam("作品ID") @RequestParam("worksId") int worksId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        if (!checkValidService.isWorksExist(worksId)) {
            responseData.jsonFill(2, "作品不存在", null);
            response.setStatus(404);
            return responseData;
        }
        Agree agree = new Agree();
        agree.setCreateTime(new Date());
        agree.setUpdateTime(new Date());
        agree.setWorksId(worksId);
        agree.setUserId(user.getId());
        boolean res = agreeService.addAgree(agree);
        responseData.jsonFill(res ? 1 : 2, null, res);
        return responseData;
    }

    @ApiOperation(value = "取消喜欢一个作品", notes = "")
    @RequestMapping(value = "/unlikeWorks", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> unlikeWorks(
            @ApiParam("作品ID") @RequestParam("worksId") int worksId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        if (!checkValidService.isWorksExist(worksId)) {
            responseData.jsonFill(2, "作品不存在", null);
            response.setStatus(404);
            return responseData;
        }
        boolean res = agreeService.deleteAgree(worksId, user.getId());
        responseData.jsonFill(res ? 1 : 2, null, res);
        return responseData;

    }


    @ApiOperation(value = "删除自己的某个作品", notes = "")
    @RequestMapping(value = "/deleteWorks", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> deleteWorks(
            @ApiParam("作品ID") @RequestParam("worksId") int worksId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }

        Works works = worksService.getWorksById(worksId);
        if (works == null) {
            responseData.jsonFill(2, "作品不存在", null);
            response.setStatus(404);
            return responseData;
        }
        if (works.getUserId() != user.getId()) {
            responseData.jsonFill(2, "无效的请求。", null);
            response.setStatus(404);
            return responseData;
        }
        boolean res = worksService.deleteWorksById(worksId);
        responseData.jsonFill(res ? 1 : 2, null, res);
        return responseData;
    }

    @ApiOperation(value = "发布作品", notes = "")
    @RequestMapping(value = "/publishWorks", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> publishWorks(
            @ApiParam("故事ID") @RequestParam("storyId") int storyId,
            @ApiParam("音频文件") @RequestParam("uploadFile") MultipartFile uploadFile,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        Story story = storyService.getStoryById(storyId);
        if (story == null) {
            responseData.jsonFill(2, "无效的故事ID", null);
            response.setStatus(404);
            return responseData;
        }

        String url = uploadFile(uploadFile, user.getId());
        if (url == null) {
            responseData.jsonFill(2, "文件上传失败", null);
            return responseData;
        }
        Works works = new Works();
        works.setCreateTime(new Date());
        works.setUpdateTime(new Date());
        works.setStoryId(storyId);
        works.setStoryTitle(story.getTitle());
        works.setUserId(user.getId());
        works.setUsername(user.getNickname());
        works.setUrl(url);
        //TODO url
        boolean res = worksService.saveWorks(works);
        responseData.jsonFill(res ? 1 : 2, null, res);
        return responseData;
    }


    /**
     * 上传作品的音频文件
     *
     * @param file
     * @return
     */
    private String uploadFile(MultipartFile file, int userId) {
        String realPath = UploadFileUtil.getBaseUrl() + WORKS_ROOT + userId + "/";
        String fileName = RandCharsUtils.getRandomString(16) + "." + UploadFileUtil.getSuffix(file.getOriginalFilename());
        boolean success = UploadFileUtil.mvFile(file, realPath, fileName);
        if (!success) {
            throw new RuntimeException("文件上传失败");
        }
        String url = UploadFileUtil.SOURCE_BASE_URL + WORKS_ROOT + userId + "/" + fileName;
        return url;
    }

}
