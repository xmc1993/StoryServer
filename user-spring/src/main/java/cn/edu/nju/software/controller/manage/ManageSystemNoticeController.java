package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.annotation.RequiredPermissions;
import cn.edu.nju.software.dto.MsgVo;
import cn.edu.nju.software.entity.Admin;
import cn.edu.nju.software.entity.Feed;
import cn.edu.nju.software.entity.SystemNotice;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.feed.MessageType;
import cn.edu.nju.software.feed.service.MessageFeedService;
import cn.edu.nju.software.service.SystemNoticeService;
import cn.edu.nju.software.service.user.UserMessageService;
import cn.edu.nju.software.util.TokenConfig;
import com.google.gson.Gson;
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
public class ManageSystemNoticeController {
    private static final Logger logger = LoggerFactory.getLogger(ManageSystemNoticeController.class);
    @Autowired
    private SystemNoticeService systemNoticeService;
    @Autowired
    private UserMessageService userMessageService;
    @Autowired
    private MessageFeedService messageFeedService;

    @RequiredPermissions({1, 20})
    @ApiOperation(value = "新增系统通知项", notes = "")
    @RequestMapping(value = "/systemNotices", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public SystemNotice publishSystemNotice(
            @ApiParam("系统通知项") @RequestBody SystemNotice systemNotice,
            HttpServletRequest request, HttpServletResponse response) {
        systemNotice.setCreateTime(new Date());
        systemNotice.setUpdateTime(new Date());
        Admin user = (Admin) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        systemNotice = systemNoticeService.saveSystemNotice(systemNotice);
        List<Integer> allUserIdList = userMessageService.getAllUserIdList();

        if (systemNotice != null) {
            MsgVo msgVo = new MsgVo();
            msgVo.setUserId(user.getId());
            msgVo.setHeadImgUrl("");
            msgVo.setUserName(user.getUsername());
            Feed feed = new Feed();
            feed.setCreateTime(new Date());
            feed.setUpdateTime(new Date());
            feed.setFid(user.getId());
            feed.setContent(new Gson().toJson(msgVo));
            feed.setMid(systemNotice.getId());
            feed.setType(MessageType.SYSTEM_NOTICE);
            messageFeedService.feed(feed, allUserIdList);
        }

        return systemNotice;
    }

    @RequiredPermissions({3, 20})
    @ApiOperation(value = "更新系统通知项", notes = "")
    @RequestMapping(value = "/systemNotices/{id}", method = {RequestMethod.PUT})
    @ResponseBody
    public SystemNotice updateSystemNotice(
            @ApiParam("ID") @PathVariable int id,
            @ApiParam("") @RequestBody SystemNotice systemNotice,
            HttpServletRequest request, HttpServletResponse response) {
        systemNotice.setId(id);
        systemNotice.setUpdateTime(new Date());
        return systemNoticeService.updateSystemNotice(systemNotice) ? systemNotice : null;

    }

    @RequiredPermissions({2, 20})
    @ApiOperation(value = "删除系统通知项", notes = "")
    @RequestMapping(value = "/systemNotices/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSystemNotice(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean success = systemNoticeService.deleteSystemNotice(id);
        if (success){
            messageFeedService.unfeed(id, new ArrayList<>());
        }
        if (!success) {
            throw new RuntimeException("删除失败");
        }
    }

    @RequiredPermissions({4, 20})
    @ApiOperation(value = "根据ID获得系统通知项", notes = "")
    @RequestMapping(value = "/systemNotices/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public SystemNotice getStoryById(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        SystemNotice systemNotice = systemNoticeService.getSystemNoticeById(id);
        if (systemNotice == null) {
            throw new RuntimeException("无效的ID");
        } else {
            return systemNotice;
        }
    }

    @RequiredPermissions({4, 20})
    @ApiOperation(value = "分页获得系统通知列表", notes = "")
    @RequestMapping(value = "/getSystemNoticeListByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<SystemNotice>> getSystemNoticeListByPage(
            @ApiParam("PAGE") @RequestParam int page,
            @ApiParam("SIZE") @RequestParam int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<SystemNotice>> responseData = new ResponseData<>();
        List<SystemNotice> systemNoticeList = systemNoticeService.getAllSystemNoticeByPage(page, pageSize);
        responseData.jsonFill(1, null, systemNoticeList);
        return responseData;
    }

}