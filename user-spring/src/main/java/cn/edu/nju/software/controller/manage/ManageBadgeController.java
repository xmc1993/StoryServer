package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.Badge;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.BadgeService;
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
public class ManageBadgeController {
    private static final Logger logger = LoggerFactory.getLogger(ManageBadgeController.class);
    @Autowired
    private BadgeService badgeService;

    @ApiOperation(value = "新增徽章项", notes = "")
    @RequestMapping(value = "/badges", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Badge publishBadge(
            @ApiParam("徽章项") @RequestBody Badge badge,
            HttpServletRequest request, HttpServletResponse response) {
        badge.setCreateTime(new Date());
        badge.setUpdateTime(new Date());
        badgeService.saveBadge(badge);
        return badge;
    }

    @ApiOperation(value = "更新徽章项", notes = "")
    @RequestMapping(value = "/badges/{id}", method = {RequestMethod.PUT})
    @ResponseBody
    public Badge updateBadge(
            @ApiParam("ID") @PathVariable int id,
            @ApiParam("") @RequestBody Badge badge,
            HttpServletRequest request, HttpServletResponse response) {
        badge.setId(id);
        badge.setUpdateTime(new Date());
        return badgeService.updateBadge(badge) ? badge : null;

    }

    @ApiOperation(value = "删除徽章项", notes = "")
    @RequestMapping(value = "/badges/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBadge(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean success = badgeService.deleteBadge(id);
        if (!success) {
            throw new RuntimeException("删除失败");
        }
    }


    @ApiOperation(value = "根据ID获得徽章项", notes = "")
    @RequestMapping(value = "/badges/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public Badge getStoryById(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        Badge badge = badgeService.getBadgeById(id);
        if (badge == null) {
            throw new RuntimeException("无效的ID");
        } else {
            return badge;
        }
    }

    @ApiOperation(value = "根据徽章类型徽章项列表", notes = "")
    @RequestMapping(value = "/getBadgeListByTypeId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Badge>> getBadgeListByTypeId(
            @ApiParam("徽章类型ID") @RequestParam int typeId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Badge>> responseData = new ResponseData<>();
        List<Badge> badgeList = badgeService.getBadgeListByTypeId(typeId);
        responseData.jsonFill(1, null, badgeList);
        return responseData;
    }

}
