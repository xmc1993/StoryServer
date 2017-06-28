package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.StoryUserLogService;
import cn.edu.nju.software.vo.StoryUserLogVo;
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
 * Created by Kt on 2017/6/27.
 */
@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageLogController {
    @Autowired
    private StoryUserLogService storyUserLogService;
    @ApiOperation("通过用户ID获取浏览过的故事列表时间倒序")
    @RequestMapping(value = "storyLogListByUserId",method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Story>> getStoryListByUserId(@ApiParam("用户ID") @RequestParam("userId") int userId,
                                                          @ApiParam("offset") @RequestParam("offset") int offset,
                                                          @ApiParam("limit") @RequestParam("limit") int limit,
                                                          HttpServletRequest request, HttpServletResponse response){
        ResponseData<List<Story>> result = new ResponseData<>();
        List<Story> storyList = storyUserLogService.getStoryListByUserIdTimeDesc(userId,offset,limit);
        result.jsonFill(1,null,storyList);
        result.setCount(storyUserLogService.getStoryCountByUserId(userId));
        return result;
    }
    @ApiOperation("通过故事ID获取浏览过的用户列表时间倒序")
    @RequestMapping(value = "userLogListByStoryId",method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<User>> getUserListByStoryId(@ApiParam("故事ID") @RequestParam("storyId") int storyId,
                                                         @ApiParam("offset") @RequestParam("offset") int offset,
                                                         @ApiParam("limit") @RequestParam("limit") int limit,
                                                         HttpServletRequest request, HttpServletResponse response){
        ResponseData<List<User>> result = new ResponseData<>();
        List<User> userList = storyUserLogService.getUserListByStoryIdTimeDesc(storyId,offset,limit);
        result.jsonFill(1,null,userList);
        result.setCount(storyUserLogService.getUserCountByStoryID(storyId));
        return result;
    }
    //@ApiOperation("获取访问记录包含Log、Story、User信息")
    @RequestMapping(value = "storyUserLogVoByPage",method={RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<StoryUserLogVo>> getStoryUserLogVoByPage(@ApiParam("offset") @RequestParam("offset") int offset,
                                                                      @ApiParam("limit") @RequestParam("limit") int limit,
                                                                      HttpServletRequest request, HttpServletResponse response){
        ResponseData<List<StoryUserLogVo>> result = new ResponseData<>();
        List<StoryUserLogVo> list = storyUserLogService.getStoryUserLogVoByPageTimeDesc(offset,limit);
        result.jsonFill(1,null,list);
        return result;
    }
}
