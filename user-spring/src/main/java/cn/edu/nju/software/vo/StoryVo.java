package cn.edu.nju.software.vo;

import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.StoryTag;
import lombok.Data;

import java.util.List;

/**
 * Created by xmc1993 on 2017/7/12.
 */
@Data
public class StoryVo extends Story {
    private List<StoryTag> tagList;
    private boolean like = false;
}
