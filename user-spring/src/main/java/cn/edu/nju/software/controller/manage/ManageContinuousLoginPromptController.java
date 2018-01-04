package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ContinuousLoginPrompt;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.user.ContinuousLoginPromptService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zj
 */
@Api("manage controller")
@Controller
@RequestMapping("/manage/continuousLoginPrompt")
public class ManageContinuousLoginPromptController {
    private static final Logger logger = LoggerFactory.getLogger(ManageContinuousLoginPromptController.class);
    @Autowired
    private ContinuousLoginPromptService continuousLoginPromptService;

    @ApiOperation(value = "添加连续登录提示", notes = "")
    @RequestMapping(value = "/addPrompt", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<ContinuousLoginPrompt> addPrompt(
            @ApiParam("提示语") @RequestParam("prompt") String prompt,
            @ApiParam("开始时间") @RequestParam("startTime") Integer startTime,
            @ApiParam("结束时间") @RequestParam("endTime") Integer endTime) {

        ContinuousLoginPrompt continuousLoginPrompt = new ContinuousLoginPrompt();
        continuousLoginPrompt.setPrompt(prompt);
        continuousLoginPrompt.setPromptStartTime(startTime);
        if (endTime == 0) {
            continuousLoginPrompt.setPromptEndTime(24);
        } else {
            continuousLoginPrompt.setPromptEndTime(endTime);
        }
        int res = continuousLoginPromptService.insertContinuousLoginPrompt(continuousLoginPrompt);

        ResponseData<ContinuousLoginPrompt> responseData = new ResponseData<>();
        if (res != 1) {
            responseData.jsonFill(2, "添加失败，服务器内部错误", null);
            return responseData;
        }
        if (endTime == 0) {
            continuousLoginPrompt.setPromptEndTime(0);
        }
        responseData.jsonFill(1, null, continuousLoginPrompt);
        return responseData;
    }

    @ApiOperation(value = "更新连续登录提示", notes = "")
    @RequestMapping(value = "/updatePrompt", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<ContinuousLoginPrompt> updatePrompt(
            @ApiParam("ID") @RequestParam("id") Integer id,
            @ApiParam("提示语") @RequestParam(value = "prompt", required = false) String prompt,
            @ApiParam("开始时间") @RequestParam(value = "startTime", required = false) Integer startTime,
            @ApiParam("结束时间") @RequestParam(value = "endTime", required = false) Integer endTime) {

        ContinuousLoginPrompt continuousLoginPrompt = new ContinuousLoginPrompt();
        continuousLoginPrompt.setId(id);
        continuousLoginPrompt.setPrompt(prompt);
        continuousLoginPrompt.setPromptStartTime(startTime);
        continuousLoginPrompt.setPromptEndTime(endTime);
        //判断顺序不能更改，否则可能空指针异常
        if (endTime != null && endTime == 0) {
            continuousLoginPrompt.setPromptEndTime(24);
        } else {
            continuousLoginPrompt.setPromptEndTime(endTime);
        }
        int res = continuousLoginPromptService.updateByPrimaryKey(continuousLoginPrompt);

        ResponseData<ContinuousLoginPrompt> responseData = new ResponseData<>();
        if (res != 1) {
            responseData.jsonFill(2, "更新失败，服务器内部错误", null);
            return responseData;
        }
        //判断顺序不能更改，否则可能空指针异常
        if (endTime != null && endTime == 0) {
            continuousLoginPrompt.setPromptEndTime(0);
        }
        responseData.jsonFill(1, null, continuousLoginPrompt);
        return responseData;
    }

    @ApiOperation(value = "删除一条连续登录提示", notes = "")
    @RequestMapping(value = "/deletePrompt/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    public void deletePrompt(@ApiParam("ID") @PathVariable Integer id) {
        int res = continuousLoginPromptService.deleteByPrimaryKey(id);
        if (res != 1) {
            throw new RuntimeException("删除失败");
        }
        System.out.println("删除成功");
    }

    @ApiOperation(value = "查询所有连续登录提示", notes = "")
    @RequestMapping(value = "/seleteAllPrompt", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<ContinuousLoginPrompt>> seleteAllPrompt() {
        List<ContinuousLoginPrompt> list = continuousLoginPromptService.seleteAllPrompt();
        ResponseData<List<ContinuousLoginPrompt>> responseData = new ResponseData<>();
        responseData.jsonFill(1, null, list);
        return responseData;
    }
}
