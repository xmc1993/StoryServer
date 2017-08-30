package cn.edu.nju.software.controller.user;

import cn.edu.nju.software.controller.BaseController;
import cn.edu.nju.software.entity.Answer;
import cn.edu.nju.software.entity.Question;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.*;
import cn.edu.nju.software.util.TokenConfig;
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

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Api("tag controller")
@Controller
@RequestMapping("/user")
public class UserQuestionController extends BaseController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;


    @ApiOperation(value = "提交问题答案", notes = "")
    @RequestMapping(value = "/submitAnswer", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Answer> getAlbumListOfStory(
            @ApiParam("问题ID") @RequestParam("questionId") int questionId,
            @ApiParam("答案") @RequestParam("content") String content,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Answer> responseData = new ResponseData<>();
        User user = (User) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        if (user == null) {
            responseData.jsonFill(2, "请先登录", null);
            response.setStatus(401);
            return responseData;
        }
        Answer answer = new Answer();
        answer.setUserId(user.getId());
        answer.setContent(content);
        answer.setQuestionId(questionId);
        answer.setCreateTime(new Date());
        answer.setUpdateTime(new Date());
        Answer res = answerService.saveAnswer(answer);
        if (res != null) {
            responseData.jsonFill(1, null, res);
        }else {
            responseData.jsonFill(2, "失败", null);
        }
        return responseData;
    }

    @ApiOperation(value = "分页获得问题", notes = "")
    @RequestMapping(value = "/getQuestionListByPage", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Question>> getQuestionListByPage(
            @ApiParam("页") @RequestParam int page,
            @ApiParam("页大小") @RequestParam int pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<List<Question>> responseData = new ResponseData();
        List<Question> questionList = questionService.getAllQuestionByPage(page, pageSize);
        responseData.jsonFill(1, null, questionList);
        responseData.setCount(questionService.getAllQuestionCount());
        return responseData;
    }

    @ApiOperation(value = "根据问题获取答案", notes = "")
    @RequestMapping(value = "/getAnswersByQuest", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Answer>> getAnswersByQuestId(
            @ApiParam("问题ID") @RequestParam int id,
            HttpServletRequest request, HttpServletResponse response){
        ResponseData<List<Answer>> responseData = new ResponseData();
        List<Answer> answerList = answerService.getAnswersByQuestId(id);
        responseData.jsonFill(1, null, answerList);
        return responseData;
    }

}
