package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.Answer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/12.
 */

@Repository
public interface AnswerDao {

    boolean saveAnswer(Answer answer);

    boolean updateAnswer(Answer answer);

    boolean deleteAnswer(int id);

    Answer getAnswerById(int id);

    List<Answer> getAnswerListByUserId(int userId, int offset, int limit);

    List<Answer> getAnswerListByQuestionId(int questionId, int offset, int limit);

    List<Answer> getAllAnswerByPage(int offset, int limit);

    List<Answer> getAnswersByQuestId(int id);
}
