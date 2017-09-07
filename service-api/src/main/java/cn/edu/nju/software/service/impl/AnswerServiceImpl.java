package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.AnswerDao;
import cn.edu.nju.software.entity.Answer;
import cn.edu.nju.software.entity.TagUserLog;
import cn.edu.nju.software.service.AnswerService;
import cn.edu.nju.software.service.TagUserLogService;
import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    @Autowired
    private TagUserLogService tagUserLogService;


    @Override
    public Answer saveAnswer(Answer answer) {
        if (answerDao.saveAnswer(answer)) {
            extractTagInfo(answer.getContent(), answer.getUserId(), answer.getBabyId());
            return answer;
        }
        return null;
    }

    private void extractTagInfo(String ans, Integer userId, Integer babyId){
        if (ans == null || ans == "") {
            return;
        }
        ans = StringEscapeUtils.unescapeJava(ans);
        try {
            JSONArray optionList = new JSONArray(ans);
            int iSize = optionList.length();
            for (int i = 0; i < iSize; i++) {
                JSONObject jsonObj = optionList.getJSONObject(i);
                String str = jsonObj.getString("tagId");
                JSONArray tagList = new JSONArray(str);
                for (int j = 0; j < tagList.length(); j++) {
                    Integer tagId = tagList.getInt(j);
                    TagUserLog tagUserLog = new TagUserLog();
                    tagUserLog.setUserId(userId);
                    tagUserLog.setTagId(tagId);
                    tagUserLog.setBabyId(babyId);
                    tagUserLogService.saveOrUpdate(tagUserLog);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    @Override
    public List<Answer> getAnswersByQuestId(int id) {
        return answerDao.getAnswersByQuestId(id);
    }
}
