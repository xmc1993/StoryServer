package cn.edu.nju.software.vo;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
public class StoryWithTagAndSentenceVo extends StoryWithTagVo {
    private List<SentenceVo> sentenceList;

    public List<SentenceVo> getSentenceList() {
        return sentenceList;
    }

    public void setSentenceList(List<SentenceVo> sentenceList) {
        this.sentenceList = sentenceList;
    }
}
