package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.annotation.RequiredPermissions;
import cn.edu.nju.software.entity.AdminPower;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.AdminPowerService;
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
import java.util.Date;
import java.util.List;

/**
 * Created by xmc1993 on 2017/5/15.
 */
@Api(value = "Admin", description = "管理接口")
@Controller()
@RequestMapping("/manage")
public class ManageAdminPowerController {
    private static final Logger logger = LoggerFactory.getLogger(ManageAdminPowerController.class);
    @Autowired
    private AdminPowerService adminPowerService;

    @RequiredPermissions({4,15})
    @ApiOperation(value = "新增后台用户权限项", notes = "")
    @RequestMapping(value = "/adminPowers", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public AdminPower publishAdminPower(
            @ApiParam("后台用户权限项") @RequestBody AdminPower adminPower,
            HttpServletRequest request, HttpServletResponse response) {
        adminPower.setCreateTime(new Date());
        adminPower.setUpdateTime(new Date());
        adminPowerService.saveAdminPower(adminPower);
        return adminPower;
    }

    @RequiredPermissions({2,15})
    @ApiOperation(value = "删除后台用户权限项", notes = "")
    @RequestMapping(value = "/adminPowers/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdminPower(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean success = adminPowerService.deleteAdminPower(id);
        if (!success) {
            throw new RuntimeException("删除失败");
        }
    }

    @RequiredPermissions({2,15})
    @ApiOperation(value = "删除后台用户权限项", notes = "")
    @RequestMapping(value = "/deleteAdminPower", method = {RequestMethod.POST})
    @ResponseBody
    public void deleteAdminPowerWithPrimaryKey(
            @ApiParam("后台用户id") @PathVariable int adminId,
            @ApiParam("权限code") @PathVariable int code,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean success = adminPowerService.deleteAdminPowerWithPrimaryKey(adminId, code);
        if (!success) {
            throw new RuntimeException("删除失败");
        }
    }

    @RequiredPermissions({4,15})
    @ApiOperation(value = "根据ID获得后台用户权限项", notes = "")
    @RequestMapping(value = "/adminPowers/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public AdminPower getStoryById(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        AdminPower adminPower = adminPowerService.getAdminPowerById(id);
        if (adminPower == null) {
            throw new RuntimeException("无效的ID");
        } else {
            return adminPower;
        }
    }


    @RequiredPermissions({4,15})
    @ApiOperation(value = "分页获得后台用户权限", notes = "")
    @RequestMapping(value = "/getAdminPowerListByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<AdminPower>> getAdminPowerListByPage(
            @ApiParam("后台用户id") @RequestParam int id,
            @ApiParam("PAGE") @RequestParam int page,
            @ApiParam("SIZE") @RequestParam int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<AdminPower>> responseData = new ResponseData<>();
        List<AdminPower> adminPowerList = adminPowerService.getAdminPowerListByAdminId(id,page,pageSize);
        responseData.jsonFill(1, null, adminPowerList);
        return responseData;
    }
}
