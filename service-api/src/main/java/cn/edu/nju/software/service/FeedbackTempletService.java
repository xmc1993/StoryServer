package cn.edu.nju.software.service;

import cn.edu.nju.software.dto.DestinationVo;
import cn.edu.nju.software.entity.FeedbackTemplet;
import cn.edu.nju.software.entity.ResponseData;

import java.util.List;

/**
 * Created by zhangsong on 2017/12/24.
 */
public interface FeedbackTempletService {
     int saveFeedbackTemplet(FeedbackTemplet feedbackTemplet);
     int deleteFeedbackTemplet(int id);
     FeedbackTemplet getFeedbackTempletById(int id);
     ResponseData<List<FeedbackTemplet>> getAllFeedbackTemplet(int page, int pageSize);
     int updateFeedbackTemplet(FeedbackTemplet feedbackTemplet);
     List<DestinationVo> getAllFeedbackDescription();
}
