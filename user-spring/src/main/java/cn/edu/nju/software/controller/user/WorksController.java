package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.Works;
import cn.edu.nju.software.service.WorksService;
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
 * Created by xmc1993 on 2017/5/12.
 */

@Api(value = "/work", description = "和作品有关的接口")
@Controller
@RequestMapping("/user")
public class WorksController extends BaseController {

    @Autowired
    private WorksService worksService;

    @ApiOperation(value = "获取某个用户的作品列表", notes = "")
    @RequestMapping(value = "/getWorksByUserId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Works>> getWorksById(
            @ApiParam("用户ID") @RequestParam("userId") int userId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Works>> responseData = new ResponseData();
        List<Works> worksList = worksService.getWorksListByUserId(userId);

        responseData.jsonFill(1, null, worksList);
        return responseData;
    }

    @ApiOperation(value = "获取一个故事的所有作品列表(按照点赞数降序)", notes = "")
    @RequestMapping(value = "/user/getWorksListByStoryId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Works>> getWorksListByStoryId(
            @ApiParam("故事ID") @RequestParam("storyId") int storyId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Works>> responseData = new ResponseData();
        List<Works> worksList = worksService.getWorksListByStoryId(storyId);
        responseData.jsonFill(1, null, worksList);
        return responseData;
    }


}
