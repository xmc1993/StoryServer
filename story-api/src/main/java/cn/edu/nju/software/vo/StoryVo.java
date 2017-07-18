package cn.edu.nju.software.vo;

import cn.edu.nju.software.entity.Story;
import cn.edu.nju.software.entity.TagRelation;
import lombok.Data;

import java.util.List;

/**
 * Created by Kt on 2017/7/2.
 */
@Data
public class StoryVo {
    private Story story;
    private List<TagRelation> tagRelationList;
    private boolean like = false;
}
