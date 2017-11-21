package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.annotation.RequiredPermissions;
import cn.edu.nju.software.entity.OpinionFeedback;
import cn.edu.nju.software.entity.ReadingPlan;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.OpinionFeedbackservice;
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
import java.util.List;

/**
 * Created by zhangsong on 2017/11/20.
 */
@Api(value = "Admin", description = "管理接口")
@Controller()
@RequestMapping("/manage")
public class ManageOpinionController {

    @Autowired
    OpinionFeedbackservice opinionFeedbackservice;

    @ApiOperation(value = "分页获取所有的意见")
    @RequestMapping(value = "/selectAllOpinion", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<OpinionFeedback>> selectAllReadPlan(@ApiParam("page") @RequestParam Integer page,
                                                                 @ApiParam("pageSize") @RequestParam Integer pageSize, HttpServletRequest request,
                                                                 HttpServletResponse response) {
        ResponseData<List<OpinionFeedback>> responseData = new ResponseData<>();
        responseData = opinionFeedbackservice.getOpinionsByPage(page, pageSize);
        return responseData;
    }
}
