package cn.edu.nju.software.vo;

import cn.edu.nju.software.entity.StoryTag;

import java.util.List;

/**
 * Created by xmc1993 on 2017/5/14.
 */
public class StoryWithTagVo extends SentenceVo{
    private List<StoryTag> tagList;

    public List<StoryTag> getTagList() {
        return tagList;
    }

    public void setTagList(List<StoryTag> tagList) {
        this.tagList = tagList;
    }
}
