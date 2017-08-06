package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.AnswerDao;
import cn.edu.nju.software.entity.Answer;
import cn.edu.nju.software.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerDao answerDao;


    @Override
    public Answer saveAnswer(Answer answer) {
        if (answerDao.saveAnswer(answer)) {
            return answer;
        }
        return null;
    }

    @Override
    public boolean updateAnswer(Answer answer) {
        return answerDao.updateAnswer(answer);
    }

    @Override
    public boolean deleteAnswer(int id) {
        return answerDao.deleteAnswer(id);
    }

    @Override
    public Answer getAnswerById(int id) {
        return answerDao.getAnswerById(id);
    }

    @Override
    public List<Answer> getAnswerListByQuestionId(int questionId, int page, int pageSize) {
        return answerDao.getAnswerListByQuestionId(questionId, page * pageSize, pageSize);
    }

    @Override
    public List<Answer> getAnswerListByUserId(int userId, int page, int pageSize) {
        return answerDao.getAnswerListByUserId(userId, page * pageSize, pageSize);
    }



    @Override
    public List<Answer> getAllAnswerByPage(int page, int pageSize) {
        int offset = page*pageSize;
        int limit = pageSize;
        return answerDao.getAllAnswerByPage(offset, limit);
    }
}
