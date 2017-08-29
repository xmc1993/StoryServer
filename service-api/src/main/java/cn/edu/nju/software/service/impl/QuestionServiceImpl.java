package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.QuestionDao;
import cn.edu.nju.software.entity.Answer;
import cn.edu.nju.software.entity.Question;
import cn.edu.nju.software.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;


    @Override
    public Question saveQuestion(Question question) {
        if (questionDao.saveQuestion(question)) {
            return question;
        }
        return null;
    }

    @Override
    public boolean updateQuestion(Question question) {
        return questionDao.updateQuestion(question);
    }

    @Override
    public boolean deleteQuestion(int id) {
        return questionDao.deleteQuestion(id);
    }

    @Override
    public Question getQuestionById(int id) {
        return questionDao.getQuestionById(id);
    }

    @Override
    public List<Question> getAllQuestionByPage(int page, int pageSize) {
        int offset = page*pageSize;
        int limit = pageSize;
        return questionDao.getAllQuestionByPage(offset, limit);
    }

    @Override
    public Integer getAllQuestionCount() {
        return questionDao.getAllQuestionCount();
    }

}
