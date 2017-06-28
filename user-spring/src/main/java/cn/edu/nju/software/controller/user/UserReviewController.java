package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.Review;
import cn.edu.nju.software.service.ReviewService;
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

/**
 * Created by Kt on 2017/6/28.
 */
@Api("review controller")
@Controller
@RequestMapping("/user")
public class UserReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private WorksService workService;
    @ApiOperation(value = "插入评论")
    @RequestMapping(value = "/addReview", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> saveReview(
            @ApiParam("workId") @RequestParam("workId") int workId,
            @ApiParam("parentId") @RequestParam(value = "parentId",required = false) Integer parentId,
            @ApiParam("fromUserId") @RequestParam("fromUserId") int fromUserId,
            @ApiParam("toUserId") @RequestParam(value = "toUserId",required = false) int toUserId,
            @ApiParam("content") @RequestParam("content") int content,
            HttpServletRequest request,HttpServletResponse response){
        if(workService.getWorksById(workId)==null) throw new RuntimeException("错误的workId");
        ResponseData<Boolean> result = new ResponseData<>();
        if(parentId!=null){
            //reviewService.getReviewById(parentId);
        }
        return result;
    }
}
