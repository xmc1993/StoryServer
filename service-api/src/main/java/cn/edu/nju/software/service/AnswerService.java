package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.Answer;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
public interface AnswerService {

    Answer saveAnswer(Answer badge);

    boolean updateAnswer(Answer badge);

    boolean deleteAnswer(int id);

    Answer getAnswerById(int id);

    List<Answer> getAnswerListByQuestionId(int questionId, int page, int pageSize);

    List<Answer> getAnswerListByUserId(int userId, int page, int pageSize);

    List<Answer> getAllAnswerByPage(int page, int pageSize);

}
