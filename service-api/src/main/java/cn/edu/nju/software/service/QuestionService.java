package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Question;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface QuestionService {

    Question saveQuestion(Question question);

    boolean updateQuestion(Question question);

    boolean deleteQuestion(int id);

    Question getQuestionById(int id);

    List<Question> getAllQuestionByPage(int page, int pageSize);

    Integer getAllQuestionCount();

}
