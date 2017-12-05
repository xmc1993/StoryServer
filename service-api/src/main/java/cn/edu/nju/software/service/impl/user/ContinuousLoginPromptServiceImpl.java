package cn.edu.nju.software.service.impl.user;

import cn.edu.nju.software.dao.user.ContinuousLoginPromptMapper;
import cn.edu.nju.software.entity.ContinuousLoginPrompt;
import cn.edu.nju.software.entity.ContinuousLoginPromptExample;
import cn.edu.nju.software.service.user.ContinuousLoginPromptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zj
 */
@Service
public class ContinuousLoginPromptServiceImpl implements ContinuousLoginPromptService {
    @Autowired
    ContinuousLoginPromptMapper continuousLoginPromptMapper;

    @Override
    public String selectPromptIntegrated(Integer userId) {
        Date now = new Date();
        ContinuousLoginPromptExample continuousLoginPromptExample = new ContinuousLoginPromptExample();
        ContinuousLoginPromptExample.Criteria criteria = continuousLoginPromptExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andPromptStartTimeLessThanOrEqualTo(now);
        criteria.andPromptEndTimeGreaterThan(now);
        List<ContinuousLoginPrompt> list = continuousLoginPromptMapper.selectByExample(continuousLoginPromptExample);
        if (list.size() == 0) {
            return null;
        } else {
            String prompt = list.get(0).getPrompt();
            return prompt;
        }
    }
}
