package cn.edu.nju.software.service.user;

import cn.edu.nju.software.entity.ContinuousLoginPrompt;

import java.util.List;

/**
 * @author zj
 */

public interface ContinuousLoginPromptService {
    String selectPromptByTime();
    int insertContinuousLoginPrompt(ContinuousLoginPrompt continuousLoginPrompt);
    int updateByPrimaryKey(ContinuousLoginPrompt continuousLoginPrompt);
    int deleteByPrimaryKey(Integer id);
    List<ContinuousLoginPrompt> seleteAllPrompt();
}
