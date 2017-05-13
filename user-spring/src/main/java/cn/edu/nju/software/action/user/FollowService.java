package cn.edu.nju.software.action.user;

import cn.edu.nju.software.entity.ResponseData;
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

@Api("follow controller")
@Controller
public class FollowService {

    @ApiOperation(value = "关注某人", notes = "")
    @RequestMapping(value = "/user/follow", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<String>> follow(
            @ApiParam("被关注者ID") @RequestParam("followeeId") String followeeId,
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @ApiOperation(value = "取消关注某人", notes = "")
    @RequestMapping(value = "/user/unfollow", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<String>> unfollow(
            @ApiParam("需要取消关注者ID") @RequestParam("followeeId") String followeeId,
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }


}
