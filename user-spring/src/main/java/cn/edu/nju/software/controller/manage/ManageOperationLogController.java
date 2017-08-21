package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.OperationLog;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.OperationLogService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/15.
 */
@Api(value = "Admin", description = "管理接口")
@Controller()
@RequestMapping("/manage")
public class ManageOperationLogController {
    private static final Logger logger = LoggerFactory.getLogger(ManageOperationLogController.class);
    @Autowired
    private OperationLogService operationLogService;

//    @ApiOperation(value = "新增权限码项", notes = "")
//    @RequestMapping(value = "/operationLogs", method = {RequestMethod.POST})
//    @ResponseBody
//    @ResponseStatus(HttpStatus.CREATED)
    public OperationLog publishOperationLog(
            @ApiParam("权限码项") @RequestBody OperationLog operationLog,
            HttpServletRequest request, HttpServletResponse response) {
        operationLog.setCreateTime(new Date());
        operationLog.setUpdateTime(new Date());
        operationLogService.saveOperationLog(operationLog);
        return operationLog;
    }

//    @ApiOperation(value = "删除权限码项", notes = "")
//    @RequestMapping(value = "/operationLogs/{id}", method = {RequestMethod.DELETE})
//    @ResponseBody
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOperationLog(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean success = operationLogService.deleteOperationLog(id);
        if (!success) {
            throw new RuntimeException("删除失败");
        }
    }


//    @ApiOperation(value = "根据ID获得权限码项", notes = "")
//    @RequestMapping(value = "/operationLogs/{id}", method = {RequestMethod.GET})
//    @ResponseBody
    public OperationLog getStoryById(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        OperationLog operationLog = operationLogService.getOperationLogById(id);
        if (operationLog == null) {
            throw new RuntimeException("无效的ID");
        } else {
            return operationLog;
        }
    }

    @ApiOperation(value = "分页获得操作日志", notes = "")
    @RequestMapping(value = "/getOperationLogListByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<OperationLog>> getOperationLogListByPage(
            @ApiParam("页") @RequestParam int page,
            @ApiParam("页大小") @RequestParam int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<OperationLog>> responseData = new ResponseData<>();
        List<OperationLog> operationLogList = operationLogService.getOperationLogListByPage(page, pageSize);
        responseData.jsonFill(1, null, operationLogList);
        return responseData;
    }

}
