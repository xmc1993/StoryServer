package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.*;
import cn.edu.nju.software.service.*;
import cn.edu.nju.software.service.user.AppUserService;
import cn.edu.nju.software.service.wxpay.util.RandCharsUtils;
import cn.edu.nju.software.util.TokenConfig;
import cn.edu.nju.software.util.UploadFileUtil;
import cn.edu.nju.software.util.UserChecker;
import cn.edu.nju.software.vo.WorksVo;

import com.github.pagehelper.PageInfo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
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
    @Autowired
    private WorkUserLogService workUserLogService;
    @Autowired
    StoryTagService storyTagService;
    @Autowired
    TagRelationService tagRelationService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    BadgeService badgeService;
    @Autowired
    UserBadgeService userBadgeService;


    @ApiOperation(value = "获得最新的作品列表", notes = "需要登录")
    @RequestMapping(value = "/getLatestWorksByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<WorksVo>> getLatestWorksByPage(
            @ApiParam("页") @RequestParam int page,
            @ApiParam("页大小") @RequestParam int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<WorksVo>> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        List<Works> worksList = worksService.getLatestWorksByPage(page, pageSize);
        responseData.jsonFill(1, null, worksList2VoList(worksList, user.getId()));
        return responseData;
    }

    @ApiOperation(value = "获得最受欢迎的作品列表", notes = "需要登录")
    @RequestMapping(value = "/getMostPopularByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<WorksVo>> getMostPopularByPage(
            @ApiParam("页") @RequestParam int page,
            @ApiParam("页大小") @RequestParam int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<WorksVo>> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        List<Works> worksList = worksService.getMostPopularByPage(page, pageSize);
        responseData.jsonFill(1, null, worksList2VoList(worksList, user.getId()));
        return responseData;
    }


    @ApiOperation(value = "获取某个用户的作品列表", notes = "需要登录")
    @RequestMapping(value = "/getWorksByUserId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<WorksVo>> getWorksById(
            @ApiParam("用户ID") @RequestParam("userId") int userId,
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<WorksVo>> responseData = new ResponseData();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        List<Works> worksList = worksService.getWorksListByUserId(userId, offset, limit);
        responseData.jsonFill(1, null, worksList2VoList(worksList, user.getId()));
        responseData.setCount(worksService.getWorksCountByUserId(userId));
        return responseData;
    }

    private List<WorksVo> worksList2VoList(List<Works> worksList, int userId) {
        List<WorksVo> worksVoList = new ArrayList<>();
        for (Works works : worksList) {
            WorksVo worksVo = new WorksVo();
            BeanUtils.copyProperties(works, worksVo);
            if (agreeService.getAgree(userId, works.getId()) != null) {
                worksVo.setLike(true);
            }
            worksVoList.add(worksVo);
        }
        return worksVoList;
    }
    @ApiOperation(value = "保存Work访问记录", notes = "")
    @RequestMapping(value = "/saveWorkUserLog", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> saveWorkUserLog(
            @ApiParam("workId") @RequestParam("workId") int workId,
            @ApiParam("userID") @RequestParam("userId") int userId,
            @ApiParam("渠道") @RequestParam("channel") String channel,
            HttpServletRequest request,HttpServletResponse response){
        ResponseData<Boolean> result = new ResponseData<>();
        WorkUserLog workUserLog = new WorkUserLog(userId,  workId,  channel, new Date());
        boolean success=workUserLogService.saveWorkUserLog(workUserLog);
        if(success==false){
            result.jsonFill(2,"保存访问记录失败",false);
            return result;
        }
        else {
            result.jsonFill(1,null,true);
            return result;
        }
    }

    @ApiOperation(value = "获取一个故事的所有作品列表(按照点赞数降序)", notes = "需登录")
    @RequestMapping(value = "/getWorksListByStoryId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<WorksVo>> getWorksListByStoryId(
            @ApiParam("故事ID") @RequestParam("storyId") int storyId,
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<WorksVo>> responseData = new ResponseData();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        List<Works> worksList = worksService.getWorksListByStoryId(storyId, offset, limit);
        responseData.jsonFill(1, null, worksList2VoList(worksList, user.getId()));
        responseData.setCount(worksService.getWorksCountByStoryId(storyId));
        return responseData;
    }

    @ApiOperation(value = "获得一个用户所有喜欢的作品列表", notes = "需登录")
    @RequestMapping(value = "/getAgreeWorksListByUserId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<WorksVo>> getWorksListByUserId(
            @ApiParam("用户ID") @RequestParam("userId") int userId,
            @ApiParam("OFFSET") @RequestParam int offset,
            @ApiParam("LIMIT") @RequestParam int limit,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<WorksVo>> responseData = new ResponseData();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        List<Integer> idList = agreeService.getAgreeWorksIdListByUserId(userId, offset, limit);
        List<Works> worksList = worksService.getWorksListByIdList(idList);
        responseData.jsonFill(1, null, worksList2VoList(worksList, user.getId()));
        responseData.setCount(worksService.getWorksCountByIdList(idList));
        return responseData;
    }


    @ApiOperation(value = "根据ID获得一个作品", notes = "需要登录")
    @RequestMapping(value = "/getWorksById", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<WorksVo> getWorksById(
            @ApiParam("作品ID") @RequestParam("id") int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<WorksVo> responseData = new ResponseData();
        Works works = worksService.getWorksById(id);
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        if (works == null) {
            responseData.jsonFill(2, "作品不存在", null);
            return responseData;
        }
        WorksVo worksVo = new WorksVo();
        BeanUtils.copyProperties(works, worksVo);
        if (agreeService.getAgree(user.getId(), works.getId()) != null) {
            worksVo.setLike(true);
        }
        responseData.jsonFill(1, null, worksVo);
        return responseData;
    }


    @ApiOperation(value = "根据ID获得一个分享作品（无需登录）", notes = "")
    @RequestMapping(value = "/getShareWorksById", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Works> getShareWorksById(
            @ApiParam("作品ID") @RequestParam("id") int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Works> responseData = new ResponseData();
        Works works = worksService.getWorksById(id);
        if (works == null) {
            responseData.jsonFill(2, "作品不存在", null);
            return responseData;
        }
        responseData.jsonFill(1, null, works);
        return responseData;
    }

    @ApiOperation(value = "喜欢一个作品", notes = "需登录")
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

    @ApiOperation(value = "取消喜欢一个作品", notes = "需登录")
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


    @ApiOperation(value = "删除自己的某个作品", notes = "需登录")
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

    @ApiOperation(value = "发布作品", notes = "需登录")
    @RequestMapping(value = "/publishWorks", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<WorksVo> publishWorks(
            @ApiParam("故事ID") @RequestParam("storyId") int storyId,
            @ApiParam("音频长度") @RequestParam("duration") String duration,
            @ApiParam("音频文件") @RequestParam(value="uploadFile",required = false) MultipartFile uploadFile,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<WorksVo> responseData = new ResponseData();
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
        works.setDuration(duration);
        works.setCreateTime(new Date());
        works.setUpdateTime(new Date());
        works.setStoryId(storyId);
        works.setStoryTitle(story.getTitle());
        works.setCoverUrl(story.getCoverUrl());
        works.setUserId(user.getId());
        works.setUsername(user.getNickname());
        works.setUrl(url);
        works.setHeadImgUrl(user.getHeadImgUrl());
        boolean res = worksService.saveWorks(works);

        WorksVo worksVo = new WorksVo();
        if (res) {

            //发布成功,用户发布作品数加一
            user.setWorkCount(user.getWorkCount()+1);
            appUserService.updateUserWorkCount(user.getWorkCount(),user.getId());
            Badge badge = judgeUserAddBadge(user);
            if(badge != null){
                BeanUtils.copyProperties(works,worksVo );
                worksVo.setBadge(badge);
            }
            responseData.jsonFill(1, null, worksVo);

        } else {
            responseData.jsonFill(2, "发布失败", null);
        }
        return responseData;

    }

    /**
     * 判断是否需要给用户增加新徽章
     * @param user
     * @return
     */
    private Badge judgeUserAddBadge(User user){
        int[] workCountBadgeArr = {5000,2000,1000,500,300,200,150,100,30,10,3};
        for (int i = 0; i < workCountBadgeArr.length; i++) {
            if(user.getWorkCount() == workCountBadgeArr[i]){
                UserBadge userBadge = new UserBadge();
                userBadge.setUserId(user.getId());
                Badge badge = badgeService.getBadgeByMeasureAndType(workCountBadgeArr[i],5);
                userBadge.setBadgeId(badge.getId());
                userBadgeService.saveUserBadge(userBadge);
                return badge;
            }

        }
        return null;
    }

    @ApiOperation(value = "重新发布作品", notes = "需登录")
    @RequestMapping(value = "/rePublishWorks", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Works> rePublishWorks(
            @ApiParam("作品ID") @RequestParam("worksId") int worksId,
            @ApiParam("音频长度") @RequestParam("duration") String duration,
            @ApiParam("音频文件") @RequestParam("uploadFile") MultipartFile uploadFile,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Works> responseData = new ResponseData();
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

        Works works = worksService.getWorksById(worksId);
        if (works == null) {
            responseData.jsonFill(2, "无效的作品ID", null);
            response.setStatus(404);
            return responseData;
        }
        if (works.getUserId() != user.getId()) {
            responseData.jsonFill(2, "非法请求。", null);
            response.setStatus(401);
            return responseData;
        }

        String url = uploadFile(uploadFile, user.getId());
        if (url == null) {
            responseData.jsonFill(2, "文件上传失败", null);
            return responseData;
        }
        works.setDuration(duration);
        works.setUpdateTime(new Date());
        //删除原有的作品文件
        UploadFileUtil.deleteFileByUrl(works.getUrl());
        works.setUrl(url);
        boolean res = worksService.updateWorks(works);
        if (res) {
            responseData.jsonFill(1, null, works);
        } else {
            responseData.jsonFill(2, "发布失败", null);
        }
        return responseData;
    }

    @ApiOperation(value = "收听作品", notes = "")
    @RequestMapping(value = "/listenWorks", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<Boolean> listenWorks(
            @ApiParam("作品ID") @RequestParam("worksId") int worksId,
            HttpServletRequest request, HttpServletResponse response) throws ParseException {
        ResponseData<Boolean> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        boolean res = worksService.listenWorks(worksId);
        Works works = worksService.getWorksById(worksId);
        if (works != null) {
            WorkUserLog workUserLog = new WorkUserLog();
            workUserLog.setAccessTime(new Date());
            workUserLog.setStoryId(works.getStoryId());
            workUserLog.setWorkId(worksId);
            workUserLog.setUserId(user.getId());
            workUserLogService.saveWorkUserLog(workUserLog);
        }
        responseData.jsonFill(1, null, res);
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
