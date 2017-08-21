package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.annotation.RequiredPermissions;
import cn.edu.nju.software.entity.PowerCode;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.PowerCodeService;
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
public class ManagePowerCodeController {
    private static final Logger logger = LoggerFactory.getLogger(ManagePowerCodeController.class);
    @Autowired
    private PowerCodeService powerCodeService;

    @RequiredPermissions({1,15})
    @ApiOperation(value = "新增权限码项", notes = "")
    @RequestMapping(value = "/powerCodes", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public PowerCode publishPowerCode(
            @ApiParam("权限码项") @RequestBody PowerCode powerCode,
            HttpServletRequest request, HttpServletResponse response) {
        powerCode.setCreateTime(new Date());
        powerCode.setUpdateTime(new Date());
        powerCodeService.savePowerCode(powerCode);
        return powerCode;
    }

    @RequiredPermissions({3,15})
    @ApiOperation(value = "更新权限码项", notes = "")
    @RequestMapping(value = "/powerCodes/{id}", method = {RequestMethod.PUT})
    @ResponseBody
    public PowerCode updatePowerCode(
            @ApiParam("ID") @PathVariable int id,
            @ApiParam("") @RequestBody PowerCode powerCode,
            HttpServletRequest request, HttpServletResponse response) {
        powerCode.setId(id);
        powerCode.setUpdateTime(new Date());
        return powerCodeService.updatePowerCode(powerCode) ? powerCode : null;

    }

    @RequiredPermissions({2,15})
    @ApiOperation(value = "删除权限码项", notes = "")
    @RequestMapping(value = "/powerCodes/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePowerCode(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean success = powerCodeService.deletePowerCode(id);
        if (!success) {
            throw new RuntimeException("删除失败");
        }
    }


    @RequiredPermissions({4,15})
    @ApiOperation(value = "根据ID获得权限码项", notes = "")
    @RequestMapping(value = "/powerCodes/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public PowerCode getStoryById(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        PowerCode powerCode = powerCodeService.getPowerCodeById(id);
        if (powerCode == null) {
            throw new RuntimeException("无效的ID");
        } else {
            return powerCode;
        }
    }

    @RequiredPermissions({4,15})
    @ApiOperation(value = "分页获得权限码", notes = "")
    @RequestMapping(value = "/getPowerCodeListByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<PowerCode>> getPowerCodeListByPage(
            @ApiParam("页") @RequestParam int page,
            @ApiParam("页大小") @RequestParam int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<PowerCode>> responseData = new ResponseData<>();
        List<PowerCode> powerCodeList = powerCodeService.getPowerCodeListByPage(page, pageSize);
        responseData.jsonFill(1, null, powerCodeList);
        return responseData;
    }

}
