package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.OpinionFeedback;
import cn.edu.nju.software.entity.ResponseData;

import java.util.List;

/**
 * Created by zhangsong on 2017/11/20.
 */
public interface OpinionFeedbackservice {
    int saveOpinion(OpinionFeedback opinionFeedback);
    ResponseData<List<OpinionFeedback>> getOpinionsByPage(Integer page,Integer pageSize);
}
