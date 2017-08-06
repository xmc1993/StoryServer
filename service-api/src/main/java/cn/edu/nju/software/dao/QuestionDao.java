package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface QuestionDao {

    boolean saveQuestion(Question question);

    boolean updateQuestion(Question question);

    boolean deleteQuestion(int id);

    Question getQuestionById(int id);

    List<Question> getAllQuestionByPage(int offset, int limit);

    Integer getAllQuestionCount();

}
