package cn.edu.nju.software.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.StoryRole;
import cn.edu.nju.software.service.StoryRoleService;

@Api("用户故事角色控制器")
@Controller
@RequestMapping("/user")
public class UserStoryRoleController {
	@Autowired
	private StoryRoleService storyRoleService;
	
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
