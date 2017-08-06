package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.StoryRole;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.StoryRoleService;
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
public class ManageStoryRoleController {
    private static final Logger logger = LoggerFactory.getLogger(ManageStoryRoleController.class);
    @Autowired
    private StoryRoleService storyRoleService;

    @ApiOperation(value = "新增故事角色项", notes = "")
    @RequestMapping(value = "/storyRoles", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public StoryRole publishStoryRole(
            @ApiParam("故事角色项") @RequestBody StoryRole storyRole,
            HttpServletRequest request, HttpServletResponse response) {
        storyRole.setCreateTime(new Date());
        storyRole.setUpdateTime(new Date());
        storyRoleService.saveStoryRole(storyRole);
        return storyRole;
    }

    @ApiOperation(value = "更新故事角色项", notes = "")
    @RequestMapping(value = "/storyRoles/{id}", method = {RequestMethod.PUT})
    @ResponseBody
    public StoryRole updateStoryRole(
            @ApiParam("ID") @PathVariable int id,
            @ApiParam("") @RequestBody StoryRole storyRole,
            HttpServletRequest request, HttpServletResponse response) {
        storyRole.setId(id);
        storyRole.setUpdateTime(new Date());
        return storyRoleService.updateStoryRole(storyRole) ? storyRole : null;

    }

    @ApiOperation(value = "删除故事角色项", notes = "")
    @RequestMapping(value = "/storyRoles/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStoryRole(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean success = storyRoleService.deleteStoryRole(id);
        if (!success) {
            throw new RuntimeException("删除失败");
        }
    }


    @ApiOperation(value = "根据ID获得故事角色项", notes = "")
    @RequestMapping(value = "/storyRoles/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public StoryRole getStoryById(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        StoryRole storyRole = storyRoleService.getStoryRoleById(id);
        if (storyRole == null) {
            throw new RuntimeException("无效的ID");
        } else {
            return storyRole;
        }
    }

    @ApiOperation(value = "根据故事角色类型故事角色项列表", notes = "")
    @RequestMapping(value = "/getStoryRoleListByStoryId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<StoryRole>> getStoryRoleListByTypeId(
            @ApiParam("故事ID") @RequestParam int storyId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<StoryRole>> responseData = new ResponseData<>();
        List<StoryRole> storyRoleList = storyRoleService.getStoryRoleListByStoryId(storyId);
        responseData.jsonFill(1, null, storyRoleList);
        return responseData;
    }


}
