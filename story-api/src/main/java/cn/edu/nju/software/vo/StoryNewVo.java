package cn.edu.nju.software.vo;

import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.StorySet;
import cn.edu.nju.software.entity.StoryTag;
import lombok.Data;

import java.util.List;

/**
 * Created by xmc1993 on 2017/7/12.
 */
@Data
public class StoryNewVo extends Story {
    private List<StoryTag> tagList;
    private List<Integer> albumIdList;
    private boolean like = false;
    private StorySet storySet;//所属故事集
}
