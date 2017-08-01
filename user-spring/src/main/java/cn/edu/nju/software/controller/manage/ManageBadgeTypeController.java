package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.BadgeType;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.BadgeTypeService;
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
public class ManageBadgeTypeController {
    private static final Logger logger = LoggerFactory.getLogger(ManageBadgeTypeController.class);
    @Autowired
    private BadgeTypeService badgeTypeService;

    @ApiOperation(value = "新增徽章类型项", notes = "")
    @RequestMapping(value = "/badgeTypes", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public BadgeType publishBadgeType(
            @ApiParam("徽章类型项") @RequestBody BadgeType badgeType,
            HttpServletRequest request, HttpServletResponse response) {
        badgeType.setCreateTime(new Date());
        badgeType.setUpdateTime(new Date());
        badgeTypeService.saveBadgeType(badgeType);
        return badgeType;
    }

    @ApiOperation(value = "更新徽章类型项", notes = "")
    @RequestMapping(value = "/badgeTypes/{id}", method = {RequestMethod.PUT})
    @ResponseBody
    public BadgeType updateBadgeType(
            @ApiParam("ID") @PathVariable int id,
            @ApiParam("") @RequestBody BadgeType badgeType,
            HttpServletRequest request, HttpServletResponse response) {
        badgeType.setId(id);
        badgeType.setUpdateTime(new Date());
        return badgeTypeService.updateBadgeType(badgeType) ? badgeType : null;

    }

    @ApiOperation(value = "删除徽章类型项", notes = "")
    @RequestMapping(value = "/badgeTypes/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBadgeType(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean success = badgeTypeService.deleteBadgeType(id);
        if (!success) {
            throw new RuntimeException("删除失败");
        }
    }


    @ApiOperation(value = "根据ID获得徽章类型项", notes = "")
    @RequestMapping(value = "/badgeTypes/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public BadgeType getStoryById(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        BadgeType badgeType = badgeTypeService.getBadgeTypeById(id);
        if (badgeType == null) {
            throw new RuntimeException("无效的ID");
        } else {
            return badgeType;
        }
    }

    @ApiOperation(value = "根据徽章类型徽章类型项列表", notes = "")
    @RequestMapping(value = "/getBadgeTypeListByTypeId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<BadgeType>> getBadgeTypeListByPage(
            @ApiParam("PAGE") @RequestParam int page,
            @ApiParam("SIZE") @RequestParam int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<BadgeType>> responseData = new ResponseData<>();
        List<BadgeType> badgeTypeList = badgeTypeService.getAllBadgeTypeByPage(page, pageSize);
        responseData.jsonFill(1, null, badgeTypeList);
        return responseData;
    }


}
