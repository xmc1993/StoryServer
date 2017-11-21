package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.OpinionFeedbackMapper;
import cn.edu.nju.software.entity.OpinionFeedback;
import cn.edu.nju.software.entity.OpinionFeedbackExample;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.OpinionFeedbackservice;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangsong on 2017/11/20.
 */

@Service
public class OpinionFeedbackserviceImpl implements OpinionFeedbackservice {

    @Autowired
    OpinionFeedbackMapper opinionFeedbackMapper;

    @Override
    public int saveOpinion(OpinionFeedback opinionFeedback) {
        return opinionFeedbackMapper.insert(opinionFeedback);
    }

    @Override
    public ResponseData<List<OpinionFeedback>> getOpinionsByPage(Integer page, Integer pageSize) {
        ResponseData<List<OpinionFeedback>> responseData = new ResponseData<>();
        PageHelper.startPage(page, pageSize);
        OpinionFeedbackExample opinionFeedbackExample = new OpinionFeedbackExample();
        //通过criteria构造查询条件
        List<OpinionFeedback> list = opinionFeedbackMapper.selectByExample(opinionFeedbackExample);
        PageInfo<OpinionFeedback> pageInfo = new PageInfo<>(list);
        int count = (int) pageInfo.getTotal();
        responseData.setCount(count);
        responseData.jsonFill(1, null, list);
        return responseData;
    }
}
