package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.dto.DestinationVo;
import cn.edu.nju.software.entity.FeedbackTemplet;
import cn.edu.nju.software.entity.OpinionFeedback;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.FeedbackTempletService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangsong on 2017/12/24.
 */

@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageFeedbackTempletController {
    @Autowired
    FeedbackTempletService feedbackTempletService;


    @ApiOperation(value = "分页获取所有的反馈模板")
    @RequestMapping(value = "/selectFeedbackTemplet", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<FeedbackTemplet>> selectFeedbackTemplet(@ApiParam("page") @RequestParam Integer page,
                                                                 @ApiParam("pageSize") @RequestParam Integer pageSize, HttpServletRequest request,
                                                                 HttpServletResponse response) {
        ResponseData<List<FeedbackTemplet>> responseData = new ResponseData<>();
        responseData = feedbackTempletService.getAllFeedbackTemplet(page, pageSize);
        return responseData;
    }

    @ApiOperation(value = "根据id获取反馈模板")
    @RequestMapping(value = "/selectFeedbackTempletById", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<FeedbackTemplet> selectFeedbackTempletById(@ApiParam("id") @RequestParam Integer id, HttpServletRequest request,
                                                                   HttpServletResponse response) {
        ResponseData<FeedbackTemplet> responseData = new ResponseData<>();
        responseData.jsonFill(1, null, feedbackTempletService.getFeedbackTempletById(id));
        return responseData;
    }

    @ApiOperation(value = "根据id删除反馈模板")
    @RequestMapping(value = "/deleteFeedbackTempletById", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> deleteFeedbackTempletById(@ApiParam("id") @RequestParam Integer id, HttpServletRequest request,
                                                           HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        int res = feedbackTempletService.deleteFeedbackTemplet(id);
        if (res == 1) {
            responseData.jsonFill(1, null, true);
            return responseData;
        }
        responseData.jsonFill(2, "删除失败", false);
        return responseData;
    }


    @ApiOperation(value = "保存反馈模板")
    @RequestMapping(value = "/saveFeedbackTemplet", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> saveFeedbackTempletById(@ApiParam("content") @RequestParam(value = "content") String content,
                                                           @ApiParam("description") @RequestParam(value = "description") String description) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        FeedbackTemplet feedbackTemplet=new FeedbackTemplet();
        feedbackTemplet.setCreateTime(new Date());
        feedbackTemplet.setUpdateTime(new Date());
        feedbackTemplet.setContent(content);
        feedbackTemplet.setDescription(description);
        int res=feedbackTempletService.saveFeedbackTemplet(feedbackTemplet);
        if (res == 1) {
            responseData.jsonFill(1, null, true);
            return responseData;
        }
        responseData.jsonFill(2, "保存失败", false);
        return responseData;
    }

    @ApiOperation(value = "更新反馈模板")
    @RequestMapping(value = "/updateFeedbackTemplet", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> updateFeedbackTemplet(@ApiParam("id") @RequestParam Integer id,
                                                       @ApiParam("content") @RequestParam(value = "content", required = false) String content,
                                                       @ApiParam("description") @RequestParam(value = "description", required = false) String description,
                                                       HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        FeedbackTemplet feedbackTemplet = feedbackTempletService.getFeedbackTempletById(id);
        if (content != null) {
            feedbackTemplet.setContent(content);
        }
        if (description!=null){
            feedbackTemplet.setDescription(description);
        }
        int res=feedbackTempletService.updateFeedbackTemplet(feedbackTemplet);
        if (res==1){
            responseData.jsonFill(1,null,true);
            return responseData;
        }
        responseData.jsonFill(2,"更新失败",false);
        return  responseData;
    }


    @ApiOperation(value = "获取所有反馈模板的描述内容和id")
    @RequestMapping(value = "/getAllFeedbackDescription", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<DestinationVo>> getAllFeedbackDescription() {
        ResponseData<List<DestinationVo>> responseData = new ResponseData<>();
        List<DestinationVo> list=feedbackTempletService.getAllFeedbackDescription();
        responseData.jsonFill(1, null,list);
        return responseData;
    }
}
