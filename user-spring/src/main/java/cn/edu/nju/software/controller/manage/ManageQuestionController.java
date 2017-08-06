package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.entity.Question;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.QuestionService;
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

/**
 * Created by xmc1993 on 2017/5/15.
 */
@Api(value = "Admin", description = "管理接口")
@Controller()
@RequestMapping("/manage")
public class ManageQuestionController {
    private static final Logger logger = LoggerFactory.getLogger(ManageQuestionController.class);
    @Autowired
    private QuestionService questionService;

    @ApiOperation(value = "新增题目项", notes = "")
    @RequestMapping(value = "/questions", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Question publishQuestion(
            @ApiParam("题目项") @RequestBody Question question,
            HttpServletRequest request, HttpServletResponse response) {
        question.setCreateTime(new Date());
        question.setUpdateTime(new Date());
        questionService.saveQuestion(question);
        return question;
    }

    @ApiOperation(value = "更新题目项", notes = "")
    @RequestMapping(value = "/questions/{id}", method = {RequestMethod.PUT})
    @ResponseBody
    public Question updateQuestion(
            @ApiParam("ID") @PathVariable int id,
            @ApiParam("") @RequestBody Question question,
            HttpServletRequest request, HttpServletResponse response) {
        question.setId(id);
        question.setUpdateTime(new Date());
        return questionService.updateQuestion(question) ? question : null;

    }

    @ApiOperation(value = "删除题目项", notes = "")
    @RequestMapping(value = "/questions/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        boolean success = questionService.deleteQuestion(id);
        if (!success) {
            throw new RuntimeException("删除失败");
        }
    }


    @ApiOperation(value = "根据ID获得题目项", notes = "")
    @RequestMapping(value = "/questions/{id}", method = {RequestMethod.GET})
    @ResponseBody
    public Question getStoryById(
            @ApiParam("ID") @PathVariable int id,
            HttpServletRequest request, HttpServletResponse response) {
        Question question = questionService.getQuestionById(id);
        if (question == null) {
            throw new RuntimeException("无效的ID");
        } else {
            return question;
        }
    }




}
