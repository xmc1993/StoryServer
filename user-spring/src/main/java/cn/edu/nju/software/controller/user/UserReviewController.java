package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.Review;
import cn.edu.nju.software.service.ReviewService;
import cn.edu.nju.software.service.UserService;
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
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

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
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/addReview", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> saveReview(
            @ApiParam("workId") @RequestParam("workId") int workId,
            @ApiParam("parentId") @RequestParam(value = "parentId",required = false) Integer parentId,
            @ApiParam("fromUserId") @RequestParam("fromUserId") int fromUserId,
            @ApiParam("toUserId") @RequestParam(value = "toUserId",required = false) Integer toUserId,
            @ApiParam("content") @RequestParam("content") String content,
            HttpServletRequest request,HttpServletResponse response){
        if(workService.getWorksById(workId)==null) throw new RuntimeException("错误的workId");
        ResponseData<Boolean> result = new ResponseData<>();
        if(parentId!=null&&reviewService.getReviewById(parentId)==null) throw new RuntimeException("错误的parentId");
        if(userService.getUserById(fromUserId)==null) throw new RuntimeException("错误的评论用户ID");
        if(toUserId!=null&&userService.getUserById(toUserId)==null) throw new RuntimeException("错误的回复用户ID");
        if(content==null||content.trim().equals("")) throw new RuntimeException("内容不能为空");
        boolean flag=reviewService.insertReview(workId,parentId,fromUserId,toUserId,content.trim());
        if(flag) result.jsonFill(1,null,true);
        else result.jsonFill(2,null,false);
        return result;
    }
    @RequestMapping(value = "/reviews", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Review>> getReviews(@ApiParam("workId") @RequestParam("workId") int workId,
                                                 @ApiParam("offset") @RequestParam("offset") int offset,
                                                 @ApiParam("limit") @RequestParam("limit") int limit){
        if(workService.getWorksById(workId)==null) throw new RuntimeException("错误的workId");
        ResponseData<List<Review>> result = new ResponseData<>();
        List<Review> reviewList = reviewService.getReviewListByWorkId(workId, offset, limit);
        result.jsonFill(1,null,reviewList);
        result.setCount(reviewService.getReviewCountByWorkId(workId));
        return result;
    }
    @RequestMapping(value = "/updateReview", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> updateReview(@RequestParam("reviewId") int reviewId,
                                              @RequestParam("fromUserId") int fromUserId,
                                              @RequestParam("content") String content){
        ResponseData<Boolean> result = new ResponseData<>();
        boolean flag = reviewService.updateReview(reviewId,fromUserId,content);
        if(flag==true) result.jsonFill(1,null,true);
        else result.jsonFill(2,null,false);
        return result;
    }
}
