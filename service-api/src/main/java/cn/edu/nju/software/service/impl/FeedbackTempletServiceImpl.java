package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.FeedbackTempletMapper;
import cn.edu.nju.software.dto.DestinationVo;
import cn.edu.nju.software.entity.FeedbackTemplet;
import cn.edu.nju.software.entity.FeedbackTempletExample;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.FeedbackTempletService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangsong on 2017/12/24.
 */
@Service
public class FeedbackTempletServiceImpl implements FeedbackTempletService {
    @Autowired
    FeedbackTempletMapper feedbackTempletMapper;

    @Override
    public int saveFeedbackTemplet(FeedbackTemplet feedbackTemplet) {
        return feedbackTempletMapper.insert(feedbackTemplet);
    }

    @Override
    public int deleteFeedbackTemplet(int id) {
        return feedbackTempletMapper.deleteByPrimaryKey(id);
    }

    @Override
    public FeedbackTemplet getFeedbackTempletById(int id) {
        return feedbackTempletMapper.selectByPrimaryKey(id) ;
    }

    @Override
    public ResponseData<List<FeedbackTemplet>> getAllFeedbackTemplet(int page,int pageSize) {
        PageHelper.startPage(page, pageSize);
        ResponseData<List<FeedbackTemplet>> responseData=new ResponseData<>();
        FeedbackTempletExample feedbackTempletExample=new FeedbackTempletExample();
        List<FeedbackTemplet> list = feedbackTempletMapper.selectByExample(feedbackTempletExample);
        PageInfo<FeedbackTemplet> pageInfo = new PageInfo<>(list);
        int count = (int) pageInfo.getTotal();
        responseData.setCount(count);
        responseData.jsonFill(1, null, list);
        return responseData;
    }

    @Override
    public int updateFeedbackTemplet(FeedbackTemplet feedbackTemplet) {
        return feedbackTempletMapper.updateByPrimaryKey(feedbackTemplet);
    }

    @Override
    public List<DestinationVo> getAllFeedbackDescription() {
        return feedbackTempletMapper.getAllFeedbackDescription();
    }
}
