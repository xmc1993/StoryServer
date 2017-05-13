package cn.edu.nju.software.action;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.vo.WorksVo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
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
public class WorksController extends BaseController {


    @ApiOperation(value = "获取某个用户的作品列表", notes = "")
    @RequestMapping(value = "/user/getWorksById", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<WorksVo>> getWorksById(
            @ApiParam("用户ID") @RequestParam("userId") String userId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<WorksVo>> responseData = new ResponseData();
        return responseData;
    }

    @ApiOperation(value = "获取一个故事的所有作品列表(按照点赞数降序)", notes = "")
    @RequestMapping(value = "/user/getWorksListByStoryId", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<WorksVo>> getWorksListByStoryId(
            @ApiParam("故事ID") @RequestParam("storyId") String storyId,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<WorksVo>> responseData = new ResponseData();
        return responseData;
    }


}
