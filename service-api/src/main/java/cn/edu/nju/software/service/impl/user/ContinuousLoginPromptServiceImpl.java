package cn.edu.nju.software.service.impl.user;

import cn.edu.nju.software.dao.user.ContinuousLoginPromptMapper;
import cn.edu.nju.software.entity.ContinuousLoginPrompt;
import cn.edu.nju.software.entity.ContinuousLoginPromptExample;
import cn.edu.nju.software.service.user.ContinuousLoginPromptService;
import org.apache.poi.ss.formula.functions.Now;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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
    public String selectPromptByTime() {
        Calendar calendar=Calendar.getInstance();
        int hourOfNow=calendar.get(Calendar.HOUR_OF_DAY);
        ContinuousLoginPromptExample continuousLoginPromptExample = new ContinuousLoginPromptExample();
        ContinuousLoginPromptExample.Criteria criteria = continuousLoginPromptExample.createCriteria();
        criteria.andPromptStartTimeLessThanOrEqualTo(hourOfNow);
        criteria.andPromptEndTimeGreaterThan(hourOfNow);
        List<ContinuousLoginPrompt> list = continuousLoginPromptMapper.selectByExample(continuousLoginPromptExample);
        if (list.size() == 0) {
            return null;
        } else {
            String prompt = list.get(0).getPrompt();
            return prompt;
        }
    }

    @Override
    public int insertContinuousLoginPrompt(ContinuousLoginPrompt continuousLoginPrompt) {
        return continuousLoginPromptMapper.insert(continuousLoginPrompt);
    }

    @Override
    public int updateByPrimaryKey(ContinuousLoginPrompt continuousLoginPrompt) {
        return continuousLoginPromptMapper.updateByPrimaryKeySelective(continuousLoginPrompt);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return continuousLoginPromptMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<ContinuousLoginPrompt> seleteAllPrompt() {
        ContinuousLoginPromptExample continuousLoginPromptExample=new ContinuousLoginPromptExample();
        List<ContinuousLoginPrompt> list= continuousLoginPromptMapper.selectByExample(continuousLoginPromptExample);
        for (ContinuousLoginPrompt e:list) {
            Integer endTime=e.getPromptEndTime();
            endTime=endTime==24?0:endTime;
            e.setPromptEndTime(endTime);
        }
        return list;
    }
}
